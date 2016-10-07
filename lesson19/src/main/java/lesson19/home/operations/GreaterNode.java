package lesson19.home.operations;

import lesson19.home.AbstractNode;
import lesson19.home.Node;

import java.util.List;
import java.util.Map;

public class GreaterNode extends AbstractNode {
    public GreaterNode() {
    }

    public GreaterNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public double getResult(Map<String, Double> values) {
        super.getResult(values);
        boolean res = true;
        double result;
        List<Node> childes = getChildes();
        if (childes.isEmpty()) {
            res = leftNode.getResult(values) > rightNode.getResult(values);
        } else {
            result = childes.get(0).getResult(values);
            childes.remove(0);
            for (Node node : childes) {
                res = result > node.getResult(values);
                if (!res) {
                    break;
                }
            }
        }
        return res ? 1.0 : 0.0;
    }
}
