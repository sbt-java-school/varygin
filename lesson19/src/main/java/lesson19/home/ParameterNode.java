package lesson19.home;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Map;

/**
 * Реаоизует интерфейс листа в дереве нод
 * Хранит строку - ключ в карте значений параметров и возвращает значение по этому ключу
 * при вызове метода getResult
 *
 * Остальные операции интерфейса Node не поддерживаются
 */
public class ParameterNode implements Node {
    private final String keyName;

    public ParameterNode(String keyName) {
        this.keyName = keyName;
    }

    public double getResult(Map<String, Double> map) {
        return map.get(keyName);
    }

    public void add(Node node) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public List<Node> getChildes() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
