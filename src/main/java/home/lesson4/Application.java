package home.lesson4;

import java.util.*;

/**
 * Created by LL on 13.08.2016.
 */
public class Application {
    public static final Truck.TruckType TYPE = Truck.TruckType.KAMAZ;
    public static final Truck.TruckType LADA = Truck.TruckType.LADA;
    private Map<Long, Truck> truckRegistry = new TreeMap<>();

    public static void main(String[] args) {
        TruckDao truckDao = new TruckDaoMemotyImpl();


        /*Мультимап по типу*/
        System.out.println("Trucks bu type with type: " + LADA);
        TruckRegistry<Truck.TruckType, Truck> truckRegistryType = new TruckRegistryByType();
        for (Truck truck : truckDao.listByTypes()) {
            truckRegistryType.put((Truck.TruckType) truck.getField(), truck);
        }
        List<Truck> list = truckRegistryType.get(LADA);
        for (Truck truck : list) {
            System.out.println(truck);
        }
        System.out.println("\n");


        /*Параметризированный мультимап*/
        TruckRegistry<Truck.TruckType, Truck> truckRegistry = new TruckRegistryByParam<>();
        for (Truck truck : truckDao.listByTypes()) {
            truckRegistry.put((Truck.TruckType) truck.getField(), truck);
        }
        System.out.println("Trucks with type: " + TYPE);
        List<Truck> trucks = truckRegistry.get(TYPE);
        for (Truck truck : trucks) {
            System.out.println(truck);
        }
        System.out.println();


        TruckRegistryByParam<Long, Truck> truckRegistry1 = new TruckRegistryByParam<>();
        for (Truck truck : truckDao.listById()) {
            truckRegistry1.put((Long) truck.getField(), truck);
        }
        truckRegistry1.viewTruckRegistry();
        System.out.println("\nTrucks with code: " + 31);
        List<Truck> trucksByCode = truckRegistry1.get(31L);
        for (Truck truck : trucksByCode) {
            System.out.println(truck);
        }

    }

    private static void printHelp() {
        System.out.println("Use first argument as truck ID");
    }
}
