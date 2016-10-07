package sbt.lesson18.com.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbt.lesson18.com.part2.dao.*;
import sbt.lesson18.com.part2.exceptions.*;
import sbt.lesson18.com.part2.service.*;
import sbt.lesson18.com.utils.Sender;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

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
            if (tryAuth()) {
                communication();
            }
        } catch (ConnectionException | SocketException e) {
            LOGGER.info("Пользователь {} покинул чат", person.getLogin());
        } catch (IOException e) {
            throw new BusinessException(e);
        } catch (BusinessException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    protected boolean isClosedConnection() {
        return person.getIoStreams().getSocket().isClosed();
    }

    protected void loginForm() {
        try {
            person.getIoStreams().getSender().sendCommand(Command.AUTH);
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    protected boolean login() {
        Sender sender = person.getIoStreams().getSender();
        try {
            String login = person.getIoStreams().getReceiver().readString();
            if (login == null) {
                sender.sendCommand(Command.ERROR);
                return false;
            }
            person.setLogin(login);

            Command resultCommand = Command.CONNECTION_SUCCESS;
            if (clients.containsKey(login)) {
                Person oldPerson = clients.get(login);
                if (!oldPerson.getIoStreams().getSocket().isClosed()) {
                    sender.sendCommand(Command.ALREADY_EXIST);
                    return false;
                } else {
                    person.setHistory(oldPerson.getHistory());
                    resultCommand = Command.RECONNECTION_SUCCESS;
                }
            }
            sendNotification(Command.NOTIFICATION.getText());
            clients.put(login, person);

            sender.sendCommand(resultCommand);
        } catch (IOException e) {
            if (isClosedConnection()) {
                return false;
            }
        }
        LOGGER.info("Пользователь {} вошёл в чат", person.getLogin());
        return true;
    }

    protected String getNextMessage() throws IOException {
        return person.getIoStreams().getReceiver().readString();
    }

    protected void getAllMessages(boolean showAnswer) throws IOException {
        person.getIoStreams().getSender().sendObject(person.getMessagesList());
    }

    protected boolean closeConnection() throws IOException {
        LOGGER.info("Пользователь {} покинул чат", person.getLogin());
        sendNotification(Command.USER_OUT.getText());
        person.getIoStreams().getSender().sendCommand(Command.SUCCESS);
        return true;
    }

    protected void sendMessage(String text) throws IOException {
        Sender sender = person.getIoStreams().getSender();
        boolean success = false;
        Command error = Command.ERROR;

        if (text.equals(Command.SEND.getCode())) {
            sender.sendCommand(Command.SUCCESS);

            Person personTo;
            Message message = (Message) person.getIoStreams().getReceiver().readMessage();
            if (message != null && (personTo = clients.get(message.getTo())) != null) {
                personTo.addMessage(message);
                success = true;
            }
            error = Command.ERROR_USER_NOT_EXIST;
        }
        if (!success) {
            sender.sendCommand(error);
        } else {
            sender.sendCommand(Command.SUCCESS);
        }
    }

    private void sendNotification(String text) {
        Message message = new Message(person.getLogin() + " " + text, "system", "");
        BCast.setNewClient(message.getText());
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