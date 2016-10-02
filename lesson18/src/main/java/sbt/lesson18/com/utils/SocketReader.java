package sbt.lesson18.com.utils;

import sbt.lesson18.com.part2.dao.Message;
import sbt.lesson18.com.part2.exceptions.BusinessException;

import java.io.*;
import java.util.List;

public class SocketReader implements Receiver {

    private final InputStream inputStream;
    private final BufferedReader reader;

    public SocketReader(InputStream inputStream) {
        this.inputStream = inputStream;
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public int readInt() throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    public boolean readBool() throws IOException {
        return Boolean.parseBoolean(reader.readLine());
    }

    public String readString() throws IOException {
        return reader.readLine();
    }

    public Object readMessage() throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
             return objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new BusinessException(e);
        }
    }

    public void close() throws IOException {
        reader.close();
    }
}
