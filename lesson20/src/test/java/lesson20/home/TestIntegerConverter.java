package lesson20.home;

import lesson20.home.converter.Converter;
import lesson20.home.converter.ConverterImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

/**
 * Тесты для проверки конвертора из стандартных типов в Integer
 */
@RunWith(Parameterized.class)
public class TestIntegerConverter {
    private final Converter converter = new ConverterImpl();

    @BeforeClass
    public static void classSetUp() {
        System.out.println("Тестируем конвертацию Integer");
    }

    @Parameter
    public int expected;

    @Parameter(1)
    public Object valueFrom;

    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {10, "10"},
                {554, new Long(554)},
                {28, new Integer(28)},
                {33, new Float(33)},
                {123, new Double(123)},
                {55, new BigDecimal(55)}
        });
    }

    @Test
    public void test() {
        assertEquals(new Integer(expected), converter.convert(valueFrom, Integer.class));
        assertEquals(Integer.class, converter.convert(valueFrom, Integer.class).getClass());
    }

    @Test
    public void toAll() throws Exception {
        assertEquals(valueFrom, converter.convert(new Integer(expected), valueFrom.getClass()));
        assertEquals(valueFrom.getClass(), converter.convert(new Integer(expected), valueFrom.getClass()).getClass());
    }
}
