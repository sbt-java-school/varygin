package home.lesson4;

import java.util.*;

/**
 * Created by LL on 14.08.2016.
 */
public class TruckRegistryByParam<T> {
    private Map<T, List<Truck>> truckRegistryByType = new HashMap<>();

    public TruckRegistryByParam(TruckDao truckDao) {
        List<Truck> list = truckDao.list();

        for (Truck truck : list) {
            T type = (T) truck.getField();
            if (truckRegistryByType.containsKey(type)) {
                truckRegistryByType.get(type).add(truck);
            } else {
                truckRegistryByType.put(type, new ArrayList<>(Arrays.asList(truck)));
            }
        }
    }

    void viewTruckRegistry() {
        for (Map.Entry<T, List<Truck>> truckListEntry : truckRegistryByType.entrySet()){
            for (Truck truck : truckListEntry.getValue()) {
                System.out.println(truckListEntry.getKey() + " " + truck);
            }
        }
    }

    public List<Truck> getTrucksByType(T type) {
        List<Truck> trucks = truckRegistryByType.get(type);
        if (trucks == null || trucks.isEmpty()) {
            throw new IllegalArgumentException("Not found truck with type=" + type);
        }
        return trucks;
    }
}
