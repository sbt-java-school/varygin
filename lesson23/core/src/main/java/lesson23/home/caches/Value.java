package lesson23.home.caches;

import java.io.Serializable;

/**
 * Контейнер для хранения кешируемого значения
 */
public class Value implements Serializable {

    private static final long serialVersionUID = -2099062002474705458L;

    private final long cachedTime;
    private final Object value;

    public Value(long cachedTime, Object value) {
        this.cachedTime = System.currentTimeMillis() + cachedTime;
        this.value = value;
    }

    /**
     * Проверка на истечение срока хранения значения в кеше
     *
     * @return true если время не истекло, false - иначе
     */
    public boolean checkTime() {
        return System.currentTimeMillis() < cachedTime;
    }

    public Object getValue() {
        return value;
    }
}
