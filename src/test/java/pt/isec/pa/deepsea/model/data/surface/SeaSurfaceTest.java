package pt.isec.pa.deepsea.model.data.surface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.surface.SeaSurface;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SeaSurface} class.
 */
class SeaSurfaceTest {

    private SeaSurface surface;

    @BeforeEach
    void setUp() {
        surface = new SeaSurface();
    }

    @Test
    void testDefaultDimensions() {
        assertEquals(GameConfig.DEFAULT_SEASURFACE_ROWS, surface.getRows());
        assertEquals(GameConfig.DEFAULT_SEASURFACE_COLS, surface.getCols());
    }


    @Test
    void testIsValidPosition() {
        assertTrue(surface.isValidPosition(0, 0));
        assertTrue(surface.isValidPosition(GameConfig.DEFAULT_SEASURFACE_COLS - 1, GameConfig.DEFAULT_SEASURFACE_ROWS - 1));
        assertFalse(surface.isValidPosition(-1, 0));
        assertFalse(surface.isValidPosition(GameConfig.DEFAULT_SEASURFACE_COLS, 0));
    }

    @Test
    void testMarkAndCheckVisited() {
        assertFalse(surface.isVisited(3, 3));
        surface.markVisited(3, 3);
        assertTrue(surface.isVisited(3, 3));
    }

}
