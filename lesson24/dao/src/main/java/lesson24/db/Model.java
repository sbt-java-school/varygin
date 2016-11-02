package lesson24.db;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Общий интерфейс всех моделей для взаимодействия с базой данных
 */
public interface Model {
    /**
     * Поиск записи в таблице по идентификатору
     *
     * @param id идентификатор записи
     * @return экземпляр класса запрашиваемого объекта
     */
    Optional<?> getById(Long id);

    /**
     * Создание записи в таблице, соответствующей
     * экземпляру передаваемого объекта
     *
     * @param model экземпляр сохраняемого в базе объекта
     * @return идентификатор созданной записи в базе данных
     */
    Long create(Object model);

    /**
     * Обновление записи в таблице, соответствующей
     * экземпляру передаваемого объекта
     *
     * @param model экземпляр обновляемого в базе объекта
     * @return <code>true</code> в случае успешного обноваления, <code>false</code> - иначе
     */
    boolean update(Object model);

    /**
     * Удаление объекта из базы данных по его идентификатору
     *
     * @param id идентификатор удаляемого объекта
     * @return <code>true</code> в случае успешного удаления, <code>false</code> - иначе
     */
    boolean remove(Long id);

    /**
     * Получение списка всех записей из таблицы модели
     *
     * @return список записей вызываемой модели
     */
    Optional<List<?>> getList();

    /**
     * Запрос на получение списка записей из таблицы модели
     * с фильтром по полю <code>field</code> и значением <code>value</code>
     *
     * @param field наименование поля для фильтрации
     * @param value искомое значение поля
     * @return список записей вызываемой модели
     */
    Optional<List<?>> getList(String field, String value);
}
