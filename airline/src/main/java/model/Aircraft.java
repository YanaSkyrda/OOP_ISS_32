package model;

public abstract class Aircraft implements Comparable<Aircraft>{
    private int payload;
    private int rangeOfFlight;
    private int fuel;
    private int capacity;
    private String name;

    public Aircraft(String name, int payload, int rangeOfFlight, int fuel, int capacity) {
        this.payload = payload;
        this.rangeOfFlight = rangeOfFlight;
        this.fuel = fuel;
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPayload() {
        return payload;
    }

    public void setPayload(int payload) {
        this.payload = payload;
    }

    public int getRangeOfFlight() {
        return rangeOfFlight;
    }

    public void setRangeOfFlight(int rangeOfFlight) {
        this.rangeOfFlight = rangeOfFlight;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int compareTo(Aircraft aircraft) {
        return this.rangeOfFlight - aircraft.rangeOfFlight;
    }


}
