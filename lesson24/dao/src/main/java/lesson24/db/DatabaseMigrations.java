package lesson24.db;

/**
 * Интерфейс для осуществления миграций БД
 */
public interface DatabaseMigrations {
    void migrate();

    void needClear(boolean needClear);
}
