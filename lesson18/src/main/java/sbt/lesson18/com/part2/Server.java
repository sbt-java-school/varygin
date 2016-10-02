package sbt.lesson18.com.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbt.lesson18.com.part2.exceptions.BusinessException;
import sbt.lesson18.com.part2.exceptions.ConnectionException;
import sbt.lesson18.com.part2.server.ServerClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final int PORT = 1234;
    private static final int MAX_COUNT_OF_THREADS = 5;

    private final ExecutorService clientsThreadPool;

    public Server(int nThreads) {
        if (nThreads > MAX_COUNT_OF_THREADS) {
            nThreads = MAX_COUNT_OF_THREADS;
        }
        this.clientsThreadPool = Executors.newFixedThreadPool(nThreads);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("Сервер ожидает подключения");
            while (true) {
                Socket socket = serverSocket.accept();
                LOGGER.info("Попытка подключения к чату");
                clientsThreadPool.execute(new ServerClient(socket));
            }
        } catch (ConnectionException e) {
            throw new BusinessException("Не удалось подключить нового пользователя к чату: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new BusinessException("Ошибка сервера: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        try {
            new Server(MAX_COUNT_OF_THREADS).start();
        } catch (ConnectionException | BusinessException e) {
            LOGGER.debug(e.getMessage());
        }
    }
}
