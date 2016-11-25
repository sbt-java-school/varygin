package lesson26.home.service.core;

import lesson26.home.dao.IngredientDao;
import lesson26.home.dao.entities.Ingredient;
import lesson26.home.dao.entities.Relation;
import lesson26.home.service.IngredientService;
import lesson26.home.service.entities.IngredientDto;
import lesson26.home.utils.BusinessException;
import lesson26.home.utils.Page;
import lesson26.home.utils.Request;
import lesson26.home.utils.ValidationFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static lesson26.home.utils.Helper.wrap;
import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Сервис для обработки ингредиентов
 * и сохранения / изменения / удаления их в базе данных
 */
@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {
    private static final Integer MAX_INGREDIENTS_COUNT = 5;

    private IngredientDao ingredientDao;

    @Autowired
    public IngredientServiceImpl(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    @Override
    public Long save(IngredientDto ingredientDto) {
        return wrap(() -> {
            try {
                ingredientDto.setName(isBlank(ingredientDto.getName())
                        ? null
                        : ingredientDto.getName());

                ValidationFrom form = new ValidationFrom(ingredientDto);
                if (!form.isValid()) {
                    throw new BusinessException(form.getErrors());
                }

                Ingredient result = (Ingredient) ingredientDao.save(
                        new Ingredient(
                                ingredientDto.getId(),
                                ingredientDto.getName()
                        )
                );

                return result.getId();
            } catch (DataIntegrityViolationException e) {
                throw new BusinessException("Этот ингредиент уже существует.", e);
            }
        });
    }

    @Override
    public boolean delete(IngredientDto ingredientDto) {
        return wrap(() -> {
            Ingredient ingredient = (Ingredient) ingredientDao.getById(ingredientDto.getId());
            requireNonNull(ingredient);

            List<Relation> relations = ingredient.getRelations();

            if (!relations.isEmpty()) {
                throw new BusinessException("Ингредиент не может быть удалён, " +
                        "так как принадлежит рецепту '" +
                        relations.get(0).getRecipe().getName() + "'");
            }

            ingredientDao.delete(ingredient);

            return true;
        });
    }

    @Override
    public Page<IngredientDto> getList(String name, Integer page) {
        return wrap(() -> {
            Request request = new Request(page, MAX_INGREDIENTS_COUNT);

            Page<Object> result = ingredientDao.getList(name, request);
            List<IngredientDto> list = result.getContents().stream()
                    .map(item -> new IngredientDto((Ingredient) item)).collect(toList());

            return new Page<>(list, request.getPages(result.getTotalCount()));
        });
    }


}
