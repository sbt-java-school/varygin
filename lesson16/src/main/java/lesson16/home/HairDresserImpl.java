package lesson16.home;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class HairDresserImpl implements HairDresser {
    private static final long TIME_TO_WORK = 5;
    private volatile Status status = Status.CHECK;
    private static final long timeToCheck = 3;
    private final Lock lock;
    private volatile Client client;
    private final CountDownLatch latch;
    private final Condition condition;

    HairDresserImpl(Lock lock, Condition condition, CountDownLatch latch) {
        this.lock = lock;
        this.latch = latch;
        this.condition = condition;
    }

    @Override
    public void run() {
        log("start");
        latch.countDown();
        Thread thread = Thread.currentThread();
        while (!thread.isInterrupted()) {
            try {
                check();
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
        log("finish working");
    }

    public Status getStatus() {
        return status;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Condition wakeUp() {
        return condition;
    }

    private void work() throws InterruptedException {
        log("mows client " + client.getName());
        TimeUnit.SECONDS.sleep(TIME_TO_WORK);
        client = null;
    }

    private void sleep() throws InterruptedException {
        log("sleeping");
        setStatus(Status.SLEEP);
        while (client == null) {
            if (!condition.await(timeToCheck * 10, TimeUnit.SECONDS)) {
                Thread.currentThread().interrupt();
            }
        }
        log("waking");
    }

    private void check() throws InterruptedException {
        lock.lock();
        try {
            log("checking");
            setStatus(Status.CHECK);
            TimeUnit.SECONDS.sleep(timeToCheck);
            if ((client = ReceptionImpl.getInstance().getClient()) == null) {
                sleep();
            }
            setStatus(Status.WORK);
        } finally {
            lock.unlock();
        }
        if (client != null) {
            work();
        }
    }

    private void setStatus(Status status) {
        this.status = status;
    }

    private void log(String mess) {
        System.out.println("HairDresserImpl: " + mess);
    }
}
