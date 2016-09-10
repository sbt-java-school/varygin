package home.lesson4;

/**
 * Created by LL on 13.08.2016.
 */
public class Truck <P> extends BigCars {

    private long id;
    private int capacity;
    private P field;

    public Truck(long id, P field, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.field = field;
    }

    public P getField() {
        return field;
    }

    public long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id+
                '}';
    }

    public static enum TruckType {
        KAMAZ, UAZ, ZAZ, LADA
    }
}
