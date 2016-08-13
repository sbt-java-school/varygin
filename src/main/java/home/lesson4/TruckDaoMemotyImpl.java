package home.lesson4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LL on 13.08.2016.
 */
public class TruckDaoMemotyImpl implements TruckDao {

    private final List<Truck> trucks = new ArrayList<>(Arrays.asList(
            new Truck(1, Truck.TruckType.KAMAZ, 10),
            new Truck(2, Truck.TruckType.KAMAZ, 8),
            new Truck(31, Truck.TruckType.UAZ, 20),
            new Truck(4, Truck.TruckType.KAMAZ, 11),
            new Truck(56, Truck.TruckType.LADA, 98),
            new Truck(6, Truck.TruckType.ZAZ, 100)
    ));

    @Override
    public List<Truck> list() {
        return trucks;
    }
}
