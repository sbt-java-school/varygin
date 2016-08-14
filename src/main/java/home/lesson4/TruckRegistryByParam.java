package home.lesson4;

import java.util.*;

/**
 * Created by LL on 14.08.2016.
 */
public class TruckRegistryByParam<E extends BigCars, T> {

    private Map<T, List<E>> truckRegistryByParam = new HashMap<>();

    public TruckRegistryByParam(List<E> listDao) {
        for (E item : listDao) {

            T type = (T) item.getField(); //???

            if (truckRegistryByParam.containsKey(type)) {
                truckRegistryByParam.get(type).add(item);
            } else {
                truckRegistryByParam.put(type, new ArrayList<>(Arrays.asList(item)));
            }
        }
    }

    void viewTruckRegistry() {
        for (Map.Entry<T, List<E>> truckListEntry : truckRegistryByParam.entrySet()){
            for (E item : truckListEntry.getValue()) {
                System.out.println(truckListEntry.getKey() + " " + item);
            }
        }
    }

    public List<E> getTrucksByType(T type) {
        List<E> trucks = truckRegistryByParam.get(type);
        if (trucks == null || trucks.isEmpty()) {
            throw new IllegalArgumentException("Not found truck with type=" + type);
        }
        return trucks;
    }
}
