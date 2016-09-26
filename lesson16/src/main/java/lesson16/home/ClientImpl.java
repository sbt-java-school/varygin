package lesson16.home;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class ClientImpl implements Client {
    private final long timeToCheck;
    private static final Reception reception = ReceptionImpl.getInstance();
    private static final Lock lock = reception.getCommonLock();
    private String name;

    public ClientImpl(long timeToCheck, String name) {
        this.timeToCheck = timeToCheck;
        this.name = name;
    }

    @Override
    public void run() {
        log("client start");
        checkHairDresser();
    }

    private void checkHairDresser() {
        synchronized (lock) {
            try {
                TimeUnit.SECONDS.sleep(timeToCheck);
                boolean enter = false;
                for (HairDresser hairDresser : reception.getHeirDressers()) {
                    if (hairDresser.getStatus().equals(Status.SLEEP)) {
                        log("waking up");
                        enter = true;
                        hairDresser.setClient(this);
                        hairDresser.wakeUp().signal();
                        break;
                    }
                }
                if (!enter) {
                    trySitToChair();
                }
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }

    private void trySitToChair() {
        if (reception.addClient(this)) {
            log("sit down");
        } else {
            log("walk away");
        }
    }

    private void log(String mess) {
        System.out.println("Client " + Thread.currentThread().getName() + ": " + mess);
    }

    public String getName() {
        return name;
    }
}
