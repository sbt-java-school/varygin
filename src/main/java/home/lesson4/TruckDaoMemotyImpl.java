package home.lesson4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LL on 13.08.2016.
 */
public class TruckDaoMemotyImpl implements TruckDao {

    private final List<Truck> trucksType = new ArrayList<>(Arrays.asList(
            new Truck<Truck.TruckType>(1, Truck.TruckType.KAMAZ, 10),
            new Truck<Truck.TruckType>(2, Truck.TruckType.KAMAZ, 8),
            new Truck<Truck.TruckType>(31, Truck.TruckType.UAZ, 20),
            new Truck<Truck.TruckType>(4, Truck.TruckType.KAMAZ, 11),
            new Truck<Truck.TruckType>(56, Truck.TruckType.LADA, 98),
            new Truck<Truck.TruckType>(6, Truck.TruckType.ZAZ, 100)
    ));

    private final List<Truck> trucksIds = new ArrayList<>(Arrays.asList(
            new Truck<Long>(1, 1L, 10),
            new Truck<Long>(2, 2L, 8),
            new Truck<Long>(3, 31L, 20),
            new Truck<Long>(4, 4L, 11),
            new Truck<Long>(5, 56L, 98),
            new Truck<Long>(6, 6L, 100)
    ));

    @Override
    public List<Truck> listById() {
        return trucksIds;
    }

    @Override
    public List<Truck> listByTypes() {
        return trucksType;
    }
}
