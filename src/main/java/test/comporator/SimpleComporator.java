package test.comporator;

import java.util.Comparator;

/**
 * Created by LL on 23.08.2016.
 */
public class SimpleComporator implements Comparator<Test> {
    public int compare(Test o1, Test o2) {
        final int i = o1.compareTo(o2);
        System.out.println(o1.getFio() + " compare to " + o2.getFio() + " = " + i);
        return i;
    }
}
