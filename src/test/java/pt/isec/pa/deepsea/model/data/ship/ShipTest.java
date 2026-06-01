package pt.isec.pa.deepsea.model.data.ship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.entity.Artifact;
import pt.isec.pa.deepsea.model.data.entity.Mineral;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Ship} class.
 */
class ShipTest {

    private Ship ship;

    @BeforeEach
    void setUp() {
        ship = new Ship();
    }

    @Test
    void testConsumeFuel() {
        ship.consumeFuel(100);
        assertEquals(GameConfig.SHIP_MAX_FUEL - 100, ship.getCurrentFuel());
    }

    @Test
    void testConsumeFuelDoesNotGoBelowZero() {
        ship.consumeFuel(GameConfig.SHIP_MAX_FUEL + 500);
        assertEquals(0, ship.getCurrentFuel());
        assertTrue(ship.isOutOfFuel());
    }

    @Test
    void testConsumeFuelIgnoresNonPositive() {
        int initial = ship.getCurrentFuel();
        ship.consumeFuel(0);
        ship.consumeFuel(-50);
        assertEquals(initial, ship.getCurrentFuel());
    }

    @Test
    void testInitialState() {
        assertEquals(GameConfig.SHIP_MAX_FUEL, ship.getCurrentFuel());
        assertEquals(GameConfig.START_DRONES, ship.getDrones().size());

        assertEquals(-1, ship.getPosition().getRow());
        assertEquals(-1, ship.getPosition().getCol());

        assertEquals(0, ship.getMinerals());
        assertTrue(ship.getCollectedArtifacts().isEmpty());
        assertTrue(ship.getCollectedMinerals().isEmpty());

        assertFalse(ship.isOutOfFuel());
        assertFalse(ship.allDronesLost());
    }

    @Test
    void testAddAndGetArtifacts() {
        Artifact a = new Artifact("teste");
        ship.addArtifact(a);
        assertEquals(1, ship.getCollectedArtifacts().size());
        assertEquals(a, ship.getCollectedArtifacts().get(0));
    }

    @Test
    void testAddAndGetMinerals() {
        Mineral m = new Mineral("TESTE");
        ship.addMineral(m);
        assertEquals(1, ship.getCollectedMinerals().size());
        assertEquals(m, ship.getCollectedMinerals().get(0));
    }

    @Test
    void testGetDrone() {
        ship.getDrones().forEach(d -> assertNotNull(ship.getDroneById(d.getId())));
        assertNull(ship.getDroneById(-1));
        assertNull(ship.getDroneById(999));
    }
}
