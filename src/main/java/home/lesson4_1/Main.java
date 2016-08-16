package home.lesson4_1;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by LL on 15.08.2016.
 */
public class Main {
    public static void main(String[] args) {

        MyList<Integer> myList = new MyLinkedList<>();
        myList.add(10);
        myList.add(31);
        myList.add(12);
        myList.add(1);

        myList.remove(2);

        myList.add(1, 5);

        myList.addAll(Arrays.asList(
                5651,
                23423
        ));

        Iterator<Integer> iterator = myList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        MyList<Integer> myList1 = new MyLinkedList<>();
        myList1.copy(Arrays.asList(51, 556, 897, 879));

        Iterator<Integer> iterator1 = myList1.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());
        }
    }
}
