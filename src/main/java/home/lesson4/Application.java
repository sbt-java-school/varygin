package home.lesson4;

import java.util.*;

/**
 * Created by LL on 13.08.2016.
 */
public class Application {
    public static final Truck.TruckType TYPE = Truck.TruckType.KAMAZ;
    private Map<Long, Truck> truckRegistry = new TreeMap<>();

    public Application(TruckDao truckDao) {
        List<Truck> list = truckDao.list();
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
        Application application = new Application(truckDao);

        List<Truck> list = truckDao.list();

        /*Iterator<Truck> iterator = list.iterator();
        while (iterator.hasNext()) {
            Truck truck = iterator.next();
            if (truck.getCapacity() < 20) {
                iterator.remove();
            }
        }*/
        //==
        /*for (Truck truck : new ArrayList<>(list)) {
            if (truck.getCapacity() < 20) {
                list.remove(truck);
            }
        }*/
        //==
        /*for (Iterator<Truck> iterator = list.iterator(); iterator.hasNext(); iterator.next()) {
            Truck truck = iterator.next();
            if (truck.getCapacity() < 20) {
                iterator.remove();
            }
        }
        System.out.println(list);*/

        /*if (args.length != 1) {
            printHelp();
            System.exit(-1);
        }

        application.viewTruckRegistry();

        List<Truck> list = truckDao.list();
        System.out.println(list);

        list.add(new Truck(121, 153));
        System.out.println(list);

        List<Truck> list1 = truckDao.list();
        System.out.println(list1);

        Map<Integer, String> integerStringMap = Collections.singletonMap(1, "234");*/

        TruckRegistryByType truckRegistryByType = new TruckRegistryByType(truckDao);
        truckRegistryByType.viewTruckRegistry();
        System.out.println("\nTrucks with type: " + TYPE);

        List<Truck> trucks = truckRegistryByType.getTrucksByType(TYPE);
        for (Truck truck : trucks) {
            System.out.println(truck);
        }

    }

    private static void printHelp() {
        System.out.println("User first argument sa truck ID");
    }
}
