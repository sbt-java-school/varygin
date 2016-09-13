package ru.sbt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class AppTest {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>(
                Arrays.asList("123", "1", "12314", "23")
        );
        Streams.of(strings).filter(s -> s.length() > 2).forEach(System.out::println);

        Map<String, Integer> map = Streams.of(new Truck(10, "Test1"), new Truck(15, "TEst564"), new Truck(36, "Test025"))
                .filter(t -> t.getCapacity() > 10)
                .transform(t -> (new Truck(t.getCapacity() + 50, t.getName())))
                .toMap(Truck::getName, Truck::getCapacity);

        for (String s : map.keySet()) {
            System.out.println(s + " " + map.get(s));
        }
    }
}
