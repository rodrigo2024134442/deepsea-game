package pt.isec.pa.deepsea.model.state.states;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PuzzleGame state class.
 *
 * <p>These tests validate the basic behavior of the minigame FSM state,
 * including state identification and invalid puzzle movement when no active
 * minigame exists.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
class PuzzleGameTest {

    /**
     * Tests if the PuzzleGame state correctly identifies itself as MINIGAME.
     */
    @Test
    void getStateReturnsMiniGame() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        PuzzleGame puzzleGame = new PuzzleGame(context, game);

        assertEquals(GameState.MINIGAME, puzzleGame.getState());
    }

    /**
     * Tests that moving a puzzle piece fails when no minigame is active
     * in the game model.
     */
    @Test
    void movePieceFailsWhenNoMiniGameExists() {
        Game game = new Game();
        game.start();

        GameContext context = new GameContext();
        PuzzleGame puzzleGame = new PuzzleGame(context, game);

        assertFalse(puzzleGame.movePiece(0, 0));
    }
}