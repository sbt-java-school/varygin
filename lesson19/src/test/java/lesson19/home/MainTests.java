package lesson19.home;

import lesson19.home.operations.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.util.HashMap;
import java.util.Map;

public class MainTests {
    private static final Map<String, Double> map = new HashMap<>();

    @Before
    public void init() {
        map.clear();
        map.put("city_need", City.NSK.getCode());
        map.put("city", City.NSK.getCode());
        map.put("amount_1", 12d);
        map.put("amount_2", 5d);
        map.put("amount_3", 10d);
        map.put("countOfMonth", 3d);
        map.put("years", 5d);
        map.put("salary_1", 200d);
        map.put("salary_2", 500d);
        map.put("salary_3", 10d);
        map.put("salary_4", 15d);
        map.put("coefficient", 2d);
    }

    @Test
    public void testResult() throws Exception {
        Assert.assertEquals(1d, getResult(), .0);
    }

    @Test
    public void testByCityResult() throws Exception {
        map.put("city_need", City.MSK.getCode());
        Assert.assertEquals(0d, getResult(), .0);
    }

    @Test
    public void testByAmountResult() throws Exception {
        map.put("amount_1", 10000d);
        map.put("amount_2", 10000d);
        Assert.assertEquals(0d, getResult(), .0);
    }

    @Test
    public void testByPeriodResult() throws Exception {
        map.put("salary_1", 0.5);
        map.put("salary_2", 0.1);
        map.put("salary_3", 0.3);
        map.put("salary_4", 0.6);
        Assert.assertEquals(0d, getResult(), .0);
    }

    private double getResult() {
        Node city_need = new ParameterNode("city_need");
        Node city = new ParameterNode("city");
        Node amount1 = new ParameterNode("amount_1");
        Node amount2 = new ParameterNode("amount_2");
        Node amount3 = new ParameterNode("amount_3");
        Node countOfMonth = new ParameterNode("countOfMonth");
        Node years = new ParameterNode("years");
        Node salary1 = new ParameterNode("salary_1");
        Node salary2 = new ParameterNode("salary_2");
        Node salary3 = new ParameterNode("salary_3");
        Node salary4 = new ParameterNode("salary_4");
        Node coefficient = new ParameterNode("coefficient");

        Node amountOfCredit = new PlusNode();
        Node salary = new PlusNode();

        Node cityEquals = new EqualsNode(city, city_need);
        try {
            amountOfCredit.add(amount1);
            amountOfCredit.add(amount2);
            amountOfCredit.add(amount3);

            salary.add(salary1);
            salary.add(salary2);
            salary.add(salary3);
            salary.add(salary4);

            Node periodOfMonth = new MultipleNode(countOfMonth, years);
            Node ltDiv = new DivisionNode(amountOfCredit, periodOfMonth);
            Node rtDiv = new DivisionNode(salary, coefficient);

            Node summaryNode = new LessThanNode(ltDiv, rtDiv);
            EqualsNode result = new EqualsNode(summaryNode, cityEquals);

            return result.getResult(map);
        } catch (OperationNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
