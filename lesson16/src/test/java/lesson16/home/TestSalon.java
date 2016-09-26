package lesson16.home;

import java.util.concurrent.TimeUnit;

public class TestSalon {

    public static void main(String[] args) throws InterruptedException {
        Reception reception = ReceptionImpl.getInstance();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new ClientImpl(i, "" + (i + 1)));
            thread.start();

            TimeUnit.SECONDS.sleep(1);
        }
        //reception.end();
    }
}
