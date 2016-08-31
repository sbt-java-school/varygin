package home.lesson8.utils;

import home.lesson8.Calculator;
import home.lesson8.EightMain;

import java.lang.reflect.Proxy;

/**
 * Class helper
 */
public class ProxyUtils {
    /**
     * Create proxy instance
     * @param classInstance instance of class realization
     * @return cached proxy instance
     */
    public static Calculator makeCached(Calculator classInstance) {
        return (Calculator) Proxy.newProxyInstance(
                EightMain.class.getClassLoader(),
                new Class<?>[]{Calculator.class},
                new CacheInvocationHandler<Integer>(classInstance));
    }
}
