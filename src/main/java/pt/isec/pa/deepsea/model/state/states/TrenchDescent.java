package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

/**
 * Represents the descending phase of the trench state in the FSM.
 *
 * <p>This state is active while a drone is moving down through the sea trench
 * from the surface to the sea floor. In this phase the drone is empty, so if
 * it gets destroyed or runs out of fuel it simply returns to the ship without
 * dropping any resources.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
public class TrenchDescent extends GameStateAdapter {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new trench-descent state.
     *
     * @param context finite state machine context
     * @param game main game model
     */
    public TrenchDescent(GameContext context, Game game) {
        super(context, game);
    }

    /**
     * Gets the enum value that identifies this state.
     *
     * @return {@link GameState#TRENCH_DESCENT}
     */
    @Override
    public GameState getState() {
        return GameState.TRENCH_DESCENT;
    }

    /**
     * Moves the active drone down the trench.
     *
     * <p>If the drone is destroyed or runs out of fuel, it is returned to the
     * ship and the FSM goes back to the surface. If it reaches the top, the
     * drone enters the surface. If it reaches the bottom, the FSM moves to
     * the sea floor.</p>
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
            return true;
        }

        if (game.hasDroneReachedBottom()) {
            game.enterSeaFloor();
            changeState(GameState.BOTTOM.createState(context, game));
            return true;
        }

        if (game.activeDroneHasNoFuelInTrench()) {
            game.returnDroneToShip();
            changeState(GameState.SURFACE.createState(context, game));
            return true;
        }

        return true;
    }
}