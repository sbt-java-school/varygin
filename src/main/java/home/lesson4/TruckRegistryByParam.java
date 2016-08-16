package home.lesson4;

import java.util.*;

/**
 * Class TruckRegistryByParam for create multimap
 * with List of some Objects implemented interface BigCars
 * and grouped by some parameter from the Object
 */
public class TruckRegistryByParam<E extends BigCars, T> {

    private Map<T, List<E>> truckRegistryByParam = new HashMap<>();


    /**
     * Multimap constructor
     * @param listDao list of some objects
     */
    public TruckRegistryByParam(List<E> listDao) {
        for (E item : listDao) {

            T type = (T) item.getField(); // ак проверить на соответствие типов?

            if (truckRegistryByParam.containsKey(type)) {
                truckRegistryByParam.get(type).add(item);
            } else {
                truckRegistryByParam.put(type, new ArrayList<>(Arrays.asList(item)));
            }
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
     * @param type to search list of Objects
     * @return list of Objects
     */
    public List<E> getByParam(T type) {
        List<E> items = truckRegistryByParam.get(type);
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Not found truck with type=" + type);
        }
        return items;
    }
}
