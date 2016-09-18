package lesson14.home.utils;

/**
 * Логирование сообщений / ошибок
 */
public class Log {
    public static void add(Thread currentThread, String message) {
        StringBuilder log = new StringBuilder("Log: ");
        log.append(currentThread.getName());
        log.append(" - ");
        log.append(message);
        System.out.println(log.toString());
    }
}
