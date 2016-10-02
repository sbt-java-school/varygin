package sbt.lesson18.com.part2.dao;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

/**
 * Person - каждый клиент чата
 * Каждая персона содержит историю сообщений текущей сессии
 * и свой логин
 */
public class Person {
    private static final int DEFAULT_BOUND = 10;
    private String login;
    private final Deque<Message> history;
    private final Semaphore blockingCountMessages;
    private final IOStreams ioStreams;

    public Person(Socket socket) throws IOException {
        this("anonymous", socket);
    }

    public Person(String login, Socket socket) throws IOException {
        this(login, socket, DEFAULT_BOUND);
    }

    public Person(String login, Socket socket, int bound) throws IOException {
        this.login = login;
        this.ioStreams = new IOStreams(socket);
        this.blockingCountMessages = new Semaphore(bound);
        this.history = new ConcurrentLinkedDeque<>();
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void addMessage(Message message) {
        if (!blockingCountMessages.tryAcquire()) {
            history.remove();
        }
        history.add(message);
    }

    public List<Message> getMessagesList() {
        List<Message> messages = new ArrayList<>();
        while (!history.isEmpty()) {
            messages.add(history.poll());
            blockingCountMessages.release();
        }
        return Collections.synchronizedList(messages);
    }

    public String getLogin() {
        return login;
    }

    public IOStreams getIoStreams() {
        return ioStreams;
    }
}
