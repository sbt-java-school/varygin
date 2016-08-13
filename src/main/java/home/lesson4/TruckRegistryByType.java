package home.lesson4;

import java.util.*;

/**
 * Created by LL on 13.08.2016.
 */
public class TruckRegistryByType {
    private Map<Truck.TruckType, List<Truck>> truckRegistryByType = new HashMap<>();

    public TruckRegistryByType(TruckDao truckDao) {
        List<Truck> list = truckDao.list();

        for (Truck truck : list) {
            Truck.TruckType type = truck.getType();
            if (truckRegistryByType.containsKey(type)) {
                truckRegistryByType.get(type).add(truck);
            } else {
                truckRegistryByType.put(type, new ArrayList<>(Arrays.asList(truck)));
            }
        }
    }

    void viewTruckRegistry() {
        for (Map.Entry<Truck.TruckType, List<Truck>> truckListEntry : truckRegistryByType.entrySet()){
            for (Truck truck : truckListEntry.getValue()) {
                System.out.println(truckListEntry.getKey() + " " + truck);
            }
        }
    }

    public List<Truck> getTrucksByType(Truck.TruckType type) {
        List<Truck> trucks = truckRegistryByType.get(type);
        if (trucks == null || trucks.isEmpty()) {
            throw new IllegalArgumentException("Not found truck with type=" + type);
        }
        return trucks;
    }
}
