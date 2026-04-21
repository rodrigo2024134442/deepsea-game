package pt.isec.pa.deepsea.model.data.surface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.ship.Ship;

import static org.junit.jupiter.api.Assertions.*;

class SeaSurfaceTest {

    private SeaSurface surface;
    private Ship ship;
    @BeforeEach
    void setUp() {
        ship = new Ship();
        surface = new SeaSurface(GameConfig.DEFAULT_SEASURFACE_ROWS,GameConfig.DEFAULT_SEASURFACE_COLS,ship);
    }

    @Test
    void testDimensions() {
        assertEquals(GameConfig.DEFAULT_SEASURFACE_ROWS, surface.getRows());
        assertEquals(GameConfig.DEFAULT_SEASURFACE_COLS, surface.getCols());
        SeaSurface custom = new SeaSurface(5, 10,ship);
        assertEquals(5, custom.getRows());
        assertEquals(10, custom.getCols());
    }

    @Test
    void testAllCellsInitialized() {
        for (int r = 0; r < surface.getRows(); r++)
            for (int c = 0; c < surface.getCols(); c++)
                assertNotNull(surface.getCell(r, c));
    }

    @Test
    void testPositionValidation() {
        assertTrue(surface.isValidPosition(0, 0));
        assertTrue(surface.isValidPosition(surface.getRows() - 1, surface.getCols() - 1));

        assertFalse(surface.isValidPosition(-1, 0));
        assertFalse(surface.isValidPosition(surface.getRows(), 0));
        assertNull(surface.getCell(-1, 0));
    }

    @Test
    void testMarkVisitedLogic() {
        assertFalse(surface.isVisited(0, 0));
        surface.markVisited(0, 0);
        assertTrue(surface.isVisited(0, 0));
        assertFalse(surface.isVisited(0, 1));
        assertDoesNotThrow(() -> surface.markVisited(-1, -1));
    }

    @Test
    void testArtifactConsistency() {
        int total = 0;
        boolean foundAny = false;

        for (int r = 0; r < surface.getRows(); r++) {
            for (int c = 0; c < surface.getCols(); c++) {
                int count = surface.getCell(r, c).getArtifactCount();
                total += count;
                if (count > 0) foundAny = true;
                assertTrue(count <= GameConfig.MAX_CELL_ARTIFACTS);
            }
        }

        assertEquals(GameConfig.MAX_ARTIFACTS, total);
        assertTrue(foundAny);
    }
}