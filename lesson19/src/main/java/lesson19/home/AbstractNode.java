package lesson19.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Абстрактный класс предназначен для определения функций,
 * однотипных у большинства наследуемых классов
 *
 * Реализует две возможности:
 * 1. Добавление нод в список и работа со списком
 * 2. Работа только с двумя нодами (левая и правая в выражении)
 */
public abstract class AbstractNode implements Node {

    // Список для хранения дочерних нод
    private List<Node> nodes = new ArrayList<>();

    protected Node leftNode;
    protected Node rightNode;

    protected AbstractNode() {
    }

    /**
     * Конструктор для задания конкретных нод для выполнения действий
     * @param left нода, подставляемая в вычисляемое выражение слева
     * @param right нода, подставляемая в вычисляемое выражение справа
     */
    public AbstractNode(Node left, Node right) {
        this.leftNode = left;
        this.rightNode = right;
    }

    /**
     * Необходимо вызывать реалиацию этого метода при его переопределении
     * в классах-потомках, чтобы выполнить минимальные проверки заполнения параметров узла
     * @param values карта значений параметров
     * @return 0, если проверка прошла успешно,
     *          иначе пробрасывает unchecked исключение IllegalStateException
     */
    public double getResult(Map<String, Double> values) {
        if (nodes.isEmpty() && (leftNode == null || rightNode == null)) {
            throw new IllegalStateException();
        }
        return 0;
    }

    public void add(Node node) {
        nodes.add(node);
    }

    /**
     * Метод возвращает дубликат списка дочерних нод
     * @return лист из нод
     */
    public List<Node> getChildes() {
        ArrayList<Node> childNodes = new ArrayList<>();
        childNodes.addAll(nodes);
        return childNodes;
    }
}
