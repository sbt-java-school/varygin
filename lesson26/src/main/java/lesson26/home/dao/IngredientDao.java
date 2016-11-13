package lesson26.home.dao;

import lesson26.home.utils.BusinessException;

public interface IngredientDao extends EntityDao {
    @Override
    default Object getById(Long id) {
        throw new BusinessException("Операция не поддерживается");
    }
}
