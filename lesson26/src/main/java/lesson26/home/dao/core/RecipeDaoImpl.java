package lesson26.home.dao.core;

import lesson26.home.dao.RecipeDao;
import lesson26.home.dao.entities.Recipe;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeDaoImpl extends AbstractDao implements RecipeDao {

    public RecipeDaoImpl() {
        super(Recipe.class);
    }
}
