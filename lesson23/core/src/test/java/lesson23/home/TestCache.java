package lesson23.home;

import lesson23.home.models.CacheModel;
import lesson23.home.proxy.ProxyUtils;
import lesson23.home.service.Calculator;
import lesson23.home.service.CalculatorImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestCache {

    @Test
    public void test() {
        Calculator calculator = new CalculatorImpl();
        Calculator cachedCalculator = ProxyUtils.makeCached(calculator);

        System.out.println("Result:" + cachedCalculator.sum(10, 15));
        CacheModel sum = new CacheModel("sum_10_15");
        Assert.assertTrue(sum.getCacheByKey());
        int result = cachedCalculator.sum(10, 15);
        Assert.assertEquals(result, (int) sum.getValue().getValue());


        System.out.println("Result:" + cachedCalculator.multiple(2, 3));
        CacheModel multiple = new CacheModel("multiple_2_3");
        Assert.assertTrue(multiple.getCacheByKey());
        int resultM = cachedCalculator.multiple(2, 3);
        Assert.assertEquals(resultM, (int) multiple.getValue().getValue());
    }
}
