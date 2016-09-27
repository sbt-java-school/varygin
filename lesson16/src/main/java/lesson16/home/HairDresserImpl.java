package lesson16.home;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Класс для моделирования работы парикмахеров
 * При проверке наличия сидящих на стульях клиентов,
 * используется общий объект для блокировки <code>commonLock</code>
 *
 * Для того, чтобы разбудить работника используется персональный локер <code>sleepLock</code>
 *
 * Для контроля одновременного запуска всех работников используется Latch
 *
 * Парикмахер может находится в трёх состояниях:
 *      WORK - стрижёт кого-то
 *      SLEEP - спит
 *      CHECK - проверяет новых клиентов
 */
class HairDresserImpl implements HairDresser {
    private final String name;
    private volatile Status status = Status.CHECK;
    private static final long TIME_TO_WORK = 5;
    private static final int timeToCheck = 3;

    private final Object commonLock;
    private final Object sleepLock = new Object();
    private volatile Client client;
    private final CountDownLatch latch;

    HairDresserImpl(Object commonLock, CountDownLatch latch, String name) {
        this.commonLock = commonLock;
        this.latch = latch;
        this.name = name;
    }

    @Override
    public void run() {
        log("пришёл на работу");
        latch.countDown();
        Thread thread = Thread.currentThread();
        while (!thread.isInterrupted()) {
            try {
                check();
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
        log("пошёл домой");
    }

    public boolean isSleeping() {
        return status.equals(Status.SLEEP);
    }

    public String getName() {
        return name;
    }

    private void work() throws InterruptedException {
        log("стрижёт клиента " + client.getName());
        process();
        client = null;
    }

    private void process() {
        int t = 0;
        for (int i = 0; i < TIME_TO_WORK * 100000; i++) {
            t *= Math.random();
        }
    }

    private void sleep() throws InterruptedException {
        synchronized (sleepLock) {
            log("спит");
            setStatus(Status.SLEEP);
            int timeToWait = 0;
            while (client == null) {
                sleepLock.wait(TimeUnit.SECONDS.toMillis(timeToCheck * 2));
                if (!ReceptionImpl.getInstance().thereIsClients()
                        && (client = ReceptionImpl.getInstance().takeClient()) == null) {
                    timeToWait++;
                }
                if (timeToWait > 1) {
                    log("пора домой");
                    throw new InterruptedException("пора домой");
                }
            }
            log("проснулся");
            setStatus(Status.WORK);
        }
        if (client != null) {
            work();
        }
    }

    public void wakeUp() throws InterruptedException {
        synchronized (sleepLock) {
            if (status.equals(Status.SLEEP)) {
                sleepLock.notify();
            } else {
                throw new InterruptedException();
            }
        }
    }

    private void check() throws InterruptedException {
        synchronized (commonLock) {
            log("проверяет");
            setStatus(Status.CHECK);
            go();
            if ((client = ReceptionImpl.getInstance().getClient()) != null) {
                setStatus(Status.WORK);
            }
        }

        if (client != null) {
            work();
        } else {
            sleep();
        }
    }

    private void go() {
        double t = 10.0;
        for (int i = 0; i < timeToCheck; i++) {
            t *= Math.random();
        }
    }

    private void setStatus(Status status) {
        this.status = status;
    }

    private void log(String mess) {
        System.out.println("Парикмахер " + name + ": " + mess);
    }
}
