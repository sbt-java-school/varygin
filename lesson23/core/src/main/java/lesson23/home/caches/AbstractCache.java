package lesson23.home.caches;

import java.util.Objects;

/**
 * Абстрактная модель кеширования данных
 * Все наследники должны содержать ключ кеша
 */
abstract class AbstractCache implements Cache {
    final String key;

    AbstractCache(String key) {
        this.key = Objects.requireNonNull(key);
    }
}
