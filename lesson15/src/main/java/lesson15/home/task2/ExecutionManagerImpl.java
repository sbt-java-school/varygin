package lesson15.home.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс, реализующий создание пула потоков
 * и распределяющий задачи по этим потокам.
 */
public class ExecutionManagerImpl implements ExecutionManager {
    private int countThreads;
    private CountDownLatch startLatch = new CountDownLatch(1);
    private CountDownLatch endLatch;

    private AtomicInteger completedTaskCount = new AtomicInteger();
    private AtomicInteger failedTaskCount = new AtomicInteger();
    private AtomicInteger canceledTaskCount = new AtomicInteger();


    /** Ссылки на созданные потоки */
    private List<Thread> threads;

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        if (tasks == null || tasks.length == 0) {
            throw new NullPointerException();
        }
        countThreads = tasks.length;
        endLatch = new CountDownLatch(countThreads);
        createThreads(tasks);
        startLatch.countDown();

        //Поток для коллбэка, который начнёт выполнение, после выполнения всех тасков
        new Thread(() -> {
            try {
                endLatch.await();
                callback.run();
            } catch (InterruptedException e) {
            }
        }, "Callback").start();

        return new ContextImpl();
    }

    private void createThreads(Runnable... tasks) {
        threads = new ArrayList<>(countThreads);
        for (int i = 0; i < countThreads; i++) {
            Thread thread = new Thread(new Worker(tasks[i]), "Task #" + i);
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Класс для создания потоков выполнения задач
     */
    private class Worker implements Runnable {
        private final Runnable task;

        private Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            try {
                startLatch.await();
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                task.run();
                completedTaskCount.incrementAndGet();
            } catch (InterruptedException e) {
                canceledTaskCount.incrementAndGet();
            } catch (Exception e) {
                failedTaskCount.incrementAndGet();
            } finally {
                endLatch.countDown();
            }
        }
    }

    private class ContextImpl implements Context {
        @Override
        public int getCompletedTaskCount() {
            return completedTaskCount.get();
        }

        @Override
        public int getFailedTaskCount() {
            return failedTaskCount.get();
        }

        @Override
        public int getInterruptedTaskCount() {
            return canceledTaskCount.get();
        }

        @Override
        public void interrupt() {
            threads.forEach(Thread::interrupt);
        }

        @Override
        public boolean isFinished() {
            return (completedTaskCount.get() + canceledTaskCount.get() == countThreads);
        }
    }
}
