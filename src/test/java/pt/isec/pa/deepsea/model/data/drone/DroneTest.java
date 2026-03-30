package pt.isec.pa.deepsea.model.data.drone;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroneTest {

    @Test
    void testFuelConsumption() {
        Drone drone = new Drone();
        drone.consumeFuel(50);

        assertEquals(50, drone.getCurrentFuel());
    }

    @Test
    void testDamageTaken() {
        Drone drone = new Drone();
        drone.takeDamage(50);

        assertEquals(50, drone.getHullIntegrity());
    }

    @Test
    void testDroneDestroyed() {
        Drone drone = new Drone();
        drone.takeDamage(200);

        assertTrue(drone.isDestroyed());
    }

    @Test
    void testDroneOutOfFuel() {
        Drone drone = new Drone();
        drone.consumeFuel(100);

        assertTrue(drone.isOutOfFuel());
    }
}