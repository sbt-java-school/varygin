package home.lesson8;

import home.lesson8.utils.Cache;

/**
 * Interface to add two numbers
 */
@Cache(10L)
public interface Calculator {

    /**
     * Returns sum two numbers
     *
     * @param a - first number
     * @param b - second number
     * @return sum a + b
     */
    int sum(int a, int b);

    /**
     * Return multiple two numbers
     * @param a - first number
     * @param b - second number
     * @return multiple: a * b
     */
    int multiple(int a, int b);
}
