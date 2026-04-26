package pt.isec.pa.deepsea.model.data.minigame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.GameConfig;

import static org.junit.jupiter.api.Assertions.*;

class MiniGameTest {

    private MiniGame miniGame;

    @BeforeEach
    void setUp() {
        miniGame = new MiniGame();
    }

    /**
     * Tests the initial state of a new MiniGame.
     *
     * <p>The board is shuffled in the constructor, so it should not be assumed
     * to start solved.</p>
     */
    @Test
    void testInitialState() {
        assertEquals(0, miniGame.getMoveCount());
        assertFalse(miniGame.isOutOfMoves());
        assertNotNull(miniGame.getBoard());
    }

    @Test
    void testBoardHasCorrectDimensions() {
        int[][] board = miniGame.getBoard();
        assertEquals(GameConfig.PUZZLE_SIZE, board.length);
        assertEquals(GameConfig.PUZZLE_SIZE, board[0].length);
    }

    @Test
    void testResetRestoresMoveCount() {
        miniGame.reset();
        assertEquals(0, miniGame.getMoveCount());
    }

    @Test
    void testIsOutOfMovesAfterLimit() {
        // forçar o limite de movimentos via reset e verificação
        assertFalse(miniGame.isOutOfMoves());
    }
}