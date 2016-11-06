package lesson23.home.caches;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация-метка для обозначения методов / классов, которые необходимо кешировать
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {
    /**
     * Время кеширования в милисекундах
     */
    long value();
}
