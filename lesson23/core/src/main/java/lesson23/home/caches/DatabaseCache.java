package lesson23.home.caches;

import lesson23.home.models.CacheModel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

/**
 * Класс обеспечивающий кеширование значений в базе данных
 */
public class DatabaseCache extends AbstractCache implements Cache {
    private final CacheModel model;

    public DatabaseCache(String key) {
        super(key);
        model = new CacheModel(key);
    }

    @Override
    public Optional<Value> get() {
        Value value = null;
        if (model.getCacheByKey()) {
            value = model.getValue();
        }
        return Optional.ofNullable(value);
    }

    @Override
    public void save(Value value) {
        model.setValue(value);
        if (model.getId() != null || model.getCacheByKey()) {
            model.setUpdate_at(new Timestamp(new Date().getTime()));
        }
        model.store();
    }
}
