package lesson24.db.configuration;

import lesson24.db.exception.BusinessException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Абстрактный класс для чтения настроек из конфигурационного файла
 */
abstract class AbstractConfig implements Config {
    private static final String config = "database.properties";
    private boolean isInit;

    private String host;
    private String dbName;
    private String user;
    private String password;

    private void initProperties() {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(config)) {
            Properties properties = new Properties();
            properties.load(input);

            host = properties.getProperty("host");
            dbName = properties.getProperty("db_name");
            user = properties.getProperty("db_user");
            password = properties.getProperty("db_password");

            isInit = true;
        } catch (IOException e) {
            throw new BusinessException("Can't load database properties", e);
        }
    }

    abstract String getPattern();

    @Override
    public String getConnectionURL() {
        if (!isInit) {
            initProperties();
        }
        return String.format(getPattern(), host, dbName, user, password);
    }
}
