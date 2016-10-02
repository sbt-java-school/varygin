package sbt.lesson18.com.utils;


import sbt.lesson18.com.part2.service.Command;

import java.io.Closeable;
import java.io.IOException;

/**
 * Интерфейс отправки сообщений между клиентом и сервером
 */
public interface Sender extends Closeable {
    void send(String message) throws IOException;
    void sendCommand(Command command) throws IOException;
    void sendObject(Object object) throws IOException;
    void print(String message);
    void close() throws IOException;
}
