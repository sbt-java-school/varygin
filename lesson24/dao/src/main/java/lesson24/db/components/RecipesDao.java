package lesson24.db.components;

import lesson24.db.dao.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RecipesDao extends DaoModel {

    @Autowired
    public RecipesDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTable() {
        return "recipes";
    }

    @Override
    protected Recipe init(Map<String, Object> resultMap) {
        return new Recipe(
                ((Number) resultMap.get("id")).longValue(),
                (String) resultMap.get("name"),
                (String) resultMap.get("description")
        );
    }
}
