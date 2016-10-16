package sbt.lesson18.com.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbt.lesson18.com.part2.dao.IOStreams;
import sbt.lesson18.com.part2.dao.Message;
import sbt.lesson18.com.part2.dao.Person;
import sbt.lesson18.com.part2.exceptions.ConnectionException;
import sbt.lesson18.com.part2.service.Command;
import sbt.lesson18.com.part2.service.Protocol;
import sbt.lesson18.com.utils.Sender;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;

/**
 * Класс для обеспечения обработки каждого клиента в отдельном потоке
 * Хранит в себе список всех клиентов в конкурентной мапе
 * Для инициализации необходим сокет подключившегося клиента
 * Протокол взаимодействия описан в классе родителе: Protocol
 */
class ServerClient extends Protocol implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final Map<String, Person> clients = new ConcurrentHashMap<>();

    private final Person person;

    ServerClient(Socket socket) {
        try {
            this.person = new Person(socket);
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    public void run() {
        try (IOStreams socket = person.getIoStreams()) {
            communication();
        } catch (ConnectionException | SocketException e) {
            LOGGER.info("Пользователь {} покинул чат", person.getLogin());
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

    @Override
    protected void before() {
        loginForm();
    }

    @Override
    protected void after() {

    }

    @Override
    protected void loginForm() {
        try {
            person.getIoStreams().getSender().sendObject(new Message(Command.AUTH));
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    protected void login(Message message) {
        try {
            Sender sender = person.getIoStreams().getSender();

            String login = message.getText();
            if (login == null) {
                loginForm();
                return;
            }

            person.setLogin(login);
            String resultMessage = Command.CONNECTION_SUCCESS.getText();

            if (clients.containsKey(login)) {
                Person oldPerson = clients.get(login);
                if (!oldPerson.getIoStreams().getSocket().isClosed()) {
                    sender.sendObject(new Message(Command.ALREADY_EXIST));
                    return;
                } else {
                    person.setHistory(oldPerson.getHistory());
                    resultMessage = Command.RECONNECTION_SUCCESS.getText();
                }
            }
            sendNotification();
            clients.put(login, person);

            sender.sendObject(new Message(Command.LOGIN, resultMessage));
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
        LOGGER.info("Пользователь {} вошёл в чат", person.getLogin());
    }

    @Override
    protected Message getNextMessage() {
        try {
            return (Message) person.getIoStreams().getReceiver().readMessage();
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    protected void getAllMessages() {
        try {
            person.getIoStreams().getSender().sendObject(person.getMessagesList());
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    protected void closeConnection() {
        throw new ConnectionException("Выход");
    }

    @Override
    protected void sendMessage(Message message) {
        try {
            Sender sender = person.getIoStreams().getSender();
            Person personTo;
            if (message != null && (personTo = clients.get(message.getTo())) != null) {
                personTo.addMessage(message);
                sender.sendObject(new Message(Command.SUCCESS));
                return;
            }
            sender.sendObject(new Message(Command.ERROR_USER_NOT_EXIST));
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    protected void getUsers() {
        try {
            List<String> users = clients.keySet().stream()
                    .filter(user -> !user.equals(person.getLogin()))
                    .collect(Collectors.toList());
            person.getIoStreams().getSender().sendObject(users);
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    private void sendNotification() {
        BCast.setNewClient(person.getLogin());
    }

    static class BCast implements Runnable {
        private static final int MULTICAST_PORT = 4555;
        private static final String MULTICAST_GROUP = "239.255.255.0";
        private static final SynchronousQueue<String> newClients = new SynchronousQueue<>();

        public void run() {
            try {
                InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
                try (DatagramSocket socket = new DatagramSocket()) {
                    Thread currentThread = Thread.currentThread();
                    LOGGER.info("Широковещательный сокет подключился");
                    while (!currentThread.isInterrupted()) {
                        String buff = newClients.take();
                        DatagramPacket packege = new DatagramPacket(buff.getBytes(), buff.getBytes().length, group, MULTICAST_PORT);
                        socket.send(packege);
                    }
                } catch (InterruptedException e) {
                    LOGGER.debug("BCast interrupted");
                }
            } catch (IOException e) {
                System.out.println("BCastExit");
            }
        }

        static void setNewClient(String text) {
            try {
                newClients.put(text);
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }
}