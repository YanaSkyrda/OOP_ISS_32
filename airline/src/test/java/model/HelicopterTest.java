package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelicopterTest {
    @Test
    void helicopterTest() {
        Aircraft helicopter = new Helicopter("helicopter", 10, 20, 30, 500);
        assertEquals(helicopter.getFuel(), 30);
        assertEquals(helicopter.getPayload(), 10);
        assertEquals(helicopter.getRangeOfFlight(), 20);
        assertEquals(helicopter.getCapacity(), 500);
        helicopter.setFuel(15);
        assertEquals(helicopter.getFuel(),15);
        helicopter.setPayload(200);
        assertEquals(helicopter.getPayload(), 200);
        helicopter.setRangeOfFlight(500);
        assertEquals(helicopter.getRangeOfFlight(),500);
        helicopter.setCapacity(20);
        assertEquals(helicopter.getCapacity(), 20);
    }

}