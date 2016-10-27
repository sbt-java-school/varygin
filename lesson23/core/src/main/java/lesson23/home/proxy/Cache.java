package lesson23.home.proxy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for caching data
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    /**
     * time to store value in cache (in milliseconds)
     */
    long value();
}
