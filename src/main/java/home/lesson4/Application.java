package home.lesson4;

import java.util.*;

/**
 * Created by LL on 13.08.2016.
 */
public class Application {
    public static final Truck.TruckType TYPE = Truck.TruckType.KAMAZ;
    private Map<Long, Truck> truckRegistry = new TreeMap<>();

    public Application(TruckDao truckDao) {
        List<Truck> list = truckDao.listByTypes();
        for (Truck truck : list) {
            Truck previouseValue = truckRegistry.put(truck.getId(), truck);
            if (null != previouseValue) {
                throw new IllegalStateException("Two trucks with same id");
            }
        }

    }

    void viewTruckRegistry() {
        for (Map.Entry<Long, Truck> truckEntry : truckRegistry.entrySet()){
            System.out.println(truckEntry.getKey() + ": " + truckEntry.getValue());
        }
    }

    public Truck getTruckbyId(long truckId) {
        Truck truck = truckRegistry.get(truckId);
        if (truck == null) {
            throw new IllegalArgumentException("Not found truck with id=" + truckId);
        }
        return truck;
    }
    public static void main(String[] args) {
        TruckDao truckDao = new TruckDaoMemotyImpl();

        TruckRegistryByParam<Truck, Truck.TruckType> truckRegistry = new TruckRegistryByParam<>(truckDao.listByTypes());
        System.out.println("Trucks with type: " + TYPE);

        List<Truck> trucks = truckRegistry.getByParam(TYPE);
        for (Truck truck : trucks) {
            System.out.println(truck);
        }
        System.out.println();

        TruckRegistryByParam<Truck, Long> truckRegistryByCode = new TruckRegistryByParam<>(truckDao.listById());
        truckRegistryByCode.viewTruckRegistry();
        System.out.println("\nTrucks with code: " + 31);
        List<Truck> trucksByCode = truckRegistryByCode.getByParam(31L);
        for (Truck truck : trucksByCode) {
            System.out.println(truck);
        }

    }

    private static void printHelp() {
        System.out.println("Use first argument as truck ID");
    }
}
