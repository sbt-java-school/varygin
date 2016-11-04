package lesson24.db.configuration;

/**
 * Общий интерфейс для конфигурирования подключения к БД
 */
interface Config {
    /**
     * Метод для получения URL для соединения с БД
     *
     * @return строка
     */
    String getConnectionURL();

    /**
     * Методя для получения текущего драйвера подключения к БД
     *
     * @return
     */
    String getDriver();
}
