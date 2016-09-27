package lesson16.home;

import java.util.Optional;

/**
 * Класс для моделирования действий клиента в парикмахерской
 */
public class ClientImpl implements Client {
    private final int timeToCheck;
    private static final Reception reception = ReceptionImpl.getInstance();
    private static final Object commonLock = reception.getCommonLock();
    private String name;

    public ClientImpl(int timeToCheck, String name) {
        this.timeToCheck = timeToCheck;
        this.name = name;
    }

    @Override
    public void run() {
        log("входит");
        checkHairDresser();
    }

    private void checkHairDresser() {
        synchronized (commonLock) {
            try {
                process();
                Optional<HairDresser> first = reception.getHeirDressers().filter(HairDresser::isSleeping).findFirst();
                if (first.isPresent() && reception.prepareClient(this)) {
                    HairDresser hairDresser = first.get();
                    log("будит парикмахера " + hairDresser.getName());
                    hairDresser.wakeUp();
                } else {
                    trySitToChair();
                }
            } catch (InterruptedException e) {
                log("не смог разбудить");
                trySitToChair();
            }
        }
    }

    private void process() {
        double k = 1.0;
        for (int i = 0; i < timeToCheck * 100000; i++) {
            k = Math.random() * 10 + 10564;
        }
    }

    private void trySitToChair() {
        if (reception.addClient(this)) {
            log("садится на стул");
        } else {
            log("уходит");
        }
    }

    private void log(String mess) {
        System.out.println("Клиент " + name + ": " + mess);
    }

    public String getName() {
        return name;
    }
}
