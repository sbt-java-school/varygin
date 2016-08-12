package home.lesson3;


/**
 * Created by LL on 12.08.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Truck truck1 = new Truck(10, "Truck1");
        Truck truck2 = new Truck(13, "Truck2");
        truck1.start();
        truck2.start();

        truck1.join();
        truck2.join();

        System.out.println("\nResult:");
        System.out.println(truck1.getName() + ": " + truck1.getTotalCapacity() + " " + truck1.getCountThings());
        System.out.println(truck2.getName() + ": " + truck2.getTotalCapacity() + " " + truck2.getCountThings());
    }
}
