package lesson26.home.dao.core;

import lesson26.home.dao.RecipeDao;
import lesson26.home.dao.schema.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeDaoImpl
        extends AbstractDao implements RecipeDao {

    public RecipeDaoImpl() {
        super(Recipe.class);
    }

    @Override
    public List<Recipe> getList(String name) {
        @SuppressWarnings("unchecked")
        List<Recipe> list = entityManager
                .createQuery("select r from Recipe r where r.name like :name")
                .setParameter("name", "%" + name + "%")
                .getResultList();
        return list;
    }
}
