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

@RunWith(Parameterized.class)
public class TestStringConverter {
    private final Converter converter = new ConverterImpl();

    @BeforeClass
    public static void classSetUp() {
        System.out.println("Тестируем конвертацию String");
    }

    @Parameterized.Parameter
    public String expected;

    @Parameterized.Parameter(1)
    public Object valueFrom;

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"blablabla", "blablabla"},
                {"554", new Long(554)},
                {"28", new Integer(28)},
                {"33.0", new Float(33)},
                {"123.0", new Double(123)},
                {"55", new BigDecimal(55)}
        });
    }

    @Test
    public void test() {
        assertEquals(expected, converter.convert(valueFrom, String.class));
        assertEquals(String.class, converter.convert(valueFrom, String.class).getClass());
    }

    @Test
    public void toAll() throws Exception {
        assertEquals(valueFrom, converter.convert(expected, valueFrom.getClass()));
        assertEquals(valueFrom.getClass(), converter.convert(expected, valueFrom.getClass()).getClass());
    }
}
