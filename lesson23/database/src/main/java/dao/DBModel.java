package dao;

import exception.BusinessException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class DBModel {
    private Long id;
    protected final String table;
    protected static final TemplateManager templateManager = TemplateManagerImpl.getInstance();

    public DBModel(String table) {
        this(null, table);
    }

    public DBModel(Long id, String table) {
        this.id = id;
        this.table = table;
    }

    public boolean store() {
        String[] fields = getClassFields();
        return templateManager.getTemplate().execute(connection -> {
            try (PreparedStatement statement = prepare(connection, getStoreSql(fields), getParamsValues(fields))) {
                if (id != null) {
                    statement.setLong(fields.length + 1, id);
                }
                if (statement.executeUpdate() != 1) {
                    throw new BusinessException("Connection error", statement.getWarnings());
                }
                setNewId(statement);
                return true;
            }
        });
    }

    private String getStoreSql(String[] fields) {
        String[] wrapStrings = Stream.of(fields).map(field -> "`" + field + "`").toArray(String[]::new);
        String sql;
        if (id != null) {
            sql = "UPDATE " + table + " SET " + StringUtils.join(wrapStrings, " = ?,") + " = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO " + table
                    + " (" + StringUtils.join(wrapStrings, ", ") + ")"
                    + " VALUES (" + StringUtils.repeat("?", ", ", fields.length) + ")";
        }
        return sql;
    }

    private void setNewId(PreparedStatement statement) throws SQLException {
        if (id != null) {
            return;
        }
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new BusinessException("Can't store to table " + table);
        }
        setId(generatedKeys.getLong(1));
    }

    private String[] getClassFields() {
        return Stream.of(this.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(TableField.class))
                .map(Field::getName)
                .toArray(String[]::new);
    }

    private Object[] getParamsValues(String[] fields) {
        Class<? extends DBModel> aClass = this.getClass();
        return Stream.of(fields)
                .map(StringUtils::capitalize)
                .map(field -> {
                    try {
                        return aClass.getMethod("get" + field).invoke(this);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        throw new BusinessException("Not found getter for field '" + field + "'", e);
                    }
                }).toArray();
    }

    protected PreparedStatement prepare(Connection connection, String sql, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (params.length > 0) {
            IntStream.range(0, params.length)
                    .forEach(i -> {
                        try {
                            statement.setObject(i + 1, params[i]);
                        } catch (SQLException e) {
                            throw new BusinessException("Wrong parameter " + params[i], e);
                        }
                    });
        }
        return statement;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
