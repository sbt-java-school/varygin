package sbt.lesson18.com.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbt.lesson18.com.part2.dao.IOStreams;
import sbt.lesson18.com.part2.dao.Message;
import sbt.lesson18.com.part2.exceptions.BusinessException;
import sbt.lesson18.com.part2.exceptions.ConnectionException;
import sbt.lesson18.com.part2.service.Protocol;
import sbt.lesson18.com.part2.service.Command;
import sbt.lesson18.com.utils.Receiver;
import sbt.lesson18.com.utils.Sender;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class Client extends Protocol {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private String login;
    private final IOStreams streams;
    private final IOStreams localStreams;

    private static final int PORT = 1234;
    private static final String HOST = "localhost";

    public Client() {
        try {
            Socket socket = new Socket(HOST, PORT);
            streams = new IOStreams(socket);
            localStreams = new IOStreams(null, System.in, System.out);
        } catch (IOException e) {
            throw new ConnectionException("Не удалось подключиться к серверу " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        try {
            new Client().start();
        } catch (ConnectionException | BusinessException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    public void start() {
        try (
                Socket socket = streams.getSocket();
                Sender sender = streams.getSender();
                Receiver receiver = streams.getReceiver()
        ) {
            showGuide();
            if (tryAuth()) {
                communication();
            }
        } catch (SocketException e) {
            localStreams.getSender().print("Сервер разорвал соединение");
        } catch (ConnectionException e) {
            localStreams.getSender().print(e.getMessage());
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    private void showGuide() throws IOException {
        localStreams.getSender().print(
                "Для выхода: exit\n" +
                "Для получения списка сообщений: get\n" +
                "Для отправки сообщения пользователю: #логин получателя#::#сообщение#\n" +
                "Авторизуйтесь для начала."
        );
    }

    protected boolean isClosedConnection() {
        return streams.getSocket().isClosed();
    }

    protected void loginForm() {
        try {
            Command command = Command.valueOf(streams.getReceiver().readString()); //ждём запрос сервера
            if (command != Command.AUTH) {
                throw new BusinessException("Нарушен протокол!");
            }
            localStreams.getSender().print(command.getText());

            login = getNextMessage();
            if (login.equals(Command.EXIT.getCode())) {
                streams.getSocket().close();
            }
            streams.getSender().send(login);
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    protected boolean login() {
        try {
            Command command = Command.valueOf(streams.getReceiver().readString()); //ждём подтверждение сервера
            localStreams.getSender().print(command.getText());
            return command == Command.CONNECTION_SUCCESS || command == Command.RECONNECTION_SUCCESS;
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    protected void getAllMessages() throws IOException {
        streams.getSender().send(Command.GET_ALL.getCode());

        List<Message> messages = (List<Message>) streams.getReceiver().readMessage();
        if (messages.isEmpty()) {
            localStreams.getSender().print(Command.NO_MESSAGES.getText());
        }
        messages.forEach(message -> localStreams.getSender().print(message.getText()));
    }

    protected boolean closeConnection() throws IOException {
        streams.getSender().send(Command.EXIT.getCode());
        String result = streams.getReceiver().readString();
        if (Command.valueOf(result) == Command.SUCCESS) {
            localStreams.getSender().print("Вы успешно вышли из чата");
        } else {
            localStreams.getSender().print("Сервер не отключился");
            return false;
        }
        return true;
    }

    protected void sendMessage(String text) throws IOException {
        String[] buff;
        if (!text.contains("::") || (buff = text.split("::")).length != 2 || login.equals(buff[0])) {
            localStreams.getSender().print(Command.ERROR.getText());
            return;
        }
        streams.getSender().send(Command.SEND.getCode()); //предупреждаеме сервер о новом сообщении

        Command command = Command.valueOf(streams.getReceiver().readString()); //ждём соглашение
        if (command == Command.SUCCESS) {
            streams.getSender().sendObject(new Message(buff[1], login, buff[0]));
            command = Command.valueOf(streams.getReceiver().readString()); //ждём подтверждение
        }
        localStreams.getSender().print(command.getText());
    }

    protected String getNextMessage() throws IOException {
        return localStreams.getReceiver().readString();
    }
}
