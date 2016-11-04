package lesson24.db.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Абстрактный класс для чтения настроек из конфигурационного файла
 * и формирования строки для подключения к базе данных.
 * Все наследники этого класса должны быть синглтонами.
 */
abstract class AbstractConfig implements Config {
    private static final String config = "database.properties";

    private String host;
    private String dbName;
    private String user;
    private String password;
    private String driver;

    private String connectionURL;

    // Флаг наличия инициализации данных из файла
    private boolean isInit;

    /**
     * Функция для чтения настроек из конфигурационного файла.
     * Данная функция вызывается в момент обращения
     */
    private void initProperties() {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(config)) {
            Properties properties = new Properties();
            properties.load(input);

            driver = properties.getProperty("driver");
            host = properties.getProperty("host");
            dbName = properties.getProperty("db_name");
            if (isBlank(driver) || isBlank(host) || isBlank(dbName)) {
                throw new NullPointerException("Required property for configuration database isn't found");
            }
            user = properties.getProperty("db_user");
            password = properties.getProperty("db_password");

            isInit = true;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Контракт на предоставление шаблона строки для подключения
     *
     * @return строка-шаблон, которая может содержать список обозначений:
     * #DRIVER# - драйвер подключения
     * #HOST# - хост
     * #DB# - название базы данных
     * #USER# - логин пользователя БД для авторизации
     * #PASSWORD# - пароль пользователя
     */
    abstract String getPattern();

    @Override
    public String getConnectionURL() {
        if (!isInit) {
            initProperties();
            connectionURL = getReplaced();
        }
        return connectionURL;
    }

    /**
     * Замена всех обозначений на значения из конфигурационного файла.
     *
     * @return URL для подключения
     */
    private String getReplaced() {
        String result = replaceEach(getPattern(),
                new String[]{"#DRIVER#", "#HOST#", "#DB#"},
                new String[]{driver, host, dbName});
        if (isNotBlank(user)) {
            result = replace(result, "#USER#", "?user=" + user);
            if (isNotBlank(password)) {
                result = replace(result, "#PASSWORD#", "&password=" + password);
            }
        } else {
            result = replaceEach(result,
                    new String[]{"#USER#", "#PASSWORD#"},
                    new String[]{"", ""});
        }
        return replace(result, "\\", "/");
    }

    @Override
    public String getDriver() {
        return driver;
    }
}
