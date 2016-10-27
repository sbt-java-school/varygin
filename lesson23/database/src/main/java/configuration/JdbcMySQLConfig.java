package configuration;

public class JdbcMySQLConfig extends AbstractConfig {
    private static final JdbcMySQLConfig INSTANCE = new JdbcMySQLConfig();

    private JdbcMySQLConfig() {
    }

    public static JdbcMySQLConfig getInstance() {
        return INSTANCE;
    }

    @Override
    String getPattern() {
        return "jdbc:mysql://%s/%s?user=%s&password=%s";
    }
}
