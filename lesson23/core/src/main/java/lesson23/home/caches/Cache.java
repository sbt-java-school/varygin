package lesson23.home.caches;

import java.util.Optional;

/**
 * Единый интерфейс для кеширования данных при помощи разных структур (в памяти, в базе данных)
 */
public interface Cache {
    /**
     * Получение результата из кеша
     * @return Опциональное значение
     */
    Optional<Value> get();

    /**
     * Сохранение нового значения в кеше
     * @param value - объект значения (содержит время кеширования и само значение)
     */
    void save(Value value);
}
