package home.lesson8;

/**
 * Some realization of interface <code>Calculator</code>
 */
public class CalculatorImpl implements Calculator {
    @Override
    public int sum(int a, int b) {
        System.out.println("--Calculated sum in the method--");
        return a + b;
    }

    @Override
    public int multiple(int a, int b) {
        System.out.println("--Calculated multiple in the method--");
        return a * b;
    }
}
