package lesson26.home.dao.core;

import lesson26.home.dao.RelationDao;
import lesson26.home.dao.entities.Relation;
import org.springframework.stereotype.Repository;

@Repository
public class RelationDaoImpl
        extends AbstractDao implements RelationDao {

    public RelationDaoImpl() {
        super(Relation.class);
    }
}
