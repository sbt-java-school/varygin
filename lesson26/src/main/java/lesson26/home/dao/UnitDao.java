package lesson26.home.dao;

import lesson26.home.utils.BusinessException;

public interface UnitDao extends EntityDao {
    @Override
    default Object save(Object object) {
        throw new BusinessException("Операция не поддержвается");
    }

    @Override
    default Object getById(Long id) {
        throw new BusinessException("Операция не поддержвается");
    }

    @Override
    default void delete(Object object) {
        throw new BusinessException("Операция не поддержвается");
    }
}
