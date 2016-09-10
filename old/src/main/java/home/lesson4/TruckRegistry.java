package home.lesson4;

import java.util.List;

/**
 * Created by LL on 16.08.2016.
 */
public interface TruckRegistry <T, V extends BigCars> {

    List<V> get(T key);
    void put(T key, V value);
}
