package lesson24.db.components;

import lesson24.db.DaoFactory;
import lesson24.db.DatabaseMigrations;
import lesson24.db.shema.Ingredient;

public class TestMigrations {
    public static void main(String[] args) {
        try (DaoFactory daoFactory = new DaoFactory()){
            DatabaseMigrationsDao migrationsDao = daoFactory.create(DatabaseMigrationsDao.class);
            migrationsDao.needClear(true);
            migrationsDao.migrate();
        }
    }
}
