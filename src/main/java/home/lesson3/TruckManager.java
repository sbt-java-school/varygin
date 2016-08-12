package home.lesson3;

import java.io.*;
import java.lang.*;
import java.util.*;


/**
 * Class TruckManager: load all available things from file to queue in different thread
 */
public class TruckManager extends Thread {
    private Queue<Integer> loadQueue;
    private Queue<Integer> basketQueue;
    private static TruckManager instance;
    private static boolean isLoaded;

    private TruckManager() {
        loadQueue = new LinkedList<>();
        basketQueue = new LinkedList<>();
        start();
    }

    public synchronized static TruckManager getInstance() {
        if (instance == null) {
            instance = new TruckManager();
        }
        return instance;
    }

    public void run() {
        try (
                Reader fileReader = new FileReader("files/home/lesson3/input.txt");
                BufferedReader reader = new BufferedReader(fileReader);
        ){
            while (reader.ready()) {
                int newWeight = Integer.parseInt(reader.readLine());
                loadQueue.add(newWeight);
                log(newWeight);
                sleep(500);
            }
        } catch (Exception e) {
            System.out.println("Problems with read file");
        }
        isLoaded = true;
    }


    public synchronized int getNext() {
        if (loadQueue == null) {
            return 0;
        }
        return (loadQueue.size() > 0) ? loadQueue.poll() : 0;
    }

    public synchronized void putToBasket(int weight) {
        basketQueue.add(weight);
    }

    public synchronized int getNextFromBasket() {
        if (basketQueue == null) {
            return 0;
        }
        return (basketQueue.size() > 0) ? basketQueue.poll() : 0;
    }

    public synchronized boolean isReady() {
        return !TruckManager.isLoaded || loadQueue.size() > 0;
    }

    private void log(int weight) {
        System.out.println("Manager put:" + weight);
    }

}