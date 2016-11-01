package lesson24.db.components;

import lesson24.db.DaoFactory;
import lesson24.db.DatabaseMigrations;

public class TestMigrations {
    public static void main(String[] args) {
        /*try (DaoFactory daoFactory = new DaoFactory()){
            DatabaseMigrations migrations = daoFactory.create(DatabaseMigrations.class);
            migrations.needClear(true);
            migrations.migrate();
        }*/
    }
}
