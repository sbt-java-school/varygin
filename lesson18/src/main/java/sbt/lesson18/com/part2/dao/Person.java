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
 * Каждая персона содержит историю сообщений текущей сессии (максимум 10), свой логин и
 * потоки взаимодействия для серверного приложения
 */
public class Person {
    // Максимальное количество сообщений в истории
    private static final int DEFAULT_BOUND = 10;

    // Логин пользователя
    private String login;

    // История сообщений, если сообщения в истории превысят лимит, то очередь начнёт отчищаться с самых старых сообщений
    private Deque<Message> history;

    // Хранилище потоков для взаимодействия клиен-сервер
    private final IOStreams ioStreams;

    // Блокировка для контроля количества сообщений в истории
    private final Semaphore blockingCountMessages;

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

    public void setHistory(Deque<Message> history) {
        this.history = history;
    }

    public Deque<Message> getHistory() {
        return history;
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
