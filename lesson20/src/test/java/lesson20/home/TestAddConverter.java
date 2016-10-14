package lesson20.home;

import lesson20.home.converter.Converter;
import lesson20.home.converter.ConverterImpl;
import lesson20.home.utils.Person;
import lesson20.home.utils.PersonToStringConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAddConverter {
    private final Converter converter = new ConverterImpl();

    @Test
    public void test() throws Exception {
        converter.addConverterFrom(String.class, Person.class, new PersonToStringConverter());
        assertEquals("Ivan:10", converter.convert(new Person("Ivan", 10), String.class));
        assertEquals("Petr:25", converter.convert(new Person("Petr", 25), String.class));
    }
}
