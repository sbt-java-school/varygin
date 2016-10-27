package jdbc;

import configuration.Config;
import configuration.JdbcMySQLConfig;
import exception.BusinessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTemplate implements Template {
    private static final Config config = JdbcMySQLConfig.getInstance();

    @Override
    public <T> T execute(Action<T> action) {
        try (Connection connection = openConnection()) {
            return action.execute(connection);
        } catch (Exception e) {
            throw new BusinessException("Execution error", e);
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(config.getConnectionURL());
    }
}
