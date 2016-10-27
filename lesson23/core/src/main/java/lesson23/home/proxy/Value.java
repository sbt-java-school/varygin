package lesson23.home.proxy;

import java.io.Serializable;

public class Value<V> implements Serializable {

    private static final long serialVersionUID = -2099062002474705458L;

    private long cachedTime;
    private V value;

    Value(long cachedTime, V value) {
        this.cachedTime = System.currentTimeMillis() + cachedTime;
        this.value = value;
    }

    boolean checkTime() {
        return System.currentTimeMillis() < cachedTime;
    }

    public V getValue() {
        return value;
    }
}
