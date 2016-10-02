package sbt.lesson18.com.part2.dao;

import sbt.lesson18.com.utils.Receiver;
import sbt.lesson18.com.utils.Sender;
import sbt.lesson18.com.utils.SocketReader;
import sbt.lesson18.com.utils.SocketSender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IOStreams {
    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;

    public IOStreams(Socket socket) throws IOException {
        this(socket, socket.getInputStream(), socket.getOutputStream());
    }

    public IOStreams(Socket socket, InputStream inputStream, OutputStream outputStream) {
        this.sender = new SocketSender(outputStream);
        this.receiver = new SocketReader(inputStream);
        this.socket = socket;
    }

    public Sender getSender() {
        return sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Socket getSocket() {
        return socket;
    }
}
