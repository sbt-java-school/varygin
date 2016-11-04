package lesson24.db.components;

import lesson24.db.DaoFactory;
import org.junit.Ignore;
import org.junit.Test;

public class TestMigrations {
    @Test
    @Ignore
    public void migration() throws Exception {
        try (DaoFactory daoFactory = new DaoFactory()){
            DatabaseMigrationsDao migrationsDao = daoFactory.get(DatabaseMigrationsDao.class);
//            migrationsDao.needClear(true);
            migrationsDao.migrate();
        }
    }
}
