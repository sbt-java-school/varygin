package lesson23.home.proxy;

import lesson23.home.caches.Cached;
import lesson23.home.caches.FullCache;
import lesson23.home.caches.Value;
import lesson23.home.service.Calculator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Кеширующий прокси: проверяет наличие закешированного значения по ключу.
 * Ключ формируется из названия вызванного метода и значений его аргуметнов.
 * Если значения нет или нет необходимости кешировать метод - вычисляется новое значение.
 * Если результат есть в кеше памяти и время хранения в кеше не истекло - возвращается значение из кеша памяти.
 * Если результата нет в кеше памяти, но есть в кеше БД и время кеширования не истекло - возвращается значение из кеша БД.
 * Иначе результат расчитывается заново.
 */
class CacheHandler implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvocationHandler.class);

    private Calculator instance;

    CacheHandler(Calculator instance) {
        this.instance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Optional<Value> cacheValue = getValueFromCache(method, args);
        if (cacheValue.isPresent()) {
            return cacheValue.get().getValue();
        }
        return method.invoke(instance, args);
    }

    private Optional<Value> getValueFromCache(Method method, Object[] args) throws Throwable {
        long cacheTime = getCacheTime(method);
        Value value = null;
        if (cacheTime != 0) {
            String key = getCacheKey(method, args);
            FullCache fullCache = new FullCache(key);
            Optional<Value> fromCache = fullCache.get();
            if (fromCache.isPresent() && fromCache.get().checkTime()) {
                LOGGER.debug("Get from cache");
                return fromCache;
            }
            LOGGER.debug("Calculate");
            value = new Value(cacheTime, method.invoke(instance, args));
            fullCache.save(value);
        }
        return Optional.ofNullable(value);
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
        Cached annotation = method.getAnnotation(Cached.class);
        if (annotation == null &&
                (annotation = method.getDeclaringClass().getAnnotation(Cached.class)) == null &&
                (annotation = instance.getClass().getAnnotation(Cached.class)) == null) {
            return 0;
        }
        return annotation.value();
    }
}
