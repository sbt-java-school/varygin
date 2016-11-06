package lesson23.home.service;


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
    int sum(int a, int b);

    /**
     * Return multiple two numbers
     *
     * @param a - first number
     * @param b - second number
     * @return multiple: a * b
     */
    int multiple(int a, int b);
}
