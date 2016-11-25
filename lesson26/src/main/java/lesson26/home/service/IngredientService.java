package lesson26.home.service;

import lesson26.home.service.core.IngredientServiceImpl;
import lesson26.home.service.entities.IngredientDto;
import lesson26.home.service.entities.RecipeDto;
import lesson26.home.utils.Page;

import java.util.List;

/**
 * Интерфейс сервиса для взаимодействия между представлением и
 * объектом Ingredient базы данных
 *
 * @see IngredientServiceImpl
 */
public interface IngredientService {
    /**
     * Получение списка ингредиентов из БД
     *
     * @return список ингредиентов
     */
    Page<IngredientDto> getList(String name, Integer page);


    /**
     * Валидация и сохренение / изменение
     * ингредиента в базе данных
     *
     * @param ingredientDto объект с заполнеными полями ингредиента
     * @return идентификатор добавленного ингредиента
     */
    Long save(IngredientDto ingredientDto);

    /**
     * Удаление ингредиента из базы данных
     *
     * @param ingredientDto экземпляр удоляемого ингредиента
     * @return true - в случае успешного удаления, false - иначе
     */
    boolean delete(IngredientDto ingredientDto);
}
