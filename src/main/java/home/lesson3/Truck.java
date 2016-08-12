package home.lesson3;

import java.lang.*;

/**
 * Class Truck resolve ability for put things into truck
 */
public class Truck extends Thread {

    /* Грузоподъёмность грузовика */
    private int capacity;

    /* Итоговая загрузка грузовика */
    private int totalCapacity;

    /* Количество груза в грузовике */
    private int countThings;

    /* Менеджер погрузки */
    private static TruckManager truckManager;

    public Truck(int capacity, String name) {
        super(name);
        this.capacity = capacity;
    }

    public void run() {
        int weight;
        truckManager = TruckManager.getInstance();
        while (truckManager.isReady()) {
            weight = truckManager.getNextFromBasket();
            if (!(weight > 0 && load(weight))) {
                load(truckManager.getNext());
            }

            if (totalCapacity == capacity) {
                break;
            }
            try {
                sleep(1000);
            } catch (Exception e) {
                System.out.println("Can not sleep.");
            }
        }
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getCountThings() {
        return countThings;
    }

    /**
     *
     * Function to load things to truck
     *
     * @param  weight
     *         new weight for loading
     *
     * @return <code>true</code> when loaded success;
     *         <code>false</code> otherwise.
     */
    private boolean load(int weight) {
        if (weight == 0) {
            return false;
        }

        if (totalCapacity + weight <= capacity) {
            totalCapacity += weight;
            countThings++;
            log(weight, "load");
            return true;
        } else {
            log(weight, "sent to basket");
            truckManager.putToBasket(weight);
        }
        return false;
    }


    /**
     *
     * Printing actions to console
     *
     * @param weight
     *        action weight
     * @param action
     *        happened action
     */
    private void log(int weight, String action) {
        System.out.println(String.format("%s %s %d", getName(), action, weight));
    }

}

