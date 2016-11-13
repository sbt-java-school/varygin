package lesson26.home.dao.core;

import lesson26.home.dao.EntityDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

public abstract class AbstractDao implements EntityDao {
    @PersistenceContext
    EntityManager entityManager;
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
    public List getList() {
        return entityManager
                .createQuery("select e from "
                        + entity.getCanonicalName() + " e")
                .getResultList();
    }
}
