package lesson26.home.utils;

@FunctionalInterface
public interface AjaxCall<V> {
    V call() throws BusinessException;
}
