package lesson14.home.utils;

/**
 * Интерфейс для запуска задачи.
 * Все задачи, запускаемые в пуле, должны имплементировать данный интерфейс.
 */
public interface Task {
    void start(String threadName) throws InterruptedException;
}
