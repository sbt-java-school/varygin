package lesson26.home.service.core;

import lesson26.home.dao.RecipeDao;
import lesson26.home.dao.RelationDao;
import lesson26.home.dao.core.RelationDaoImpl;
import lesson26.home.dao.schema.Recipe;
import lesson26.home.dao.schema.Relation;
import lesson26.home.service.RecipeService;
import lesson26.home.service.schema.RecipeTdo;
import lesson26.home.utils.BusinessException;
import lesson26.home.utils.ValidationFrom;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeDao recipeDao;
    private RelationDao relationDao;

    @Autowired
    public RecipeServiceImpl(RecipeDao recipeDao,
                             RelationDao relationDao) {
        this.recipeDao = recipeDao;
        this.relationDao = relationDao;
    }

    @Override
    @Transactional
    public Long save(RecipeTdo recipe) {
        try {
            ValidationFrom form = new ValidationFrom(recipe);
            if (!form.isValid()) {
                throw new BusinessException(form.getErrors());
            }

            Recipe newRecipe = new Recipe(
                    recipe.getName(),
                    recipe.getDescription()
            );
            Recipe result = (Recipe) recipeDao.save(newRecipe);
            if (Objects.isNull(result)) {
                return null;
            }
            return result.getId();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional
    public RecipeTdo get(Long id) {
        try {
            Recipe recipe = (Recipe) recipeDao.getById(id);
            Objects.requireNonNull(recipe);

            return new RecipeTdo(recipe);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional
    public List<RecipeTdo> getList() {
        try {
            @SuppressWarnings("unchecked")
            List<Recipe> list = recipeDao.getList();
            return list.stream().map(RecipeTdo::new).collect(toList());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional
    public List<RecipeTdo> getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return getList();
        }
        try {
            return recipeDao.getList(name).stream()
                    .map(RecipeTdo::new)
                    .collect(toList());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional
    public void delete(RecipeTdo recipeTdo) {
        try {
            Recipe recipe = (Recipe) recipeDao.getById(recipeTdo.getId());
            List<Relation> relations = recipe.getRelations();
            if (!relations.isEmpty()) {
                relations.forEach(relationDao::delete);
            }
            Objects.requireNonNull(recipe);
            recipeDao.delete(recipe);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
