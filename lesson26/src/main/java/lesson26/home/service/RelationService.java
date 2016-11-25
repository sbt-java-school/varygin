package lesson26.home.service;

import lesson26.home.service.core.RelationServiceImpl;
import lesson26.home.service.entities.IngredientDto;
import lesson26.home.service.entities.RelationDto;

/**
 * Интерфейс сервиса для взаимодействия между представлением и
 * объектом Relation базы данных
 *
 * @see RelationServiceImpl
 */
public interface RelationService {
    /**
     * Валидация и сохранение / изменение отношения в БД
     *
     * @param recipe объект с заполнеными полями отношения
     * @return объект для отображения добавленного ингредиента
     */
    IngredientDto save(RelationDto recipe);

    /**
     * Удаление ингредиента из базы данных
     *
     * @param relationDto экземпляр удоляемого отношения
     */
    boolean delete(RelationDto relationDto);
}
