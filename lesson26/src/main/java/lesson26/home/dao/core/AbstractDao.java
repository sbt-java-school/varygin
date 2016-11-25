package lesson26.home.dao.core;

import lesson26.home.dao.EntityDao;
import lesson26.home.dao.entities.Ingredient;
import lesson26.home.dao.entities.Recipe;
import lesson26.home.dao.entities.Relation;
import lesson26.home.dao.entities.Unit;
import lesson26.home.utils.Page;
import lesson26.home.utils.Request;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

/**
 * Абстрактный класс для реализации общего функционала
 * по доступу к таблицам БД
 */
public abstract class AbstractDao implements EntityDao {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Класс объекта, представляющий таблицу в БД
     *
     * @see Recipe
     * @see Ingredient
     * @see Relation
     * @see Unit
     */
    private Class<?> entity;

    public AbstractDao() {
    }

    AbstractDao(Class<?> entity) {
        this.entity = entity;
    }

    @Override
    public Object save(Object object) {
        return entityManager.merge(object);
    }

    @Override
    public Object getById(Long id) {
        return entityManager.find(entity, id);
    }

    @Override
    public void delete(Object object) {
        Objects.requireNonNull(object);
        entityManager.remove(object);
    }

    @Override
    public Page<Object> getList(String name, Request request) {
        String entityName = entity.getSimpleName() + " e";
        StringBuilder sqlList = new StringBuilder("select e from " + entityName);
        StringBuilder sqlCount = new StringBuilder("select count(e) from " + entityName);
        String sqlWhere = " where lower(e.name) like :name";
        boolean withFilter;

        if (withFilter = StringUtils.isNotBlank(name)) {
            sqlList.append(sqlWhere);
            sqlCount.append(sqlWhere);
        }

        sqlList.append(" order by e.id desc");

        Query listQuery = listQuery(request, sqlList.toString());
        TypedQuery<Long> countQuery = entityManager.createQuery(sqlCount.toString(), Long.class);

        if (withFilter) {
            String wrappedName = "%" + name.toLowerCase() + "%";
            listQuery.setParameter("name", wrappedName);
            countQuery.setParameter("name", wrappedName);
        }

        @SuppressWarnings("unchecked")
        List<Object> resultList = listQuery.getResultList();
        Long totalCount = countQuery.getSingleResult();

        return new Page<>(resultList, totalCount);
    }

    private Query listQuery(Request request, String sqlString) {
        Query query = entityManager.createQuery(sqlString);
        if (request.getPageSize() != null) {
            query.setMaxResults(request.getPageSize());
        }
        if (request.getFirstResult() != null) {
            query.setFirstResult(request.getFirstResult());
        }
        return query;
    }
}
