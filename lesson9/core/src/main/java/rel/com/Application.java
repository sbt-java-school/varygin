package rel.com;

import home.work.MyIterator;
import home.work.MyIteratorImpl;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Some application to try MyIterator interface
 */
public class Application {
    public static void main(String[] args) {

        MyIterator<String> iterator = new MyIteratorImpl<>(
                new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4")));

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
