package lesson16.home;

public class TestSalon {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 7; i++) {
            Thread thread = new Thread(new ClientImpl(i, "" + (i + 1)));
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();

//            TimeUnit.SECONDS.sleep((int) (random() * 5));
        }
    }
}
