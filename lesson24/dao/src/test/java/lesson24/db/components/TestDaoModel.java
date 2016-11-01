package lesson24.db.components;

import lesson24.db.DaoFactory;
import lesson24.db.Model;
import lesson24.dao.Recipe;
import lesson24.dao.Unit;
import org.junit.Test;

import java.util.Optional;

public class TestDaoModel {
    @Test
    public void testCreate() throws Exception {
        Unit unit = new Unit("Грамм", "г.");
        Recipe recipeObj = new Recipe("test", "description");
        try (DaoFactory factory = new DaoFactory()){
            Model model = factory.create(UnitsDao.class);
            /*Long id = model.create(unit);
            unit.setId(id);
            System.out.println(id);
            unit.setName("МиллиГрамм");
            model.update(unit);*/
            Optional<Object> byId = model.getById(8L);
            if (byId.isPresent()) {
                Unit found = (Unit) byId.get();
                System.out.println(found.getName() + " " + found.getShort_name());
            }
        }

        /*try (DaoFactory factory = new DaoFactory()) {
            Model recipe = factory.create(RecipesDao.class);
            Long aLong = recipe.create(recipeObj);
            System.out.println(aLong);
        }*/
    }
}
