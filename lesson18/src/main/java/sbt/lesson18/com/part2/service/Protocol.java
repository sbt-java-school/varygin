package sbt.lesson18.com.part2.service;

import sbt.lesson18.com.part2.exceptions.ConnectionException;

import java.io.IOException;

public abstract class Protocol {
    protected boolean tryAuth() throws IOException {
        while (true) {
            if (isClosedConnection()) {
                return false;
            }
            loginForm();
            if (login()) {
                break;
            }
        }
        return true;
    }
    protected void communication() throws IOException {
        while (true) {
            String message = getNextMessage();
            if (isClosedConnection()) {
                throw new ConnectionException("Соединение с сервером разорвано");
            }
            if (message.equals(Command.GET_ALL.getCode())) {
                getAllMessages();
            } else if (message.equals(Command.EXIT.getCode())) {
                if (closeConnection()) {
                    break;
                }
            } else {
                sendMessage(message);
            }
        }
    }

    protected abstract boolean isClosedConnection();
    protected abstract void loginForm();
    protected abstract boolean login();

    protected abstract void getAllMessages() throws IOException;
    protected abstract boolean closeConnection() throws IOException;
    protected abstract void sendMessage(String text) throws IOException;
    protected abstract String getNextMessage() throws IOException;
}
