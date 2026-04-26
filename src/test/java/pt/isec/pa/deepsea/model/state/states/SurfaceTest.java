package pt.isec.pa.deepsea.model.state.states;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Surface state class.
 *
 * <p>These tests validate the basic behavior of the surface FSM state,
 * including state identification and transitions to other states.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
class SurfaceTest {

    /**
     * Tests if the Surface state correctly identifies itself as SURFACE.
     */
    @Test
    void getStateReturnsSurface() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        Surface surface = new Surface(context, game);

        assertEquals(GameState.SURFACE, surface.getState());
    }

    /**
     * Tests that entering the shop changes the state to SHOP.
     */
    @Test
    void enterShopChangesState() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        Surface surface = new Surface(context, game);

        assertTrue(surface.enterShop());
        assertEquals(GameState.SHOP, context.getState());
    }
}