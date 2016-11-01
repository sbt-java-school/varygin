package lesson24.db.components;

import lesson24.db.Model;
import lesson24.db.dao.TableField;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class DaoModel implements Model {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    DaoModel(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Object> getById(Long id) {
        String query = "SELECT * FROM " + getTable()
                + " WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Map<String, Object>> listResult = jdbcTemplate.queryForList(query, params);
        if (listResult.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(init(listResult.get(0)));
    }

    @Override
    public Long create(Object table) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(table);
        List<String> classFields = getClassFields(table);
        String[] wrapFields = classFields.stream()
                .map(field -> "`" + field + "`").toArray(String[]::new);
        String[] values = classFields.stream()
                .map(field -> ":" + field).toArray(String[]::new);

        String query = "INSERT INTO " + getTable()
                + " (" + StringUtils.join(wrapFields, ", ") + ")"
                + " VALUES (" + StringUtils.join(values, ", ") + ")";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, params, holder);
        return holder.getKey().longValue();
    }

    @Override
    public boolean update(Object table) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(table);
        List<String> classFields = getClassFields(table);
        String[] wrapFields = classFields.stream()
                .map(field -> "`" + field + "` = :" +  field)
                .toArray(String[]::new);
        String query = "UPDATE " + getTable()
                + " SET " + StringUtils.join(wrapFields, ",")
                + " WHERE id = :id";
        int update = jdbcTemplate.update(query, params);
        return update == 1;
    }

    @Override
    public Optional<List<Object>> getList() {
        String query = "SELECT * FROM " + getTable() + " WHERE 1 = :tag";
        SqlParameterSource params = new MapSqlParameterSource("tag", 1);
        List<Map<String, Object>> listResult = jdbcTemplate.queryForList(query, params);
        if (listResult.isEmpty()) {
            return Optional.empty();
        }
        List<Object> objects = listResult.stream().map(this::init).collect(Collectors.toList());
        return Optional.of(objects);
    }

    @Override
    public Optional<List<Object>> getList(String field, String value) {
        throw new IllegalStateException("Operation not supported");
    }

    private List<String> getClassFields(Object table) {
        return Stream.of(table.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(TableField.class))
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    protected abstract String getTable();

    protected abstract Object init(Map<String, Object> resultMap);
}
