package lesson24.services;

import lesson24.db.DaoFactory;
import lesson24.db.components.DatabaseMigrationsDao;

public class MainService {
    public static void createTables() {
        try (DaoFactory daoFactory = new DaoFactory()) {
            DatabaseMigrationsDao migrationsDao = daoFactory.get(DatabaseMigrationsDao.class);
            migrationsDao.migrate();
        }
    }
}
