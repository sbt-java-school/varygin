package lesson23.home.caches;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс обеспечивающий кеширование значений в памяти
 */
public class MemoryCache extends AbstractCache implements Cache {
    private Map<String, Value> cacheMap = new ConcurrentHashMap<>();

    public MemoryCache(String key) {
        super(key);
    }

    @Override
    public Optional<Value> get() {
        return Optional.ofNullable(cacheMap.get(key));
    }

    @Override
    public void save(Value value) {
        cacheMap.put(key, value);
    }
}
