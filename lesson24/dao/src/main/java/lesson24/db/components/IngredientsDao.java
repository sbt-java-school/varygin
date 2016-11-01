package lesson24.db.components;

import lesson24.dao.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class IngredientsDao extends DaoModel {

    @Autowired
    public IngredientsDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTable() {
        return "ingredients";
    }

    @Override
    protected Ingredient init(Map<String, Object> resultMap) {
        return new Ingredient(
                ((Number) resultMap.get("id")).longValue(),
                (String) resultMap.get("name")
        );
    }
}
