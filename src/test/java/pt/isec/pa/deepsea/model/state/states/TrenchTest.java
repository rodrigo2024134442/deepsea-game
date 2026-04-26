package pt.isec.pa.deepsea.model.state.states;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Trench state class.
 *
 * <p>These tests validate the basic behavior of the trench FSM state,
 * including state identification and invalid movement when no active drone exists.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
class TrenchTest {

    /**
     * Tests if the Trench state correctly identifies itself as TRENCH.
     */
    @Test
    void getStateReturnsTrench() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        Trench trench = new Trench(context, game);

        assertEquals(GameState.TRENCH, trench.getState());
    }

    /**
     * Tests that moving in the trench fails when there is no active drone.
     */
    @Test
    void moveFailsWhenNoActiveDroneExists() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        Trench trench = new Trench(context, game);

        assertFalse(trench.move("s"));
    }
}