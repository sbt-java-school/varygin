package test.comporator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by LL on 23.08.2016.
 */
public class Application {
    public static void main(String[] args) {
        ArrayList<Test> tests = new ArrayList<>(Arrays.asList(
                new Test("Вова", 26, 175d),
                new Test("Миша", 45, 180d),
                new Test("Вова", 26, 175.2),
                new Test("Петя", 60, 145.6)
        ));

        Collections.sort(tests);
        for (Test test : tests) {
            System.out.println(test);
        }
        System.out.println();

        Collections.sort(tests, new SimpleComporator());
        for (Test test : tests) {
            System.out.println(test);
        }

    }
}
