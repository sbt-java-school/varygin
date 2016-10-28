package lesson23.home.caches;

import java.util.Optional;

/**
 * Класс для композиции всех методов кеширования
 * в обном месте для предоставления единого интерфейса
 */
public class FullCache extends AbstractCache implements Cache {
    private final Cache memCache;
    private final Cache dbCache;

    public FullCache(String key) {
        super(key);
        this.memCache = new MemoryCache(key);
        this.dbCache = new DatabaseCache(key);
    }

    @Override
    public Optional<Value> get() {
        Optional<Value> value = memCache.get();
        if (!value.isPresent()) {
            value = dbCache.get();
        }
        return value;
    }

    @Override
    public void save(Value value) {
        memCache.save(value);
        dbCache.save(value);
    }
}
