package sbt.lesson18.com.part2.service;

/**
 * Перечисление доступных серверу и клиенту комманд
 */
public enum Command {
    /**
     * Авторизация
     */
    AUTH("Введите логин:"),

    /**
     * Нотификации
     */
    NOTIFICATION("подключился к чату"),
    NO_MESSAGES("Вам ещё не отправляли сообщений"),
    USER_OUT("покинул чат"),

    /**
     * Команды для комуникации
     */
    GET_ALL("Получить все сообщения", "get"),
    SEND("Отправить сообщение пользователю", "new"),
    EXIT("Пользователь вышел из чата", "exit"),

    /**
     * Сервисные сообщения
     */
    SUCCESS("Сообщение успешно отправлено"),
    CONNECTION_SUCCESS("Вы успешно подключились к чату. Введите комнду:"),
    RECONNECTION_SUCCESS("Рады снова видеть вас. Введите комнду:"),
    ALREADY_EXIST("Пользователь с таким логином уже существует"),
    ERROR("Некорректный ввод"),
    ERROR_USER_NOT_EXIST("Пользователь с таким логином ещё не подключался к чату");

    private final String text;
    private final String code;

    Command() {
        this("", "");
    }

    Command(String text) {
        this(text, "");
    }

    Command(String text, String code) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public String getCode() {
        return code;
    }
}
