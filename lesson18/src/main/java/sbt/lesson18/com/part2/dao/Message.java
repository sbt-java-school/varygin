package sbt.lesson18.com.part2.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, реализующий передаваемые между пользователями сообщения
 */
public class Message implements Serializable {
    private final String text;
    private final Date dateAdd;
    private final String from;
    private final String to;

    public Message(String text, String from, String to) {
        this.text = text;
        this.from = from;
        this.to = to;
        this.dateAdd = new Date();
    }

    public String getText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy k:m");
        return "[" + dateFormat.format(dateAdd) + "]" + from + " >> " + text;
    }

    public String getTo() {
        return to;
    }
}
