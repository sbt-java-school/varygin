package lesson26.home.dao.core;

import lesson26.home.dao.UnitDao;
import lesson26.home.dao.schema.Unit;
import org.springframework.stereotype.Repository;

@Repository
public class UnitDaoImpl
        extends AbstractDao implements UnitDao {

    public UnitDaoImpl() {
        super(Unit.class);
    }
}
