package lesson14.home.utils;

public class Job implements Task {
    private int number;

    public Job(int number) {
        this.number = number + 1;
    }

    @Override
    public void start(String threadName) throws InterruptedException {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new InterruptedException();
        }
        System.out.println("Task number " + number + " is completed on thread: " + threadName);
    }
}
