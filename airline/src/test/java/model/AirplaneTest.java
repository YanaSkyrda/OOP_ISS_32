package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirplaneTest {
    @Test
    void testAirplane(){
        Aircraft airplane = new Airplane("airplane",10,20, 30,500);
        assertEquals(airplane.getFuel(), 30);
        assertEquals(airplane.getPayload(), 10);
        assertEquals(airplane.getRangeOfFlight(), 20);
        airplane.setFuel(15);
        assertEquals(airplane.getFuel(),15);
        airplane.setPayload(200);
        assertEquals(airplane.getPayload(), 200);
        airplane.setRangeOfFlight(500);
        assertEquals(airplane.getRangeOfFlight(),500);
    }
}