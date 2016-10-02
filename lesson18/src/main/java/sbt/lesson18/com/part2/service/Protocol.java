package sbt.lesson18.com.part2.service;

import sbt.lesson18.com.part2.exceptions.ConnectionException;

import java.io.IOException;

/**
 * Протокол взаимодействия клиента и сервера
 */
public abstract class Protocol {
    /**
     * Метод для авторизации клиента на сервере.
     * При подключении нового клиента, сервер отправляет запрос на авторизацию по логину (loginForm)
     * Клиентское приложение считывает от пользователя логин и отправляет серверу (login)
     * Сервер пытается авторизовать пользователя:
     *      Если пользователь ранее был авторизован, но выходил - он просто переподключается
     *      Если пользователь новый и ещё есть свободные потоки - подключается новый пользователь
     *      Иначе - в авторизации отказывается
     * Клиент ждёт от сервера ответа и если он положительный - начинает комуникацию, а если нет, повторяет запрос логина
     *
     * @return true - в случае успешной авторизации, false - иначе
     * @throws IOException - пробрасываются все ошибки ввода
     */
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

    /**
     * Комуникация между авторизованным пользователем и серверром
     * Комуникацию инициирует пользователь вводя команду в консоль
     * Если соединение не потеряно, то команда отправляется серверу и ожадается ответ в соответствии с протоколом
     * Сервер, в свою очередь отвечает по протоколу взаимодействия, соответствующим запрошеной комманде образом
     * Условие завершение взаимодействия - либо экстренное прерывание, либо ввод команды exit пользователем
     * @throws IOException
     */
    protected void communication() throws IOException {
        while (true) {
            String message = getNextMessage();
            if (isClosedConnection()) {
                throw new ConnectionException("Соединение с сервером разорвано");
            }
            if (message.equals(Command.GET_ALL.getCode())) {
                getAllMessages(true);
            } else if (message.equals(Command.EXIT.getCode())) {
                if (closeConnection()) {
                    break;
                }
            } else {
                sendMessage(message);
            }
        }
    }

    /**
     * Проверка на наличие соединения
     * @return true - если соединение не потеряно, false - иначе
     */
    protected abstract boolean isClosedConnection();

    /**
     * Запрос сервером авторизации
     * Передача клиентом логина
     */
    protected abstract void loginForm();

    /**
     * Попытка авторизовать клиента сервером
     * Ожидание вердикта клиентом
     * @return true - если авторизация удалась, false - иначе
     */
    protected abstract boolean login();

    /**
     * Запрос на все отправленные сообщения клиентом
     * Отправка всех сообщений клиента сервером
     * @param showAnswer - флаг, показывать ли ответ от сервера в клиенте
     * @throws IOException
     */
    protected abstract void getAllMessages(boolean showAnswer) throws IOException;

    /**
     * Запрос на завершение сессии клиентом
     * Корректное завершение сервером всех потоков, связанных с клиентом
     * @return true - в случае успешного завершения, false - иначе
     * @throws IOException
     */
    protected abstract boolean closeConnection() throws IOException;

    /**
     * Отправка введённого пользователем сообщения от клиентского приложения
     * Получение отправленного пользователем сообщения на стороне сервера и перенаправление этого сообщения соответствующему клиенту
     * @param text текст сообщения у клиента и команда "приготовиться к получению сообщения" у сервера
     * @throws IOException
     */
    protected abstract void sendMessage(String text) throws IOException;

    /**
     * Получение отправленной команды из интерфейса клиента и сервера
     * @return
     * @throws IOException
     */
    protected abstract String getNextMessage() throws IOException;
}
