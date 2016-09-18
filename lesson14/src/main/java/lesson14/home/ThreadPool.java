package lesson14.home;

import lesson14.home.utils.Log;
import lesson14.home.utils.Task;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Класс, реализующий создание пула процессов
 * и распределяющий задачи по этим процессам.
 */
public class ThreadPool {
    private final int countThreads;
    private final Queue<Task> tasksQueue;
    private final long killerMercy;

    /** Ссылки на созданные потоки */
    private final Thread[] threads;

    /** Объект, по которому синхронизируется процесс для остановки потоков */
    private final Object killerWatcher = new Object();

    /**
     *
     * @param countThreads максимальное количество создаваемых потоков
     * @param tasks очередь задач
     * @param killerMercy количество секунд ожидания, перед тем как завершить все процессы.
     *                    Если 0 - то процессы не завершаются при завершении всех задач в очереди
     *                    Если больше нуля, то когда задачи в очереди <code>tasks</code> закончатся,
     *                    и ни одна новая задача не поступит в течении <code>killerMercy</code> секунд - все
     *                    потоки завершатся.
     */
    public ThreadPool(int countThreads, Queue<Task> tasks, long killerMercy) {
        if (countThreads <= 0) {
            throw new IllegalStateException("Count of thread can't be 0");
        }
        this.tasksQueue = new LinkedList<>();
        this.killerMercy = killerMercy;
        this.countThreads = countThreads;
        this.threads = new Thread[countThreads];

        if (tasks != null && !tasks.isEmpty()) {
            tasksQueue.addAll(tasks);
        }
        startThreads();
    }

    /**
     * @param countThreads количество потоков
     * @param killerMercy время в секундах
     */
    public ThreadPool(int countThreads, long killerMercy) {
        this(countThreads, null, killerMercy);
    }

    /**
     * @param countThreads количество потоков
     * @param tasks задачи
     */
    public ThreadPool(int countThreads, Queue<Task> tasks) {
        this(countThreads, tasks, 0);
    }

    /**
     * @param countThreads количество потоков
     */
    public ThreadPool(int countThreads) {
        this(countThreads, 0);
    }

    private void startThreads() {
        for (int i = 0; i < countThreads; i++) {
            threads[i] = new Thread(new Worker(), "Thread #" + i);
            threads[i].start();
        }
        if (killerMercy > 0) {
            new Thread(new Killer(), "Killer").start();
        }
    }

    public void addTask(Task task) {
        synchronized (tasksQueue) {
            tasksQueue.add(task);
            tasksQueue.notify();
        }
    }

    public void addTask(Queue<Task> tasks) {
        synchronized (tasksQueue) {
            tasksQueue.addAll(tasks);
            tasksQueue.notify();
        }
    }

    /**
     * Класс для создания потоков выполнения задач
     */
    private class Worker implements Runnable {
        @Override
        public void run() {
            Thread currentThread = Thread.currentThread();
            Log.add(currentThread, "start");
            Task task;
            while (true) {
                synchronized (tasksQueue) {
                    while (tasksQueue.isEmpty() && !currentThread.isInterrupted()) {
                        try {
                            tasksQueue.wait();
                        } catch (InterruptedException e) {
                            currentThread.interrupt();
                        }
                    }
                    if (currentThread.isInterrupted()) {
                        break;
                    }

                    task = tasksQueue.poll();
                }
                try {
                    task.start(currentThread.getName());
                } catch (Exception e) {
                    Log.add(currentThread, e.getMessage());
                }
                if (tasksQueue.isEmpty()) {
                    synchronized (killerWatcher) {
                        killerWatcher.notify();
                    }
                }
            }
            Log.add(currentThread, "stopped");
        }
    }

    /**
     * Отдельный поток для отслеживания завершения всех задач
     */
    private class Killer implements Runnable {
        public final long TIMEOUT = killerMercy * 10;

        @Override
        public void run() {
            Thread currentThread = Thread.currentThread();
            while (!currentThread.isInterrupted()) {
                synchronized (killerWatcher) {
                    while (!tasksQueue.isEmpty() && !currentThread.isInterrupted()) {
                        try {
                            killerWatcher.wait(TIMEOUT);
                        } catch (InterruptedException e) {
                            currentThread.interrupt();
                        }
                    }
                }
                if (currentThread.isInterrupted()) {
                    break;
                }

                try {
                    Log.add(currentThread, "time to mercy");
                    TimeUnit.SECONDS.sleep(killerMercy);
                    if (tasksQueue.isEmpty()) {
                        Log.add(currentThread, "time to death");
                        break;
                    }
                } catch (InterruptedException e) {
                    currentThread.interrupt();
                }
            }
            killAll();
            Log.add(currentThread, "finished");
        }

        private void killAll() {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }
    }
}
