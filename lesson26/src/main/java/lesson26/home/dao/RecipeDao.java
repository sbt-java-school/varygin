package lesson26.home.dao;

import lesson26.home.dao.schema.Recipe;

import java.util.List;

public interface RecipeDao extends EntityDao {
    List<Recipe> getList(String name);
}
