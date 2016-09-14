package ru.sbt;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {

    @Test
    public void testFilter() throws Exception {
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("test1", "te", "tytyy1gasd", "87654"));
        ArrayList<String> list = new ArrayList<>();
        Streams.of(strings)
                .filter(s -> s.length() > 2 && s.contains("1"))
                .forEach(list::add);

        assertEquals("test1", list.get(0));
        assertTrue(list.size() == 2);
    }

    @Test
    public void testMapping() throws Exception {
        Truck[] trucks = {
                new Truck(10, "Test1"),
                new Truck(15, "TEst564"),
                new Truck(36, "Test025")
        };
        Map<String, Integer> map = Streams.of(trucks)
                .filter(t -> t.getCapacity() > 10)
                .transform(t -> (new Truck(t.getCapacity() + 50, t.getName())))
                .toMap(Truck::getName, Truck::getCapacity);

        assertTrue(map.size() == 2);
        Truck truck = trucks[1];
        assertEquals(truck.getCapacity() + 50, (int) map.get(truck.getName()));
    }
}
