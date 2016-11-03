package lesson24.db.components;

import lesson24.db.Config;
import lesson24.db.DaoFactory;
import lesson24.db.configuration.JdbcConfig;
import org.junit.Ignore;
import org.junit.Test;

public class TestMigrations {
    @Test
    public void dbConfig() throws Exception {
        Config config = JdbcConfig.getInstance();
        System.out.println(config.getConnectionURL());
    }

    @Test
    @Ignore
    public void migration() throws Exception {
        try (DaoFactory daoFactory = new DaoFactory()){
            DatabaseMigrationsDao migrationsDao = daoFactory.create(DatabaseMigrationsDao.class);
//            migrationsDao.needClear(true);
            migrationsDao.migrate();
        }
    }
}
