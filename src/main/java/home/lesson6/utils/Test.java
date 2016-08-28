package home.lesson6.utils;

/**
 * Some class to test
 */
public class Test extends TestParent {

    public static final String MONDAY = "MONDAY";
    private static final String SUNDAY = "SUNDAY";

    private Number num;
    private String fio;
    protected int age;

    public Test() {
        this(21);
    }

    public Test(String name, int age, Number num) {
        this.fio = name;
        this.age = age;
        this.num = num;
    }

    private Test(int age) {
        this.age = age;
    }

    public int add(int a, int b) {
        return a + b;
    }

    protected static void remove(int i) {

    }

    public String getFio() {
        return fio;
    }

    public int getAge() {
        return age;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Test{" +
                "fio='" + fio + '\'' +
                "age='" + age + '\'' +
                '}';
    }

    private int random() {
        int res = (int) (Math.random()*10);
        return res;
    }

    public Number getNum() {
        return num;
    }

    public void setNum(Number num) {
        this.num = num;
    }
}
