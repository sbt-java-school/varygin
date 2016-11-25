package lesson26.home.dao.core;

import lesson26.home.dao.UnitDao;
import lesson26.home.dao.entities.Unit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UnitDaoImpl
        extends AbstractDao implements UnitDao {

    public UnitDaoImpl() {
        super(Unit.class);
    }

    @Override
    public boolean isUsed(Unit unit) {
        String qlString = "select 1 from Relation r where r.unit = :unit";
        List<Integer> result = entityManager
                .createQuery(qlString, Integer.class)
                .setParameter("unit", unit)
                .setMaxResults(1)
                .getResultList();
        return result.size() == 1;
    }
}
