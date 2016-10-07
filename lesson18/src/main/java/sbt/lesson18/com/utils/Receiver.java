package sbt.lesson18.com.utils;

import sbt.lesson18.com.part2.service.Command;

import java.io.Closeable;
import java.io.IOException;

/**
 * Интерфейс чтения сообщений от клиента/сервера
 */
public interface Receiver extends Closeable {
    int readInt() throws IOException;

    boolean readBool() throws IOException;

    String readString() throws IOException;

    Object readMessage() throws IOException;

    Command readCommand() throws IOException;

    void close() throws IOException;
}
