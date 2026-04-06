package pt.isec.pa.deepsea.model.data.surface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.GameConfig;

import static org.junit.jupiter.api.Assertions.*;

class SurfaceCellTest {

    private SurfaceCell cell;

    @BeforeEach
    void setUp() {
        cell = new SurfaceCell(
                GameConfig.DEFAULT_SEATRENCH_ROWS,
                GameConfig.DEFAULT_SEATRENCH_COLS
        );
    }

    @Test
    void testInitialState() {
        assertFalse(cell.isVisited());
        assertEquals(0, cell.getArtifactCount());
        assertNotNull(cell.getTrench());
    }

    @Test
    void testArtifactCollectionLifecycle() {
        cell.addArtifact();
        cell.addArtifact();
        assertEquals(2, cell.getArtifactCount());

        int collected = cell.collectArtifacts();
        assertEquals(2, collected);
        assertEquals(0, cell.getArtifactCount());
        assertEquals(0, cell.collectArtifacts());
    }

    @Test
    void testVisitedToggle() {
        cell.setVisited(true);
        assertTrue(cell.isVisited());
        cell.setVisited(false);
        assertFalse(cell.isVisited());
    }
}