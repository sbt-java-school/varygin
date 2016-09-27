package lesson16.home;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * Класс для моделирования работы парикмахерской
 * Здесь ведётся учёт всех клиентов и запуск всех парикмахеров и возможность досрочного их роспуска
 * Первоначальные настройки берутся из файла conf.properties, где
 *      countWorker - количество парикмахеров
 *      countChairs - количество доступных стульев в приёмной
 */
final class ReceptionImpl implements Reception {
    private static Reception instance;

    private static final Lock lock  = new ReentrantLock();
    private final Object checkLock = new Object();
    private final CountDownLatch latch;

    private final BlockingQueue<Client> queue;
    private final BlockingQueue<Client> newClientList = new ArrayBlockingQueue<>(1);
    private final List<HairDresser> hairDressers;
    private final List<Thread> threads;

    private ReceptionImpl(Properties properties) throws IOException {
        queue = new ArrayBlockingQueue<>(Integer.parseInt(properties.getProperty("countChairs", "1")));
        int workers = Integer.parseInt(properties.getProperty("countWorker", "1"));
        if (workers == 0) {
            throw new IOException();
        }
        latch = new CountDownLatch(workers);
        List<HairDresser> workersList = new ArrayList<>(workers);
        List<Thread> threadList = new ArrayList<>(workers);
        for (int i = 0; i < workers; i++) {
            HairDresser worker = new HairDresserImpl(checkLock, latch, Integer.toString(i + 1));
            Thread thread = new Thread(worker);
            thread.setPriority(Thread.MAX_PRIORITY);
            threadList.add(thread);
            workersList.add(worker);
        }
        hairDressers = workersList;
        threads = threadList;
        threads.forEach(Thread::start);
        try {
            latch.await();
        } catch (InterruptedException e) {
            //ignore
        }
    }

    static Reception getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                InputStream stream = null;
                try {
                    stream = Reception.class.getClassLoader().getResourceAsStream("conf.properties");
                    Properties properties = new Properties();
                    properties.load(stream);

                    instance = new ReceptionImpl(properties);
                    log("Парикмахерская открыта");
                } catch (IOException e) {
                    System.out.println("Парихмахеры не пришли на работу.");
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                            //ignore
                        }
                    }
                }
            }
            lock.unlock();
        }
        return instance;
    }

    public Client getClient() {
        return queue.poll();
    }

    public boolean addClient(Client client) {
        return queue.offer(client);
    }

    public boolean prepareClient(Client client) {
        return newClientList.offer(client);
    }

    public Client takeClient() {
        return newClientList.poll();
    }

    public Object getCommonLock() {
        return checkLock;
    }

    public Stream<HairDresser> getHeirDressers() {
        return hairDressers.stream();
    }

    public void end() {
        threads.forEach(Thread::interrupt);
    }

    public boolean thereIsClients() {
        return queue.size() > 0;
    }

    private static void log(String mess) {
        System.out.println("Ресепшн: " + mess);
    }
}
