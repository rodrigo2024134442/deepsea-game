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
     * Verifies that the sea floor stores the number of rows and columns correctly.
     */
    @Test
    void seaFloorShouldStoreDimensionsCorrectly() {
        SeaFloor seaFloor = new SeaFloor(5, 7, 0);

        assertEquals(5, seaFloor.getRow());
        assertEquals(7, seaFloor.getCol());
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

    /**
     * Verifies that in normal mode the cells are not automatically revealed.
     *
     * Execute only with DEFENSE_MODE = false.
     */
    @Test
    void normalModeShouldNotRevealCellsAutomatically() {
        SeaFloor seaFloor = new SeaFloor(5, 5, 0);

        assertFalse(seaFloor.getCell(0, 0).isRevealed());
        assertFalse(seaFloor.getCell(2, 2).isRevealed());
    }

    /**
     * Verifies that in defense mode all cells are revealed.
     *
     * Execute only with DEFENSE_MODE = true.
     */
    @Test
    void defenseModeShouldRevealAllCells() {
        SeaFloor seaFloor = new SeaFloor(5, 5, 0);

        for (int r = 0; r < seaFloor.getRow(); r++) {
            for (int c = 0; c < seaFloor.getCol(); c++) {
                assertTrue(seaFloor.getCell(r, c).isRevealed());
            }
        }
    }
}