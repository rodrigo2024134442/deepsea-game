package pt.isec.pa.deepsea.model.data.seafloor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SeaFloor class.
 *
 * Note:
 * Some tests are intended for normal mode and others for defense mode.
 * To validate each group, DEFENSE_MODE must be set accordingly in GameConfig.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
class SeaFloorTest {

    /**
     * Tests if SeaFloor stores the correct grid dimensions.
     */
    @Test
    void seaFloorShouldStoreDimensionsCorrectly() {
        SeaFloor seaFloor = new SeaFloor(5, 7, 0);

        assertEquals(5, seaFloor.getRows());
        assertEquals(7, seaFloor.getCols());
    }

    /**
     * Verifies that invalid positions are rejected.
     */
    @Test
    void invalidPositionShouldReturnFalse() {
        SeaFloor seaFloor = new SeaFloor(5, 5, 0);

        assertFalse(seaFloor.isValidPosition(-1, 0));
        assertFalse(seaFloor.isValidPosition(0, -1));
        assertFalse(seaFloor.isValidPosition(5, 0));
        assertFalse(seaFloor.isValidPosition(0, 5));
    }

}