package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

/**
 * Represents the surface state of the FSM.
 *
 * <p>This is the default state where the ship moves across the ocean surface.
 * From here, the player can navigate the ship, enter the shop, or deploy a drone
 * into the trench.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
public class Surface extends GameStateAdapter {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new surface state.
     *
     * @param context finite state machine context
     * @param game main game model
     */
    public Surface(GameContext context, Game game) {
        super(context, game);
    }

    /**
     * Gets the enum value that identifies this state.
     *
     * @return {@link GameState#SURFACE}
     */
    @Override
    public GameState getState() {
        return GameState.SURFACE;
    }

    /**
     * Moves the ship on the surface grid.
     *
     * @param command movement command ("w", "a", "s", "d")
     * @return true if the movement was valid, false otherwise
     */
    @Override
    public boolean move(String command) {
        return game.moveShip(command);
    }

    /**
     * Enters the shop state.
     *
     * @return true after changing to the shop state
     */
    @Override
    public boolean enterShop() {
        changeState(GameState.SHOP.createState(context, game));
        return true;
    }

    /**
     * Launches a drone into the trench.
     *
     * <p>If the drone launch is successful, the FSM transitions to the trench state.</p>
     *
     * @param droneId identifier of the drone to deploy
     * @return true if the drone was deployed, false otherwise
     */
    @Override
    public boolean descend(int droneId) {
        if (!game.descend(droneId))
            return false;

        changeState(GameState.TRENCH.createState(context, game));
        return true;
    }
}