package lesson16.home;

/**
 * Интерфейс для взаимодействия с парикмахером
 */
public interface HairDresser extends Runnable {
    /**
     * Проверка, спит ли парикмахер
     * @return boolean true, если парикмахер спит, false - иначе
     */
    boolean isSleeping();

    /**
     * Возвращает имя парикмахера
     * @return String
     */
    String getName();

    /**
     * Попытка разбудить парикмахера
     * @throws InterruptedException если парикмахер уже был кем-то разбужен,
     * но ещё не принял клиента
     */
    void wakeUp() throws InterruptedException;
}
