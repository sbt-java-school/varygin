package sbt.lesson18.com.part2.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbt.lesson18.com.part2.Server;
import sbt.lesson18.com.part2.dao.Message;
import sbt.lesson18.com.part2.dao.Person;
import sbt.lesson18.com.part2.exceptions.BusinessException;
import sbt.lesson18.com.part2.exceptions.ConnectionException;
import sbt.lesson18.com.part2.service.Protocol;
import sbt.lesson18.com.part2.service.Command;
import sbt.lesson18.com.utils.Receiver;
import sbt.lesson18.com.utils.Sender;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerClient extends Protocol implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final Map<String, Person> clients = new ConcurrentHashMap<>();

    private final Person person;

    public ServerClient(Socket socket) {
        try {
            this.person = new Person(socket);
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    public void run() {
        try (
                Socket socket = person.getIoStreams().getSocket();
                Sender sender = person.getIoStreams().getSender();
                Receiver receiver = person.getIoStreams().getReceiver()
        ) {
            if (tryAuth()) {
                communication();
            }
        } catch (ConnectionException | SocketException e ) {
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
                if (!clients.get(login).getIoStreams().getSocket().isClosed()) {
                    sender.sendCommand(Command.ALREADY_EXIST);
                    return false;
                } else {
                    resultCommand = Command.RECONNECTION_SUCCESS;
                }
            }
            sendNotification();
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

    protected void getAllMessages() throws IOException {
        person.getIoStreams().getSender().sendObject(person.getMessagesList());
    }

    protected boolean closeConnection() throws IOException {
        LOGGER.info("Пользователь {} покинул чат", person.getLogin());
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

    private void sendNotification() {
        Message message = new Message(Command.NOTIFICATION.getText() + ": " + person.getLogin(), "system", "");
        clients.values().forEach(p -> p.addMessage(message));
    }
}