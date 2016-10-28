package lesson23.home.proxy;


import lesson23.home.service.Calculator;

import java.lang.reflect.Proxy;

/**
 * Class helper
 */
public class ProxyUtils {
    /**
     * Create proxy instance
     *
     * @param classInstance instance of class realization
     * @return cached proxy instance
     */
    public static Calculator makeCached(Calculator classInstance) {
        return (Calculator) Proxy.newProxyInstance(
                ProxyUtils.class.getClassLoader(),
                new Class<?>[]{Calculator.class},
                new CacheHandler(classInstance));
    }
}
