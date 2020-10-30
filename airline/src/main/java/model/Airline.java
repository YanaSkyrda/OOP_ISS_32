package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Airline {
    private List<Aircraft> aircrafts;
    private String name;
    public Airline(String name) {
        aircrafts = new ArrayList<>();
        this.name = name;
    }

    public void addAircraft(Aircraft aircraft) {
        aircrafts.add(aircraft);
    }

    public void sortAircraftsByRangeOfFlight() {
        Collections.sort(aircrafts);
    }

    public List<Aircraft> getAircraftsByFuelRange(int lowerBound, int upperBound) {
        List<Aircraft> aircraftsInRange = new ArrayList<>();
        for (Aircraft aircraft: aircrafts) {
            if (aircraft.getFuel() < upperBound && aircraft.getFuel() > lowerBound) {
                aircraftsInRange.add(aircraft);
            }
        }
        return aircraftsInRange;
    }

    public void printAircrafts() {
        System.out.println(aircrafts);
    }

    public List<Aircraft> getAllAircrafts() {
        return new ArrayList<>(aircrafts);
    }

    public int getTotalPayload() {
        int total = 0;
        for (int i = 0; i < aircrafts.size(); i++) {
            total += aircrafts.get(i).getPayload();
        }
        return total;
    }
    public int getTotalCapacity() {
        int total = 0;
        for (int i = 0; i < aircrafts.size(); i++) {
            total += aircrafts.get(i).getCapacity();
        }
        return total;
    }
}
