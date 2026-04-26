package pt.isec.pa.deepsea.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.GameConfig;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.start();
    }

    /**
     * Tests if the game initializes the ship data correctly.
     */
    @Test
    void testGameInitializesCorrectly() {
        assertEquals(GameConfig.SHIP_MAX_FUEL, game.getShipFuel());
        assertEquals(GameConfig.SHIP_MAX_FUEL, game.getShipMaxFuel());
        assertFalse(game.isShipOutOfFuel());
    }

    /**
     * Tests if the ship starts approximately at the center of the surface grid.
     */
    @Test
    void testShipStartsAtCenterOfSurface() {
        assertEquals(GameConfig.DEFAULT_SEASURFACE_COLS / 2, game.getShipX());
        assertEquals(GameConfig.DEFAULT_SEASURFACE_ROWS / 2, game.getShipY());
    }
}