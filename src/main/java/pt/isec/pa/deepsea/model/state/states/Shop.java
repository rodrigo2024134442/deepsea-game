package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

/**
 * Represents the shop state of the FSM.
 *
 * <p>In this state, the player can repair and refuel drones.
 * The player can also leave the shop and return to the surface state.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
public class Shop extends GameStateAdapter {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new shop state.
     *
     * @param context finite state machine context
     * @param game main game model
     */
    public Shop(GameContext context, Game game) {
        super(context, game);
    }

    /**
     * Gets the enum value that identifies this state.
     *
     * @return {@link GameState#SHOP}
     */
    @Override
    public GameState getState() {
        return GameState.SHOP;
    }

    /**
     * Leaves the shop and returns to the surface state.
     *
     * @return true after changing to the surface state
     */
    @Override
    public boolean leaveShop() {
        changeState(GameState.SURFACE.createState(context, game));
        return true;
    }

    /**
     * Repairs a selected drone.
     *
     * @param droneId identifier of the drone to repair
     * @return true if the drone was repaired, false otherwise
     */
    @Override
    public boolean repair(int droneId) {
        return game.repair(droneId);
    }

    /**
     * Refuels a selected drone.
     *
     * @param droneId identifier of the drone to refuel
     * @return true if the drone was refueled, false otherwise
     */
    @Override
    public boolean refuel(int droneId) {
        return game.refuel(droneId);
    }
}