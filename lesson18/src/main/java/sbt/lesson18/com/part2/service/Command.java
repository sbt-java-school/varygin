package sbt.lesson18.com.part2.service;

import sbt.lesson18.com.part2.dao.Message;

/**
 * Перечисление доступных серверу и клиенту комманд
 */
public enum Command {
    /**
     * Авторизация
     */
    AUTH("Введите логин:") {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.loginForm();
        }
    },
    LOGIN() {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.login(message);
        }
    },

    /**
     * Нотификации
     */
    NO_MESSAGES("Вам ещё не отправляли сообщений") {
        @Override
        public void action(Protocol protocol, Message message) {

        }
    },

    /**
     * Команды для комуникации
     */
    GET_ALL("Получить все сообщения", "get") {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.getAllMessages();
        }
    },
    SEND("Отправить сообщение пользователю", "new") {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.sendMessage(message);
        }
    },
    EXIT("Пользователь вышел из чата", "exit") {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.closeConnection();
        }
    },
    GET_USERS() {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.getUsers();
        }
    },

    /**
     * Сервисные сообщения
     */
    SUCCESS("Сообщение успешно отправлено") {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.showNotification(null);
        }
    },
    CONNECTION_SUCCESS("Вы успешно подключились к чату!") {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.login(message);
        }
    },
    RECONNECTION_SUCCESS("Рады снова видеть вас!") {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.login(message);
        }
    },
    ALREADY_EXIST("Пользователь с таким логином уже существует") {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.userExist();
        }
    },
    ERROR("Некорректный ввод") {
        @Override
        public void action(Protocol protocol, Message message) {

        }
    },
    ERROR_USER_NOT_EXIST("Пользователь с таким логином ещё не подключался к чату") {
        @Override
        public void action(Protocol protocol, Message message) {
            protocol.showNotification(this.getText());
        }
    };

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

    abstract public void action(Protocol protocol, Message message);
}
