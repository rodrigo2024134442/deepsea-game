package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

/**
 * Represents the trench state of the FSM.
 *
 * <p>This state is active while a drone is moving through the sea trench,
 * either descending from the surface to the sea floor or ascending back
 * from the sea floor to the surface.</p>
 *
 * <p>The state checks if the drone was destroyed, reached the top of the
 * trench, or reached the bottom of the trench, and changes the FSM state
 * accordingly.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
public class Trench extends GameStateAdapter {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new trench state.
     *
     * @param context finite state machine context
     * @param game main game model
     */
    public Trench(GameContext context, Game game) {
        super(context, game);
    }

    /**
     * Gets the enum value that identifies this state.
     *
     * @return {@link GameState#TRENCH}
     */
    @Override
    public GameState getState() {
        return GameState.TRENCH;
    }

    /**
     * Moves the active drone inside the trench.
     *
     * <p>If the drone is destroyed, it is returned to the ship and the FSM
     * changes to the surface state. If the drone reaches the top of the trench,
     * it enters the surface. If it reaches the bottom while descending, it enters
     * the sea floor state.</p>
     *
     * @param command movement command ("w", "a", "s", "d")
     * @return true if the action was executed, false otherwise
     */
    @Override
    public boolean move(String command) {
        if (!game.moveDroneInTrench(command))
            return false;

        if (game.isActiveDroneDestroyed()) {
            game.returnDroneToShip();
            changeState(GameState.SURFACE.createState(context, game));
            return true;
        }

        if (game.hasDroneReachedTop()) {
            game.enterSurface();
            changeState(GameState.SURFACE.createState(context, game));
        }

        if (game.hasDroneReachedBottom() && !game.getIsLastStateBotom()) {
            game.enterSeaFloor();
            changeState(GameState.BOTTOM.createState(context, game));
            return true;
        }

        return true;
    }
}