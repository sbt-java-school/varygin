package home.lesson4_1;

import java.util.Iterator;
import java.util.LinkedList;

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

        myList.add(3, 5);


        Iterator<Integer> iterator = myList.iterator();
        while (iterator.hasNext()) {
            int item = iterator.next();
            System.out.println(item);
        }
    }
}
