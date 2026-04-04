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

    @Test
    void testGameInitializesCorrectly() {
        assertNotNull(game.getSurfaceGrid());
        assertNotNull(game.getShip());
    }

    @Test
    void testShipStartsAtCenterOfSurface() {
        assertEquals(GameConfig.DEFAULT_SEASURFACE_COLS / 2, game.getShip().getPosX());
        assertEquals(GameConfig.DEFAULT_SEASURFACE_ROWS / 2, game.getShip().getPosY());
    }
}