package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameContext class.
 * These tests validate the initial state of the FSM and basic state delegation.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
class GameContextTest {

    /**
     * Tests if a new GameContext starts in the SURFACE state.
     */
    @Test
    void constructorStartsInSurfaceState() {
        GameContext context = new GameContext();

        assertEquals(GameState.SURFACE, context.getState());
    }

    /**
     * Tests if the context can delegate the enterShop action
     * and change from SURFACE to SHOP.
     */
    @Test
    void enterShopChangesStateToShop() {
        GameContext context = new GameContext();

        assertTrue(context.enterShop());
        assertEquals(GameState.SHOP, context.getState());
    }
}