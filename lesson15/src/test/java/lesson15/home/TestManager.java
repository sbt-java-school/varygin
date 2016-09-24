package lesson15.home;

import lesson15.home.task2.Context;
import lesson15.home.task2.ExecutionManagerImpl;

import java.util.LinkedList;

public class TestManager {
    private static Context execute;
    private volatile static boolean flag = false;

    public static void main(String[] args) throws Exception {
        Runnable runnable = () -> {
            int k = 0;
            for (int i = 0; i < 100000; i++) {
                k++;
            }
        };

        execute = new ExecutionManagerImpl().execute(
                TestManager::showStat,
                runnable,
                runnable,
                /*() -> {
                    throw new IllegalStateException();
                },*/
                runnable,
                () -> {
                    int k = 0;
                    for (int i = 0; i < 100000000; i++) {
                        k++;
                    }
                },
                () -> {
                    LinkedList<String> strings = new LinkedList<>();
                    for (int i = 0; i < 10000; i++) {
                        strings.add(i + "'");
                    }
                    for (String string : strings) {
                        string.contains("1");
                    }
                }
        );
        execute.interrupt();

        while (!flag) {
            Thread.yield();
        }
        System.out.println("Completed: " + execute.getCompletedTaskCount());
        System.out.println("Failed: " + execute.getFailedTaskCount());
        System.out.println("Interrupt: " + execute.getInterruptedTaskCount());
        System.out.println("Is finished?: " + execute.isFinished());
    }

    private static void showStat() {
        System.out.println("Finish!");
        flag = true;
    }
}
