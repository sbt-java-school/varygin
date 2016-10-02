package sbt.lesson18.com.utils;

import sbt.lesson18.com.part2.service.Command;

import java.io.*;

public class SocketSender implements Sender {

    private final BufferedWriter writer;
    private final OutputStream outputStream;

    public SocketSender(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void send(String message) throws IOException {
        writer.write(message + "\n");
        writer.flush();
    }

    public void sendCommand(Command command) throws IOException {
        this.send(command.toString());
    }

    public void sendObject(Object object) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    public void print(String message) {
        try {
            send(message);
        } catch (IOException e) {
            System.out.println(message);
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
