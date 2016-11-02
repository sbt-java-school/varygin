package lesson24.db.components;

import lesson24.db.shema.RecipesToIngredients;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static lesson24.db.components.Tables.*;

@Repository
public class RecipesToIngredientsDao extends DaoModel {
    @Autowired
    public RecipesToIngredientsDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTable() {
        return RELATIONS.getName();
    }

    @Override
    protected RecipesToIngredients init(Map<String, Object> resultMap) {
        return new RecipesToIngredients(
                ((Number) resultMap.get("recipe_id")).longValue(),
                ((Number) resultMap.get("ingredient_id")).longValue(),
                ((Number) resultMap.get("amount")).doubleValue(),
                ((Number) resultMap.get("unit_id")).longValue()
        );
    }

    @Override
    public Optional<?> getById(Long id) {
        throw new IllegalStateException();
    }

    public boolean create(List<RecipesToIngredients> relations) {
        if (Objects.isNull(relations) || relations.isEmpty()) {
            return false;
        }
        List<String> classFields = getClassFields(RecipesToIngredients.class);
        String[] wrapFields = classFields.stream()
                .map(field -> "`" + field + "`").toArray(String[]::new);
        String[] values = classFields.stream()
                .map(field -> ":" + field).toArray(String[]::new);

        String query = "INSERT INTO " + getTable()
                + " (" + StringUtils.join(wrapFields, ", ") + ")"
                + " VALUES (" + StringUtils.join(values, ", ") + ")";

        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(relations.toArray());
        jdbcTemplate.batchUpdate(query, params);
        return true;
    }

    @Override
    public boolean remove(Long recipeId) {
        return removeByKey("recipe_id", recipeId);
    }

    public boolean removeByKey(String field, Long id) {
        if (!field.equals("recipe_id") && !field.equals("ingredient_id")) {
            throw new IllegalStateException("Нет такого поля");
        }
        Objects.requireNonNull(id);
        String query = "DELETE FROM " + getTable() + " WHERE " + field + " = :value";
        SqlParameterSource params = new MapSqlParameterSource("value", id);
        int delete = jdbcTemplate.update(query, params);
        return delete == 1;
    }

    public List<Map<String, Object>> getByRecipeId(Long id) {
        Objects.requireNonNull(id);
        SqlParameterSource params = new MapSqlParameterSource("recipe_id", id);
        String query = "SELECT rti.*, " +
                "ig.name as ingredient_name," +
                "u.name as unit_name, u.short_name " +
                "FROM " + getTable() + " rti " +
                "LEFT JOIN " + INGREDIENTS.getName() + " ig ON ig.id = rti.ingredient_id " +
                "LEFT JOIN " + UNITS.getName() + " u ON u.id = rti.unit_id " +
                "WHERE rti.recipe_id = :recipe_id";
        return jdbcTemplate.queryForList(query, params);
    }
}
