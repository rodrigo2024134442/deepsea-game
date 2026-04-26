package pt.isec.pa.deepsea.model.state.states;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Shop state class.
 *
 * <p>These tests validate the basic behavior of the shop FSM state,
 * including state identification and transition back to the surface state.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
class ShopTest {

    /**
     * Tests if the Shop state correctly identifies itself as SHOP.
     */
    @Test
    void getStateReturnsShop() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        Shop shop = new Shop(context, game);

        assertEquals(GameState.SHOP, shop.getState());
    }

    /**
     * Tests that leaving the shop state returns true.
     */
    @Test
    void leaveShopReturnsTrue() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        Shop shop = new Shop(context, game);

        assertTrue(shop.leaveShop());
    }
}