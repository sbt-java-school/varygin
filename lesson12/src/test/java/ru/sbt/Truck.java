package ru.sbt;

class Truck {
    private int capacity;
    private String name;

    Truck(int capacity, String name) {
        this.capacity = capacity;
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "capacity=" + capacity +
                '}';
    }

    public String getName() {
        return name;
    }
}
