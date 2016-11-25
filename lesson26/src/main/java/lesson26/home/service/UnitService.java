package lesson26.home.service;

import lesson26.home.service.core.UnitServiceImpl;
import lesson26.home.service.entities.UnitDto;
import lesson26.home.utils.Page;

/**
 * Интерфейс сервиса для взаимодействия между представлением и
 * объектом Unit базы данных
 *
 * @see UnitServiceImpl
 */
public interface UnitService {
    /**
     * Валидация и сохренение / изменение
     * единицы измерения в базе данных
     *
     * @param unitDto объект с заполнеными полями единицы измерения
     * @return идентификатор добавленной единицы измерения
     */
    Long save(UnitDto unitDto);

    /**
     * Удаление единицы измерения из базы данных
     *
     * @param unitDto экземпляр удоляемой единицы измерения
     * @return true - в случае успешного удаления, false - иначе
     */
    boolean delete(UnitDto unitDto);

    /**
     * Получение списка единиц измерения из БД
     *
     * @return список единиц измерения
     */
    Page<UnitDto> getList(String name, Integer page);
}
