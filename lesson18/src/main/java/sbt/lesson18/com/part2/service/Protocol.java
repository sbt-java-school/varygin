package sbt.lesson18.com.part2.service;

import sbt.lesson18.com.part2.dao.Message;
import sbt.lesson18.com.part2.exceptions.BusinessException;

/**
 * Протокол взаимодействия клиента и сервера
 */
public abstract class Protocol {

    /**
     * Комуникация между пользователем и серверром осуществляется при помощи
     * команд отправляемых клиентом/сервером. Все команды представленны в виде сообщений
     * Message. Каждое сообщение должно содержать команду типа Command для корректной обработки
     * запроса. Каждый элемент перечисления Command содержит метод action и в зависимости от
     * запрошеной команды выполняется соответствующее действие протокола.
     */
    protected final void communication() {
        try {
            before();
            while (true) {
                nextAction(getNextMessage());
            }
        } finally {
            after();
        }
    }

    /**
     * Запуск действия в зависимости от команды сервера/клиента
     *
     * @param message обрабатываемое сообщение
     */
    protected final void nextAction(Message message) {
        message.getCommand().action(this, message);
    }

    /**
     * Сервисные действия для подготовки комуникации между клиентом и сервером
     */
    protected void before() {
    }

    protected void after() {
    }

    /**
     * Запрос сервером авторизации
     * Передача клиентом логина
     */
    protected abstract void loginForm();

    /**
     * Попытка авторизовать клиента сервером
     * Ожидание вердикта клиентом
     */
    protected abstract void login(Message message);

    /**
     * Обработка ответа сервера в случае дублирования логина
     */
    protected void userExist() {
        throw new BusinessException(Command.ALREADY_EXIST.getText());
    }

    /**
     * Распечатка результата авторизации на стороне клиента
     *
     * @param text текст сообщения от сервера
     */
    protected void showNotification(String text) {
        throw new BusinessException(text);
    }

    protected void getUsers() {
        throw new BusinessException("Операция не поддерживается");
    }

    /**
     * Запрос на все отправленные сообщения клиентом
     * Отправка всех сообщений сервером клиенту
     */
    protected abstract void getAllMessages();

    /**
     * Запрос на завершение сессии клиентом
     * Корректное завершение сервером всех потоков, связанных с клиентом
     */
    protected abstract void closeConnection();

    /**
     * Отправка введённого пользователем сообщения от клиентского приложения
     * Получение отправленного пользователем сообщения на стороне сервера и
     * перенаправление этого сообщения соответствующему клиенту
     *
     * @param message передаваемое сообщение
     */
    protected abstract void sendMessage(Message message);

    /**
     * Ожидание получения сообщения
     */
    protected abstract Message getNextMessage();
}
