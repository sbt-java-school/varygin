package lesson24.services;

import lesson24.db.DaoFactory;
import lesson24.db.Model;
import lesson24.db.components.UnitsDao;
import lesson24.db.shema.Unit;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Сервис для взаимодействия представления с данными
 * из таблицы units базы данных
 */
public class UnitService {

    /**
     * Осуществляет запрос на выборку всех единиц измерения в БД
     *
     * @return список единиц измерения
     */
    public static List<Unit> getList() {
        try (DaoFactory factory = new DaoFactory()) {
            Model unitsDao = factory.create(UnitsDao.class);
            Optional<List<?>> unitsDaoList = unitsDao.getList();

            if (!unitsDaoList.isPresent()) {
                return null;
            }

            return unitsDaoList.get()
                    .stream()
                    .map(item -> (Unit) item)
                    .collect(toList());
        }
    }
}
