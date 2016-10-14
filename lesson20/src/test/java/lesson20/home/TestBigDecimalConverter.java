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
public class TestBigDecimalConverter {
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
                {"5464987954654987946459879464975498684", "5464987954654987946459879464975498684"},
                {"554654654", new Long(554654654)},
                {"28211", new Integer(28211)},
                {"334564.0", new Float(334564)},
                {"123.0", new Double(123)},
                {"55654654654654", new BigDecimal("55654654654654")}
        });
    }

    @Test
    public void test() {
        assertEquals(new BigDecimal(expected), converter.convert(valueFrom, BigDecimal.class));
        assertEquals(BigDecimal.class, converter.convert(valueFrom, BigDecimal.class).getClass());
    }

    @Test
    public void toAll() throws Exception {
        assertEquals(valueFrom, converter.convert(new BigDecimal(expected), valueFrom.getClass()));
        assertEquals(valueFrom.getClass(), converter.convert(new BigDecimal(expected), valueFrom.getClass()).getClass());
    }
}
