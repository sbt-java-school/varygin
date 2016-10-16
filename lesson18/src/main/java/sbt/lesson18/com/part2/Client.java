package sbt.lesson18.com.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbt.lesson18.com.part2.dao.IOStreams;
import sbt.lesson18.com.part2.dao.Message;
import sbt.lesson18.com.part2.exceptions.BusinessException;
import sbt.lesson18.com.part2.exceptions.ConnectionException;
import sbt.lesson18.com.part2.service.Command;
import sbt.lesson18.com.part2.service.Protocol;
import window.view.Controller;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * Клиентское приложение, поддерживающее взаимодействие с серверным по протоколю Protocol
 */
public class Client extends Protocol implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private final Controller controller;
    private static final SynchronousQueue<Message> messages = new SynchronousQueue<>();

    private String login;
    private boolean isAuth;
    private final IOStreams streams;
    private final Thread bcast;

    private static final int PORT = 1234;
    private static final String HOST = "localhost";

    public Client(Controller controller) {
        this.controller = controller;
        try {
            Socket socket = new Socket(HOST, PORT);
            streams = new IOStreams(socket);
            bcast = new Thread(new BCast());
        } catch (IOException e) {
            controller.print("Сервер не отвечает");
            throw new ConnectionException(e);
        }
    }

    public void run() {
        controller.enableBtn();
        try (IOStreams socket = streams) {
            communication();
        } catch (SocketException | ConnectionException e) {
            controller.print("Вы вышли из чата");
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    public void addMessage(Message message) {
        try {
            if (message != null) {
                messages.put(message);
            }
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public Message prepareMessage(String text) {
        if (isAuth) {
            String recipient = controller.getRecipient();
            if (recipient == null || recipient.isEmpty()) {
                controller.print(Command.ERROR.getText());
                return null;
            }
            return new Message(Command.SEND, text, login, recipient);
        } else {
            return new Message(Command.LOGIN, text);
        }
    }

    @Override
    protected void before() {
        bcast.setDaemon(true);
        bcast.start();

        controller.print("Для отправки сообщения пользователю веберите адресата из списка\n" +
                "Авторизуйтесь для начала.\n");
        nextAction(getSocketMessage());
    }

    @Override
    protected void after() {
        bcast.interrupt();
    }

    @Override
    protected void loginForm() {
        try {
            controller.print(Command.AUTH.getText());
            Message login = getNextMessage();
            this.login = login.getText();
            streams.getSender().sendObject(login);
            if (login.getCommand() == Command.EXIT) {
                streams.getSocket().close();
                throw new ConnectionException("Выход");
            }
            nextAction(getSocketMessage());
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    protected void userExist() {
        controller.print(Command.ALREADY_EXIST.getText());
        loginForm();
    }

    @Override
    protected void showNotification(String text) {
        if (text != null) {
            controller.print(text);
        }
    }

    @Override
    protected void login(Message message) {
        controller.print(message.getText());
        isAuth = true;
        controller.setLabel("Сообщение:");
        controller.activateGet();
        getAvailableUsers();
        getAllMessages();
    }

    @Override
    protected void getAllMessages() {
        try {
            streams.getSender().sendObject(new Message(Command.GET_ALL));

            List<Message> messages = (List<Message>) streams.getReceiver().readMessage();
            if (messages.isEmpty()) {
                controller.print(Command.NO_MESSAGES.getText());
            } else {
                messages.forEach(message -> controller.print(message.getMessage()));
            }
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    protected void closeConnection() {
        try {
            streams.getSender().sendObject(new Message(Command.EXIT));
            throw new ConnectionException("Выход");
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    protected void sendMessage(Message message) {
        try {
            streams.getSender().sendObject(message);
            nextAction(getSocketMessage());
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    protected Message getNextMessage() {
        try {
            return messages.take();
        } catch (InterruptedException e) {
            throw new ConnectionException(e);
        }
    }

    private Message getSocketMessage() {
        try {
            return (Message) streams.getReceiver().readMessage();
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    private void getAvailableUsers() {
        try {
            streams.getSender().sendObject(new Message(Command.GET_USERS));

            List<String> users = (List<String>) streams.getReceiver().readMessage();
            if (users != null && !users.isEmpty()) {
                users.forEach(controller::addUserToList);
            }
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
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
                Thread currentThread = Thread.currentThread();
                while (!currentThread.isInterrupted()) {
                    DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                    socket.receive(datagramPacket);
                    String userLogin = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                    if (!userLogin.equals(login)) {
                        controller.addUserToList(userLogin);
                        controller.print("Подключился новый пользователь: " + userLogin);
                    }
                }
                socket.leaveGroup(multicastAddress);
            } catch (IOException e) {
                controller.print("Ошибка в широковещательном протокола");
            }
        }
    }
}
