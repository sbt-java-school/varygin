package lesson16.home;

import java.util.List;
import java.util.concurrent.locks.Lock;

public interface Reception {
    Client getClient();
    boolean addClient(Client client);
    Lock getCommonLock();
    List<HairDresser> getHeirDressers();
    void end();
}
