package sbt.lesson18.com.utils;

import sbt.lesson18.com.part2.dao.Message;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface Receiver extends Closeable {
    int readInt() throws IOException;
    boolean readBool() throws IOException;
    String readString() throws IOException;
    Object readMessage() throws IOException;
    void close() throws IOException;
}
