package lesson24.db.components;

import lesson24.dao.RecipesToIngredients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RecipesToIngredientsDao extends DaoModel {
    @Autowired
    public RecipesToIngredientsDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTable() {
        return "recipes_to_ingredients";
    }

    @Override
    protected RecipesToIngredients init(Map<String, Object> resultMap) {
        return new RecipesToIngredients(
                ((Number) resultMap.get("recipe_id")).longValue(),
                ((Number) resultMap.get("ingredient_id")).longValue(),
                ((Number) resultMap.get("amount")).intValue(),
                ((Number) resultMap.get("unit_id")).longValue()
        );
    }
}
