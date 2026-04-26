package pt.isec.pa.deepsea.model.state.states;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Bottom state class.
 * These tests validate the basic behavior of the sea floor state,
 * including state identification and invalid mining without an active drone.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
class BottomTest {

    /**
     * Tests if the Bottom state correctly identifies itself as BOTTOM.
     */
    @Test
    void getStateReturnsBottom() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        Bottom bottom = new Bottom(context, game);

        assertEquals(GameState.BOTTOM, bottom.getState());
    }

    /**
     * Tests that mining fails when there is no active drone on the sea floor.
     */
    @Test
    void miningFailsWhenThereIsNoActiveDrone() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        Bottom bottom = new Bottom(context, game);

        assertFalse(bottom.mining());
    }
}