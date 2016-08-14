package home.lesson4;

import java.util.List;

/**
 * Created by LL on 13.08.2016.
 */
public interface TruckDao {

    List<Truck> listByTypes();
    List<Truck> listById();
}
