package lesson26.home.dao.core;

import lesson26.home.dao.IngredientDao;
import lesson26.home.dao.entities.Ingredient;
import org.springframework.stereotype.Repository;

@Repository
public class IngredientDaoImpl
        extends AbstractDao implements IngredientDao {

    public IngredientDaoImpl() {
        super(Ingredient.class);
    }
}
