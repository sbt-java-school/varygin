package base;

import ru.sbt.Plugin;

public class Person implements Plugin {
    public Truck truck;

    public Person() {
        truck = new Truck();
    }

    public String action() {
        return this.getClass().getPackage().getName() + ":" + this.getClass().getSimpleName();
    }
}
