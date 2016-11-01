package lesson24.db.configuration;

/**
 * Общий интерфейс создания конфигурации подключения к БД
 */
interface Config {
    /**
     * Метод для получения URL для соединения с БД
     * @return строка
     */
    String getConnectionURL();
}
