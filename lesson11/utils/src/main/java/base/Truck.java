package base;

import ru.sbt.Plugin;

public class Truck implements Plugin {
    public String action() {
        return this.getClass().getPackage().getName() + ":" + this.getClass().getSimpleName();
    }
}
