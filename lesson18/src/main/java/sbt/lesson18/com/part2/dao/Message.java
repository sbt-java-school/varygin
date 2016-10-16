package sbt.lesson18.com.part2.dao;

import sbt.lesson18.com.part2.service.Command;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, реализующий передаваемые между пользователями сообщения
 */
public class Message implements Serializable {
    private final Command command;
    private final String text;
    private final Date dateAdd;
    private final String from;
    private final String to;

    public Message(Command command, String text, String from, String to) {
        this.command = command;
        this.text = text;
        this.from = from;
        this.to = to;
        this.dateAdd = new Date();
    }

    public Message(Command command) {
        this(command, null, null, null);
    }

    public Message(Command command, String text) {
        this(command, text, null, null);
    }

    public String getMessage() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy k:m");
        return "[" + dateFormat.format(dateAdd) + "] " + ((from != null) ? from : "") + " >> " + text;
    }

    public String getText() {
        return text;
    }

    public String getTo() {
        return to;
    }

    public Command getCommand() {
        return command;
    }
}
