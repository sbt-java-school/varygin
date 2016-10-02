package sbt.lesson18.com.part1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbt.lesson18.com.utils.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Сервер: отвечает за подключение новых клиентов (максимум MAX_COUNT_OF_THREADS)
 * Каждый новый клиент обрабатывается в отдельном потоке
 * Поведение клиента и сервера подчиняется протоколу:
 * 1. Сервер подключается - ждёт подключения нового клиента
 * 2. Клиент подключается и отправляет число, введённое пользователем
 * 3. Сервер отвечает либо ДА либо НЕТ в качестве переменной типа boolean
 * 4. Операция повторяется, пока клиент не угадает число
 */
public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private static final int PORT = 1234;
    public static final int N_THREADS = 10;
    private static int MIND_NUMBER = 55;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("Server is waiting for connection");
            while (true) {
                executorService.execute(new PersonalWork(serverSocket.accept()));
            }
        } catch (IOException e) {
            System.out.println("Exception!!!");
        }
    }

    private static class PersonalWork implements Runnable {
        private final Socket clientSocket;

        private PersonalWork(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            LOGGER.debug("New client is connected [{}]", clientSocket.getInetAddress());
            try (
                    Receiver reader = new SocketReader(clientSocket.getInputStream());
                    Sender sender = new SocketSender(clientSocket.getOutputStream())
            ) {
                while (reader.readInt() != MIND_NUMBER) {
                    sender.send(Boolean.toString(false));
                }
                sender.send(Boolean.toString(true));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}
