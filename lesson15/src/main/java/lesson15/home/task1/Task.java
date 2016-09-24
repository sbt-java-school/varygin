package lesson15.home.task1;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс Task принимает на вход функциональный интерфейс Callable
 * Метод T get() возвращает результат выполнения Callable функции, либо TaskException, в случае ошибки,
 *              либо пробрасывает исключения InterruptedException, если текущему потоку была передана комманда interrupt
 * @param <T> тип возвращаемого результата, вызываемой Callable функцией
 */
public class Task<T> {
    private Lock lock = new ReentrantLock();
    private final Callable<? extends T> callable;
    private T result;
    private TaskException exception;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws InterruptedException {
        if (exception != null) {
            throw exception;
        }
        if (result == null) {
            try {
                lock.lockInterruptibly();
                if (result != null) {
                    return result;
                }
                if (exception != null) {
                    throw exception;
                }
                result = callable.call();
            }catch (TaskException e) {
                throw e;
            } catch (Exception e) {
                exception = new TaskException(e);
                throw exception;
            } finally {
                lock.unlock();
            }
        }
        return result;
    }
}
