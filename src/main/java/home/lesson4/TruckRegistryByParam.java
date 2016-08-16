package home.lesson4;

import java.util.*;

/**
 * Class TruckRegistryByParam for create multimap
 * with List of some Objects implemented interface BigCars
 * and grouped by some parameter from the Object
 */
public class TruckRegistryByParam<T, E extends BigCars> implements TruckRegistry<T, E> {

    private Map<T, List<E>> truckRegistryByParam = new HashMap<>();

    public TruckRegistryByParam() {
    }

    /**
     * Multimap constructor
     * @param listDao list of some objects
     */
    public TruckRegistryByParam(List<E> listDao) {
        for (E item : listDao) {
            T type = (T) item.getField(); //ћожно ли как-то проверить соответствие типов?
            put(type, item);
        }
    }

    public void put(T key, E value) {
        if (truckRegistryByParam.containsKey(key)) {
            truckRegistryByParam.get(key).add(value);
        } else {
            truckRegistryByParam.put(key, new ArrayList<>(Arrays.asList(value)));
        }
    }

    /**
     * Print all items in multimap to console
     */
    void viewTruckRegistry() {
        for (Map.Entry<T, List<E>> truckListEntry : truckRegistryByParam.entrySet()){
            for (E item : truckListEntry.getValue()) {
                System.out.println(truckListEntry.getKey() + " " + item);
            }
        }
    }

    /**
     *
     * Search in multimap list of Object by param
     *
     * @param key to search list of Objects
     * @return list of Objects
     */
    public List<E> get(T key) {
        List<E> items = truckRegistryByParam.get(key);
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Not found truck with key=" + key);
        }
        return items;
    }
}
