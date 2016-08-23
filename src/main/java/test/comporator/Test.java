package test.comporator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LL on 22.08.2016.
 */
class Test implements Comparable<Test> {

    private String fio;
    private int age;
    private Double high;

    public Test(String name, int age, Double high) {
        this.fio = name;
        this.age = age;
        this.high = high;
    }

    public String getFio() {
        return fio;
    }

    public int getAge() {
        return age;
    }

    public Double getHigh() {
        return high;
    }

    @Override
    public String toString() {
        return "Test{" +
                "fio='" + fio + '\'' +
                " age=" + age +
                " high=" + high  +
                '}';
    }

    @Override
    public int compareTo(Test o) {
        int result = fio.compareTo(o.getFio());
        if (result != 0) {
            if (result > 0) {
                result = 1;
            } else {
                result = -1;
            }
        }
        result += Integer.compare(age, o.getAge());
        result += Double.compare(high, o.getHigh());
        return result;
    }
}
