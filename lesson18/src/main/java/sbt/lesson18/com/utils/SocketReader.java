package sbt.lesson18.com.utils;

import sbt.lesson18.com.part2.exceptions.BusinessException;
import sbt.lesson18.com.part2.service.Command;

import java.io.*;

public class SocketReader implements Receiver {

    private final InputStream inputStream;
    private final BufferedReader reader;

    public SocketReader(InputStream inputStream) {
        this.inputStream = inputStream;
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public int readInt() throws IOException {
        int val = 0;
        try {
            val = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            //ignore
        }
        return val;
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

    @Override
    public Command readCommand() throws IOException {
        return Command.valueOf(readString());
    }

    public void close() throws IOException {
        reader.close();
    }
}
