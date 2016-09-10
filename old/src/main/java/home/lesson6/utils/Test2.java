package home.lesson6.utils;

/**
 * Second class for test
 */
public class Test2 {
    private Integer num;
    private String fio;
    private int age;

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Test{" +
                "fio='" + fio + '\'' +
                "num='" + num + '\'' +
                "age='" + age + '\'' +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
