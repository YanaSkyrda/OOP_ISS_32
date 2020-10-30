package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AirlineTest {
    @Test
    void testAirline() {
        Airline airport = new Airline("airport");
        Aircraft airplaneA = new Airplane("A", 10,20,20,500);
        Aircraft airplaneB = new Airplane("B", 10,100,100,500);
        Aircraft airplaneC = new Airplane("C", 10,70,70, 500);
        Aircraft airplaneD = new Airplane("A", 10,10,30, 500);

        airport.addAircraft(airplaneA);
        airport.addAircraft(airplaneB);
        airport.addAircraft(airplaneC);
        airport.addAircraft(airplaneD);
        airport.sortAircraftsByRangeOfFlight();

        Aircraft arr[] = new Aircraft[]{airplaneD,airplaneA,airplaneC,airplaneB};
        List<Aircraft> expectedSortedAircrafts = new ArrayList<>(Arrays.asList(arr));

        assertEquals(airport.getAllAircrafts(), expectedSortedAircrafts);

        List<Aircraft> expecteFoundAircrafts = new ArrayList<>();
        expecteFoundAircrafts.add(airplaneC);
        expecteFoundAircrafts.add(airplaneB);
        assertEquals(airport.getAircraftsByFuelRange(50, 150), expecteFoundAircrafts);

        assertEquals(airport.getTotalPayload(),40);

        assertEquals(airport.getTotalCapacity(), 2000);
    }
}