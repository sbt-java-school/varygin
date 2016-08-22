package home.lesson6;

/**
 * Created by LL on 22.08.2016.
 */
class Test {

    private String fio;
    protected int age;

    public Test() {
        this(21);
    }

    public Test(String name, int age) {
        this.fio = name;
        this.age = age;
    }

    private Test(int age) {
        this.age = age;
    }

    public int add(int a, int b) {
        return a + b;
    }

    protected static void remove(int i) {

    }

    @Override
    public String toString() {
        return "Test{" +
                "fio='" + fio + '\'' +
                '}';
    }

    private int random() {
        int res = (int) (Math.random()*10);
        return res;
    }
}
