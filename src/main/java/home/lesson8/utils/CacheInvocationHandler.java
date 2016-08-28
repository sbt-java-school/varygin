package home.lesson8.utils;

import home.lesson8.Calculator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Cached Proxy Handler
 */
public class CacheInvocationHandler implements InvocationHandler {
    public static final String TIME_KEY = "time";
    public static final String VALUE_KEY = "value";
    private Calculator instance;

    public CacheInvocationHandler(Calculator instance) {
        this.instance = instance;
    }

    private static HashMap<String, HashMap<String, Number>> cacheMap = new HashMap<>();

    @Override
    public Integer invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int value;
        long cacheTime = getCacheTime(method);
        if (cacheTime != 0) {
            String key = getCacheKey(method, args);
            if (cacheMap.containsKey(key)) {
                HashMap<String, Number> valueMap = cacheMap.get(key);
                if (System.currentTimeMillis() < ((long) valueMap.get(TIME_KEY))) {
                    System.out.println("--Get sum from chache--");
                    return (int) valueMap.get(VALUE_KEY);
                } else {
                    System.out.println("--Clear cache--");
                    cacheMap.put(key, null);
                }
            }

            value = (int) method.invoke(instance, args);
            store(key, value, cacheTime);
        } else {
            value = (int) method.invoke(instance, args);
        }
        return value;
    }

    private void store(String key, int value, long cacheTime) {
        HashMap<String, Number> valueMap = new HashMap<>();
        valueMap.put(TIME_KEY, System.currentTimeMillis() + cacheTime);
        valueMap.put(VALUE_KEY, value);
        cacheMap.put(key, valueMap);
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
