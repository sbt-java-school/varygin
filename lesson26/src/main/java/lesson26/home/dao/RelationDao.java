package lesson26.home.dao;

import lesson26.home.dao.core.RelationDaoImpl;
import lesson26.home.utils.BusinessException;
import lesson26.home.utils.Page;
import lesson26.home.utils.Request;

import java.util.List;

/**
 * Интерфейс для взаимодействия с таблицей отношения
 * рецептов и ингредиентов
 *
 * @see RelationDaoImpl
 */
public interface RelationDao extends EntityDao {
    @Override
    default Page<Object> getList(String subName, Request request) {
        throw new BusinessException("Операция не поддерживается");
    }
}
