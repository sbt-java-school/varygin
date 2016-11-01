package lesson24.db.components;

import lesson24.db.shema.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

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
    public Optional<Ingredient> getById(Long id) {
        Optional<?> optionalObj = super.getById(id);
        if (!optionalObj.isPresent()) {
            return Optional.empty();
        }
        return Optional.of((Ingredient)optionalObj.get());
    }

    @Override
    protected Ingredient init(Map<String, Object> resultMap) {
        return new Ingredient(
                ((Number) resultMap.get("id")).longValue(),
                (String) resultMap.get("name")
        );
    }
}
