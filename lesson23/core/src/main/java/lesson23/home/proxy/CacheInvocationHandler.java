package lesson23.home.proxy;

import lesson23.home.models.CacheModel;
import lesson23.home.service.Calculator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cached Proxy Handler
 */
class CacheInvocationHandler<T> implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvocationHandler.class);

    private Calculator instance;
    private Map<String, Value<T>> cacheMap = new ConcurrentHashMap<>();

    CacheInvocationHandler(Calculator instance) {
        this.instance = instance;
    }

    @Override
    public T invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long cacheTime = getCacheTime(method);
        if (cacheTime != 0) {
            String key = getCacheKey(method, args);
            Optional<T> fromCache = getFromCache(key);
            if (fromCache.isPresent()) {
                return fromCache.get();
            }
            @SuppressWarnings("unchecked")
            T value = (T) method.invoke(instance, args);
            saveCache(value, cacheTime, key);
        }
        @SuppressWarnings("unchecked")
        T value = (T) method.invoke(instance, args);
        return value;
    }

    private void saveCache(T value, long cacheTime, String key) {
        Value<T> memValue = new Value<>(cacheTime, value);
        cacheMap.put(key, memValue);

        CacheModel<T> model = new CacheModel<>(key, memValue);
        if (model.getCacheByKey()) {
            LOGGER.debug("Update DB cache");
            model.setUpdate_at(new Timestamp(new Date().getTime()));
        }
        model.store();
    }

    private Optional<T> getFromCache(String key) {
        Objects.requireNonNull(key);
        if (cacheMap.containsKey(key)) {
            return Optional.ofNullable(memCache(key));
        }
        return Optional.ofNullable(dbCache(key));
    }

    private T dbCache(String key) {
        CacheModel<T> model = new CacheModel<>(key);
        if (model.getCacheByKey()) {
            LOGGER.debug("Get value from DB");
            Value<T> modelValue = model.getValue();
            if (modelValue.checkTime()) {
                cacheMap.put(key, modelValue);
                return modelValue.getValue();
            }
        }
        return null;
    }

    private T memCache(String key) {
        if (cacheMap.get(key).checkTime()) {
            LOGGER.debug("get from memory cache");
            return cacheMap.get(key).getValue();
        } else {
            LOGGER.debug("clear cache");
            cacheMap.remove(key);
        }
        return null;
    }

    private String getCacheKey(Method method, Object[] args) {
        StringBuilder tmp = new StringBuilder();
        tmp.append(method.getName());
        if (args != null && args.length > 0) {
            tmp.append("_").append(StringUtils.join(args, "_"));
        }
        return tmp.toString().toLowerCase();
    }

    private long getCacheTime(Method method) {
        Cache annotation = method.getAnnotation(Cache.class);
        if (annotation == null &&
                (annotation = method.getDeclaringClass().getAnnotation(Cache.class)) == null &&
                (annotation = instance.getClass().getAnnotation(Cache.class)) == null) {
            return 0;
        }
        return annotation.value();
    }
}
