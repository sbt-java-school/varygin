package sbt.lesson18.com.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbt.lesson18.com.part2.dao.IOStreams;
import sbt.lesson18.com.part2.dao.Message;
import sbt.lesson18.com.part2.exceptions.*;
import sbt.lesson18.com.part2.service.*;

import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 * Клиентское приложение, поддерживающее взаимодействие с серверным по протоколю Protocol
 */
public class Client extends Protocol {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private String login;
    private final IOStreams streams;
    private final IOStreams localStreams;
    private final Thread bcast;

    private static final int PORT = 1234;
    private static final String HOST = "localhost";

    public Client() {
        try {
            Socket socket = new Socket(HOST, PORT);
            streams = new IOStreams(socket);
            localStreams = new IOStreams(null, System.in, System.out);
            bcast = new Thread(new BCast());
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
        try (IOStreams socket = streams) {
            showGuide();
            bcast.setDaemon(true);
            bcast.start();
            if (tryAuth()) {
                getAllMessages(false);
                communication();
            }
        } catch (SocketException e) {
            localStreams.getSender().print("Сервер разорвал соединение");
        } catch (ConnectionException e) {
            localStreams.getSender().print(e.getMessage());
        } catch (IOException e) {
            throw new BusinessException(e);
        } finally {
            bcast.interrupt();
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
            Command command = streams.getReceiver().readCommand(); //ждём запрос сервера
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
            Command command = streams.getReceiver().readCommand(); //ждём подтверждение сервера
            localStreams.getSender().print(command.getText());
            return command == Command.CONNECTION_SUCCESS || command == Command.RECONNECTION_SUCCESS;
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    protected void getAllMessages(boolean showAnswer) throws IOException {
        streams.getSender().send(Command.GET_ALL.getCode());

        List<Message> messages = (List<Message>) streams.getReceiver().readMessage();
        if (messages.isEmpty() && showAnswer) {
            localStreams.getSender().print(Command.NO_MESSAGES.getText());
        }
        messages.forEach(message -> localStreams.getSender().print(message.getText()));
    }

    protected boolean closeConnection() throws IOException {
        streams.getSender().send(Command.EXIT.getCode());
        if (streams.getReceiver().readCommand() == Command.SUCCESS) {
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

        Command command = streams.getReceiver().readCommand(); //ждём соглашение
        if (command == Command.SUCCESS) {
            streams.getSender().sendObject(new Message(buff[1], login, buff[0]));
            command = streams.getReceiver().readCommand(); //ждём подтверждение
        }
        localStreams.getSender().print(command.getText());
    }

    protected String getNextMessage() throws IOException {
        return localStreams.getReceiver().readString();
    }

    private class BCast implements Runnable {
        private static final int MULTICAST_PORT = 4555;
        private static final String MULTICAST_GROUP = "239.255.255.0";
        private static final int BUFFER_SIZE = 100;

        public void run() {
            try (MulticastSocket socket = new MulticastSocket(MULTICAST_PORT)) {

                InetAddress multicastAddress = InetAddress.getByName(MULTICAST_GROUP);
                socket.joinGroup(multicastAddress);

                byte[] buffer = new byte[BUFFER_SIZE];
                String message = "";
                Thread currentThread = Thread.currentThread();
                while (!currentThread.isInterrupted()) {
                    DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                    socket.receive(datagramPacket);
                    message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                    localStreams.getSender().print(message);
                }
                socket.leaveGroup(multicastAddress);
            } catch (IOException e) {
                LOGGER.debug("Ошибка в широковещательном протокола");
            }
        }
    }
}
