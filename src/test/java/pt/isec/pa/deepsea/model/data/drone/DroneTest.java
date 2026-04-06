package pt.isec.pa.deepsea.model.data.drone;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.GameConfig;

import static org.junit.jupiter.api.Assertions.*;

class DroneTest {

    @Test
    void testFuelConsumption() {
        Drone drone = new Drone();
        drone.consumeFuel(50);
        assertEquals(GameConfig.DRONE_MAX_FUEL - 50, drone.getCurrentFuel()); // 150
    }

    @Test
    void testDamageTaken() {
        Drone drone = new Drone();
        drone.takeDamage();
        assertEquals(GameConfig.DRONE_MAX_INTEGRITY - 1, drone.getHullIntegrity()); // 99
    }

    @Test
    void testDroneDestroyed() {
        Drone drone = new Drone();

        while (!drone.isDestroyed()) {
            drone.takeDamage();
        }

        assertTrue(drone.isDestroyed());
        assertEquals(0, drone.getHullIntegrity());
    }

    @Test
    void testDroneOutOfFuel() {
        Drone drone = new Drone();
        drone.consumeFuel(GameConfig.DRONE_MAX_FUEL); // consome tudo
        assertTrue(drone.isOutOfFuel());
    }
}