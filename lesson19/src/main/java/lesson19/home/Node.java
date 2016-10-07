package lesson19.home;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Map;

/**
 * Общий интерфейс компановщика
 */
public interface Node {

    /**
     * Метод расчёта результата выражения
     * @param values карта значений параметров
     * @return double результат расчёта,
     *                либо 1 или 0, если вызывается логическая операция
     */
    double getResult(Map<String, Double> values);

    /**
     * Добавление ноды в список дочерних нод для последующего вычисления
     * @param node экземпляр объекта, реализующего интерфейс Node
     * @throws OperationNotSupportedException пробрасывается для Node-листов
     */
    void add(Node node) throws OperationNotSupportedException;

    /**
     * Получение списка дичерних нод
     * @return список нод
     * @throws OperationNotSupportedException пробрасывается для Node-листов
     */
    List<Node> getChildes() throws OperationNotSupportedException;
}
