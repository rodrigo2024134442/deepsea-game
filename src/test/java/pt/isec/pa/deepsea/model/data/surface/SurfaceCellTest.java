package pt.isec.pa.deepsea.model.data.surface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.GameConfig;

import static org.junit.jupiter.api.Assertions.*;
import static pt.isec.pa.deepsea.model.data.GameConfig.*;
import static pt.isec.pa.deepsea.model.data.GameConfig.DEFAULT_SEAFLOOR_COLS;

class SurfaceCellTest {

    private SurfaceCell cell;

    @BeforeEach
    void setUp() {
        cell = new SurfaceCell(
                DEFAULT_SEATRENCH_ROWS,
                DEFAULT_SEATRENCH_COLS,0,DEFAULT_SEAFLOOR_ROWS,DEFAULT_SEAFLOOR_COLS
        );
    }

    @Test
    void testInitialState() {
        assertFalse(cell.isVisited());
        assertEquals(0, cell.getArtifactCount());
        assertNotNull(cell.getTrench());
    }

    @Test
    void testInitialArtifactCount() {
        SurfaceCell cellWithArtifacts = new SurfaceCell(
                DEFAULT_SEATRENCH_ROWS,
                DEFAULT_SEATRENCH_COLS,
                2,
                DEFAULT_SEAFLOOR_ROWS,
                DEFAULT_SEAFLOOR_COLS
        );

        assertEquals(2, cellWithArtifacts.getArtifactCount());
    }

    @Test
    void testCollectArtifactsResetsCount() {
        SurfaceCell cellWithArtifacts = new SurfaceCell(
                DEFAULT_SEATRENCH_ROWS,
                DEFAULT_SEATRENCH_COLS,
                2,
                DEFAULT_SEAFLOOR_ROWS,
                DEFAULT_SEAFLOOR_COLS
        );

        cellWithArtifacts.collectArtifacts();

        assertEquals(0, cellWithArtifacts.getArtifactCount());
    }
}