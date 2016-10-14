package lesson20.home;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = {
        TestWrongResult.class,
        TestAddConverter.class,
        TestBigDecimalConverter.class,
        TestFloatConverter.class,
        TestIntegerConverter.class,
        TestStringConverter.class
})
public class AllTests {
}
