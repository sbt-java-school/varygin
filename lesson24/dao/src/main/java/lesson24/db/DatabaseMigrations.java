package lesson24.db;

public interface DatabaseMigrations {
    void migrate();
    void needClear(boolean needClear);
}
