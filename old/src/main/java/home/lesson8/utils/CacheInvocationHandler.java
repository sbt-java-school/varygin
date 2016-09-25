package home.lesson8.utils;

import home.lesson8.Calculator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cached Proxy Handler
 */
public class CacheInvocationHandler <T> implements InvocationHandler {
    private Calculator instance;
    private Map<String, Values<T>> cacheMap = new ConcurrentHashMap<>();
    public CacheInvocationHandler(Calculator instance) {
        this.instance = instance;
    }

    private static class Values <V> {
        private long time;
        private V value;

        public Values(long time, V value) {
            this.time = time;
            this.value = value;
        }

        public boolean checkTime() {
            return System.currentTimeMillis() < time;
        }

        public V getValue() {
            return value;
        }
    }

    @Override
    public T invoke(Object proxy, Method method, Object[] args) throws Throwable {
        T value;
        long cacheTime = getCacheTime(method);
        if (cacheTime != 0) {
            String key = getCacheKey(method, args);
            if (cacheMap.containsKey(key)) {
                if (cacheMap.get(key).checkTime()) {
                    System.out.println("--Get value from chache--");
                    return (T) cacheMap.get(key).getValue();
                } else {
                    System.out.println("--Clear cache for one variable--");
                    cacheMap.remove(key);
                }
            }

            value = (T) method.invoke(instance, args);
            cacheMap.put(key, new Values<T>(System.currentTimeMillis() + cacheTime, value));
        } else {
            value = (T) method.invoke(instance, args);
        }
        return value;
    }

    private String getCacheKey(Method method, Object[] args) {
        StringBuilder tmp = new StringBuilder();
        tmp.append(method.getDeclaringClass().getSimpleName());
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg == null) {
                    throw new IllegalArgumentException("Wring argument");
                }
                tmp.append("_").append(arg.toString());
            }
        }
        return tmp.toString();
    }

    private long getCacheTime(Method method) {
        Cache annotation = method.getAnnotation(Cache.class);
        if (annotation == null &&
                (annotation = method.getDeclaringClass().getAnnotation(Cache.class)) == null &&
                (annotation = instance.getClass().getAnnotation(Cache.class)) == null) {
            return 0;
        } else {
            return annotation.value();
        }
    }
}
