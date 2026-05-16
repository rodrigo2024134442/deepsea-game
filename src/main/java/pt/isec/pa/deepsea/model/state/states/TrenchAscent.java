package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

/**
 * Represents the ascending phase of the trench state in the FSM.
 *
 * <p>This state is active while a drone is moving up through the sea trench,
 * coming back from the sea floor towards the surface. In this phase the drone
 * may be carrying minerals and artifacts, so if it gets destroyed or runs out
 * of fuel the collected resources are dropped on the sea floor before the
 * drone returns to the ship.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
public class TrenchAscent extends GameStateAdapter {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new trench-ascent state.
     *
     * @param context finite state machine context
     * @param game main game model
     */
    public TrenchAscent(GameContext context, Game game) {
        super(context, game);
    }

    /**
     * Gets the enum value that identifies this state.
     *
     * @return {@link GameState#TRENCH_ASCENT}
     */
    @Override
    public GameState getState() {
        return GameState.TRENCH_ASCENT;
    }

    /**
     * Moves the active drone up the trench.
     *
     * <p>If the drone is destroyed or runs out of fuel, the carried resources
     * are dropped on the sea floor and the drone returns to the ship. If it
     * reaches the top of the trench, it enters the surface and delivers the
     * resources to the ship.</p>
     *
     * @param command movement command ("w", "a", "s", "d")
     * @return true if the action was executed, false otherwise
     */
    @Override
    public boolean move(String command) {
        if (!game.moveDroneInTrench(command))
            return false;

        if (game.isActiveDroneDestroyed()) {
            game.handleDroneWithoutFuelInTrench();
            changeState(GameState.SURFACE.createState(context, game));
            return true;
        }

        if (game.hasDroneReachedTop()) {
            game.enterSurface();
            changeState(GameState.SURFACE.createState(context, game));
            return true;
        }

        if (game.activeDroneHasNoFuelInTrench()) {
            game.handleDroneWithoutFuelInTrench();
            changeState(GameState.SURFACE.createState(context, game));
            return true;
        }

        return true;
    }
}