package lesson26.home.dao;

import lesson26.home.utils.BusinessException;

import java.util.List;

public interface RelationDao extends EntityDao {
    @Override
    default List getList() {
        throw new BusinessException("Операция не поддерживается");
    }
}
