package lesson23.home.service;

import lesson23.home.caches.Cached;

/**
 * Some realization of interface <code>Calculator</code>
 */
public class CalculatorImpl implements Calculator {
    @Override
    @Cached(5000L)
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    @Cached(20_000L)
    public int multiple(int a, int b) {
        return a * b;
    }

    private void ini() {

    }
}
