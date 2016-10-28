package lesson23.home.service;


import lesson23.home.caches.Cached;

/**
 * Interface to add two numbers
 */
public interface Calculator {

    /**
     * Returns sum two numbers
     *
     * @param a - first number
     * @param b - second number
     * @return sum a + b
     */
    @Cached(5000L)
    int sum(int a, int b);

    /**
     * Return multiple two numbers
     *
     * @param a - first number
     * @param b - second number
     * @return multiple: a * b
     */
    @Cached(20_000L)
    int multiple(int a, int b);
}
