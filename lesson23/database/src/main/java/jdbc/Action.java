package jdbc;

import java.sql.Connection;

@FunctionalInterface
public interface Action<T> {
    T execute(Connection connection) throws Exception;
}
