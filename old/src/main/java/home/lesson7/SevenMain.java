package home.lesson7;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by LL on 26.08.2016.
 */
public class SevenMain {

    public static final int MAX_SIZE = 1_000_000;

    public static void start() {
        final LinkedList<String> strings = new LinkedList<>();
        for (int i = 0; i < MAX_SIZE; i++) {
            strings.add("string" + i);
        }

        String str = null;
        for (int i = 0; i < MAX_SIZE; i++) {
            str = "str " + i;
        }

        final HashMap<Integer, String> map = new HashMap<>();
        int num = 0;
        for (int i = 0; i < MAX_SIZE; i++) {
            num = (int) getNewNum(i);
            map.put(num, strings.get(i));
        }

        for (int i = 0; i < MAX_SIZE / 2; i++) {
            map.remove(i);
        }
    }
    private static double getNewNum(int i) {
        return Math.random() * i + 10;
    }
}
