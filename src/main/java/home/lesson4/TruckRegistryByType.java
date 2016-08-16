package home.lesson4;

import java.util.*;

/**
 *
 */
public class TruckRegistryByType implements TruckRegistry<Truck.TruckType, Truck> {

    private Map<Truck.TruckType, List<Truck>> truckRegistryByType = new HashMap<>();


    public TruckRegistryByType() {
    }

    @Override
    public List<Truck> get(Truck.TruckType type) {
        List<Truck> items = truckRegistryByType.get(type);
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Not found truck with type=" + type);
        }
        return items;
    }

    @Override
    public void put(Truck.TruckType key, Truck value) {
        if (truckRegistryByType.containsKey(key)) {
            truckRegistryByType.get(key).add(value);
        } else {
            truckRegistryByType.put(key, new ArrayList<>(Arrays.asList(value)));
        }
    }
}
