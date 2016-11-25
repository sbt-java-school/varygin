package lesson26.home.dao;

import lesson26.home.dao.core.AbstractDao;
import lesson26.home.utils.Page;
import lesson26.home.utils.Request;

import java.util.List;

/**
 * Общий интерфейс взаимодействия с объектами БД
 *
 * @see AbstractDao
 */
public interface EntityDao {
    /**
     * Сохранение объекта в БД
     *
     * @param object экземпляр сохраняемого объекта
     * @return объект после сохранения в БД
     */
    Object save(Object object);

    /**
     * Получение объекта из базы данных по идентификатору
     *
     * @param id идентификатор искомого объекта
     * @return искомый объект
     */
    Object getById(Long id);

    /**
     * Удаление объекта из базы данных
     *
     * @param object экземпляр удаляемого объекта
     */
    void delete(Object object);

    /**
     * Получение списка сущностей с фильтром по подстроке
     * в названии сущности
     *
     * @param subName искомая подстрока
     * @return список сущностей
     */
    Page<Object> getList(String subName, Request request);
}
