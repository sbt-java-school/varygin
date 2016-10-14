package lesson20.home;

import lesson20.home.converter.Converter;
import lesson20.home.converter.ConverterImpl;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestWrongResult {
    private final Converter converter = new ConverterImpl();

    @BeforeClass
    public static void classSetUp() {
        System.out.println("Тестируем ошибки");
    }

    @Test(expected = NullPointerException.class)
    public void test() throws Exception {
        converter.convert(10, TestWrongResult.class);
    }
}
