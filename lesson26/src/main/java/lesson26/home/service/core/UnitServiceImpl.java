package lesson26.home.service.core;

import lesson26.home.dao.UnitDao;
import lesson26.home.dao.entities.Unit;
import lesson26.home.service.UnitService;
import lesson26.home.service.entities.UnitDto;
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
 * Сервис для взаимодействия представления с данными
 * из таблицы units базы данных
 */
@Service
@Transactional
public class UnitServiceImpl implements UnitService {
    public static final int MAX_UNITS_COUNT = 5;

    private UnitDao unitDao;

    @Autowired
    public UnitServiceImpl(UnitDao unitDao) {
        this.unitDao = unitDao;
    }

    @Override
    public Long save(UnitDto unitDto) {
        return wrap(() -> {
            try {
                unitDto.setName(isBlank(unitDto.getName())
                        ? null
                        : unitDto.getName());
                unitDto.setShortName(isBlank(unitDto.getShortName())
                        ? null
                        : unitDto.getShortName());

                ValidationFrom form = new ValidationFrom(unitDto);
                if (!form.isValid()) {
                    throw new BusinessException(form.getErrors());
                }

                Unit result = (Unit) unitDao.save(
                        new Unit(
                                unitDto.getId(),
                                unitDto.getName(),
                                unitDto.getShortName()
                        )
                );

                return result.getId();
            } catch (DataIntegrityViolationException e) {
                throw new BusinessException("Такая единица измерения уже существует.", e);
            }
        });
    }

    @Override
    public boolean delete(UnitDto unitDto) {
        return wrap(() -> {
            Unit unit = (Unit) unitDao.getById(unitDto.getId());
            requireNonNull(unit);

            if (unitDao.isUsed(unit)) {
                throw new BusinessException("Единица измерения не может быть удалена, " +
                        "так как связана с рецептами.");
            }

            unitDao.delete(unit);
            return true;
        });
    }

    /**
     * Осуществляет запрос на выборку всех единиц измерения в БД
     *
     * @return список единиц измерения
     */
    public Page<UnitDto> getList(String name, Integer page) {
        return wrap(() -> {
            Request request = new Request(page, MAX_UNITS_COUNT);

            Page<Object> result = unitDao.getList(name, request);
            List<UnitDto> list = result.getContents().stream()
                    .map(item -> new UnitDto((Unit) item)).collect(toList());

            Long pages = request.getPages(result.getTotalCount());
            return new Page<>(list, pages);
        });
    }
}
