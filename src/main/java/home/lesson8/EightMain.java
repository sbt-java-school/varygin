package home.lesson8;

import home.lesson8.utils.ProxyUtils;

/**
 * Main class for testing
 */
public class EightMain {
    public static void main(String[] args) throws Exception {
        Calculator calculator = new CalculatorImpl();
        Calculator cachedCalculator = ProxyUtils.makeCached(calculator);
        System.out.println(cachedCalculator.sum(10, 15));
        System.out.println(cachedCalculator.sum(10, 15));

        Thread.sleep(10);

        System.out.println(cachedCalculator.sum(12, 15));
        System.out.println(cachedCalculator.sum(10, 15));

        System.out.println(cachedCalculator.multiple(2, 3));
        System.out.println(cachedCalculator.multiple(2, 3));

        System.out.println(cachedCalculator.sum(10, 15));
    }
}
