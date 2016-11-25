package lesson26.home.dao;

import lesson26.home.dao.core.UnitDaoImpl;
import lesson26.home.dao.entities.Unit;

/**
 * Интерфейс для взаимодействия с таблицей
 * единиц измерения ингредиентов
 *
 * @see UnitDaoImpl
 */
public interface UnitDao extends EntityDao {
    /**
     * Проверка используемости единицы измерения
     * среди ингредиентов рецептов
     *
     * @param unit искомая единица измерения
     * @return true - единица измерения используется, false - иначе
     */
    boolean isUsed(Unit unit);
}
