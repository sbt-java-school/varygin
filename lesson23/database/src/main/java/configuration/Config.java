package configuration;

/**
 * Общий интерфейс создания конфигурации подключения к БД
 */
public interface Config {
    /**
     * Метод для получения URL для соединения с БД
     *
     * @return строка
     */
    String getConnectionURL();
}
