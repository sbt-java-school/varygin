package homw.work;

import home.work.MyIterator;
import home.work.MyIteratorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class MyIteratorTest {

    @BeforeClass
    public static void classSetUp() {
        System.out.println("MyIteratorTest: Start");
    }

    @Test(expected = NoSuchElementException.class)
    public void testNext() {
        MyIterator<String> iterator = new MyIteratorImpl<>(null);
        iterator.next();
    }

    @Test
    public void testHasNext() {
        MyIteratorImpl<String> iterator = new MyIteratorImpl<>(null);
        assertFalse("Check for empty result", iterator.hasNext());

        List<String> items = Arrays.asList("str", "str2");

        iterator = new MyIteratorImpl<>(items);
        assertTrue("Check for not empty result", iterator.hasNext());
    }
}
