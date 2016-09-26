package lesson16.home;

import java.util.concurrent.locks.Condition;

public interface HairDresser extends Runnable {
    Status getStatus();
    void setClient(Client client);
    Condition wakeUp();
}
