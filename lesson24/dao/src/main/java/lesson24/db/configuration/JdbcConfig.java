package lesson24.db.configuration;

/**
 * Реализация конфигурации приложения
 */
public class JdbcConfig extends AbstractConfig {
    private static final JdbcConfig INSTANCE = new JdbcConfig();

    private JdbcConfig() {
    }

    public static JdbcConfig getInstance() {
        return INSTANCE;
    }

    @Override
    String getPattern() {
        return "jdbc:#DRIVER#:#HOST#/#DB##USER##PASSWORD#";
    }
}
