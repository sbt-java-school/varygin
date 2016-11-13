package lesson26.home.service.core;

import lesson26.home.dao.UnitDao;
import lesson26.home.dao.schema.Unit;
import lesson26.home.service.UnitService;
import lesson26.home.service.schema.UnitTdo;
import lesson26.home.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Сервис для взаимодействия представления с данными
 * из таблицы units базы данных
 */
@Service
public class UnitServiceImpl implements UnitService {

    private UnitDao unitDao;

    @Autowired
    public UnitServiceImpl(UnitDao unitDao) {
        this.unitDao = unitDao;
    }

    /**
     * Осуществляет запрос на выборку всех единиц измерения в БД
     *
     * @return список единиц измерения
     */
    public Map<Long, String> getList() {
        try {
            @SuppressWarnings("unchecked")
            List<Unit> list = unitDao.getList();
            return list.stream().map(UnitTdo::new)
                    .collect(toMap(UnitTdo::getId, UnitTdo::getName));
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
