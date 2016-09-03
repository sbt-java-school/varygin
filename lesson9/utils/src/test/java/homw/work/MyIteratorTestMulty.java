package homw.work;


import home.work.MyIterator;
import home.work.MyIteratorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class MyIteratorTestMulty {

    @BeforeClass
    public static void classSetUp() {
        System.out.println("MyIteratorTestMulty: Start");
    }

    @Parameter
    public Object expected;

    @Parameter(1)
    public List<Object> a;

    private MyIterator<Object> iterator;

    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"test1", Arrays.asList("test1", "test2", "test3", "test4")},
                {1, Arrays.asList(1, 2, 3, 4, 5, 6)},
                {new Person("Ivan", 25), Arrays.asList(
                        new Person("Ivan", 25),
                        new Person("Petr", 30),
                        new Person("SomeOne", 45)
                )}
        });
    }

    @Test
    public void testIterate() {
        iterator = new MyIteratorImpl<>(a);
        Assert.assertEquals("Check equals", expected, iterator.next());
    }
}
