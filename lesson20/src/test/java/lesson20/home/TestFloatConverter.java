package lesson20.home;

import lesson20.home.converter.Converter;
import lesson20.home.converter.ConverterImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestFloatConverter {
    private final Converter converter = new ConverterImpl();

    @BeforeClass
    public static void classSetUp() {
        System.out.println("Тестируем конвертацию Float");
    }

    @Parameterized.Parameter
    public float expected;

    @Parameterized.Parameter(1)
    public Object valueFrom;

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {554.23f, "554.23"},
                {554.0f, new Long(554)},
                {28.0f, new Integer(28)},
                {33.0f, new Float(33)},
                {123.25564f, new Double(123.2556381225586)}
        });
    }

    @Test
    public void test() {
        assertEquals(new Float(expected), converter.convert(valueFrom, Float.class));
        assertEquals(Float.class, converter.convert(valueFrom, Float.class).getClass());
    }

    @Test
    public void toAll() throws Exception {
        assertEquals(valueFrom, converter.convert(new Float(expected), valueFrom.getClass()));
        assertEquals(valueFrom.getClass(), converter.convert(new Float(expected), valueFrom.getClass()).getClass());
    }
}
