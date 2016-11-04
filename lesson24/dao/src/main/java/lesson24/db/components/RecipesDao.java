package lesson24.db.components;

import lesson24.db.Model;
import lesson24.db.sсhema.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

import static lesson24.db.components.Tables.RECIPES;

@Repository
public class RecipesDao extends DaoModel implements Model {

    @Autowired
    public RecipesDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTable() {
        return RECIPES.getName();
    }

    @Override
    public Optional<Recipe> getById(Long id) {
        Optional<?> optionalObj = super.getById(id);
        if (!optionalObj.isPresent()) {
            return Optional.empty();
        }
        return Optional.of((Recipe) optionalObj.get());
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
