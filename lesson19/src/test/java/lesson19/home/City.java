package lesson19.home;

public enum City {
    NSK(54),
    MSK(77);

    private final double code;

    City(double code) {
        this.code = code;
    }

    public double getCode() {
        return code;
    }
}
