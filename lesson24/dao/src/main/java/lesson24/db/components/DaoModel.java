package lesson24.db.components;

import lesson24.db.Model;
import lesson24.db.shema.TableField;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

abstract class DaoModel implements Model {
    final NamedParameterJdbcTemplate jdbcTemplate;

    DaoModel(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<?> getById(Long id) {
        String query = "SELECT * FROM " + getTable() + " WHERE id = ?";
        Map<String, Object> mapResult = jdbcTemplate.queryForMap(query, new MapSqlParameterSource("id", id));
        if (mapResult.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(init(mapResult));
    }

    @Override
    public Long create(Object model) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(model);
        List<String> classFields = getClassFields(model);
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
    public boolean update(Object model) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(model);
        List<String> classFields = getClassFields(model);
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
    public Optional<List<?>> getList() {
        return getList("1", "1");
    }

    @Override
    public Optional<List<?>> getList(String field, String value) {
        String query = "SELECT * FROM " + getTable() + " WHERE " + field + " = :value";
        SqlParameterSource params = new MapSqlParameterSource("value", value);
        List<Map<String, Object>> listResult = jdbcTemplate.queryForList(query, params);
        if (listResult.isEmpty()) {
            return Optional.empty();
        }
        List<Object> objects = listResult.stream().map(this::init).collect(toList());
        return Optional.of(objects);
    }

    @Override
    public boolean remove(Long id) {
        Objects.requireNonNull(id);
        String query = "DELETE FROM " + getTable() + " WHERE id = :value";
        SqlParameterSource params = new MapSqlParameterSource("value", id);
        int delete = jdbcTemplate.update(query, params);
        return delete == 1;
    }

    private List<String> getClassFields(Object model) {
        return getClassFields(model.getClass());
    }

    List<String> getClassFields(Class<?> aClass) {
        return Stream.of(aClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(TableField.class))
                .map(Field::getName)
                .collect(toList());
    }

    protected abstract String getTable();

    protected abstract Object init(Map<String, Object> resultMap);
}
