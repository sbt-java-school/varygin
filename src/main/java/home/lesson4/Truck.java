package home.lesson4;

/**
 * Created by LL on 13.08.2016.
 */
public class Truck {

    private long id;
    private TruckType type;
    private int capacity;

    public Truck(long id, TruckType type, int capacity) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public TruckType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                '}';
    }

    public static enum TruckType {
        KAMAZ, UAZ, ZAZ, LADA
    }
}
