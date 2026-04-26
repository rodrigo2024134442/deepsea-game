package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

/**
 * Represents the minigame state of the FSM.
 *
 * <p>This state is active when the player is solving the puzzle associated
 * with artifact retrieval. It allows moving puzzle pieces and handles the
 * result of the minigame.</p>
 *
 * <p>If the puzzle is solved, the FSM returns to the sea floor state.
 * If the player runs out of moves, the minigame ends, resources may be lost,
 * and the FSM returns to the surface state.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
public class PuzzleGame extends GameStateAdapter {

    /**
     * Creates a new minigame state.
     *
     * @param context finite state machine context
     * @param game main game model
     */
    public PuzzleGame(GameContext context, Game game) {
        super(context, game);
    }

    /**
     * Gets the enum value that identifies this state.
     *
     * @return {@link GameState#MINIGAME}
     */
    @Override
    public GameState getState() {
        return GameState.MINIGAME;
    }

    /**
     * Moves a puzzle piece in the minigame board.
     *
     * <p>If the move solves the puzzle, the minigame is cleared and the FSM
     * returns to the sea floor state. If the move limit is exceeded, the
     * minigame is cleared, resources are lost, the drone returns to the ship,
     * and the FSM changes to the surface state.</p>
     *
     * @param row row of the piece to move
     * @param col column of the piece to move
     * @return true if the move was valid and processed, false otherwise
     */
    @Override
    public boolean movePiece(int row, int col) {
        if (!game.movePiece(row, col)) {
            return false;
        }

        if (game.isMiniGameSolved()) {
            game.clearMiniGame();
            changeState(GameState.BOTTOM.createState(context, game));
            return true;
        }

        if (game.isMiniGameOutOfMoves()) {
            game.clearMiniGame();
            game.loseResources();
            game.returnDroneToShip();
            changeState(GameState.SURFACE.createState(context, game));
            return true;
        }

        return true;
    }

    /**
     * Forces a successful minigame result.
     *
     * <p>This method is mainly useful for testing and redirects the FSM back
     * to the sea floor state.</p>
     *
     * @return true after changing to the sea floor state
     */
    @Override
    public boolean winMini() {
        changeState(GameState.BOTTOM.createState(context, game));
        return true;
    }

    /**
     * Forces a failed minigame result.
     *
     * <p>The active drone is returned to the ship and the FSM changes to the
     * surface state.</p>
     *
     * @return true after changing to the surface state
     */
    @Override
    public boolean loseMini() {
        game.returnDroneToShip();
        changeState(GameState.SURFACE.createState(context, game));
        return true;
    }
}