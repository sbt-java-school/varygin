package lesson15.home;

import lesson15.home.task1.Task;
import lesson15.home.task1.TaskException;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class TestTasks {
    private static CountDownLatch startAll = new CountDownLatch(1);

    public static void main(String[] args) {
        Task<Integer> simpleTask = new Task<>(() -> {
//            throw new Exception("something wrong");
            System.out.println("Real Calculation");
            int i = 0;
            for (int j = 0; j < 1000; j++) {
                i++;
            }
            return i;
        });

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(runnableFunc(simpleTask)));
        }
        threads.forEach(Thread::start);
        startAll.countDown();
//        threads.forEach(Thread::interrupt);
    }

    private static Runnable runnableFunc(Task<Integer> simpleTask) {
        return () -> {
            int res = 0;
            try {
                startAll.await();
                res = simpleTask.get();
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                return;
            } catch (TaskException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(res);
        };
    }
}
