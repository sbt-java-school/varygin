package lesson23.home.service;

/**
 * Some realization of interface <code>Calculator</code>
 */
public class CalculatorImpl implements Calculator {
    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public int multiple(int a, int b) {
        return a * b;
    }
}
