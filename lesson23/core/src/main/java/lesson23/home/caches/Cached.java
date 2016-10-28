package lesson23.home.caches;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация-метка для обозначения методов / классов, которые необходимо кешировать
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {
    /**
     * Время кеширования в милисекундах
     */
    long value();
}
