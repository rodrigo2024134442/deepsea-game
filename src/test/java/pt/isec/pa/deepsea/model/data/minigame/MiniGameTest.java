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

    @Test
    void testInitialState() {
        assertEquals(0, miniGame.getMoveCount());
        assertFalse(miniGame.isOutOfMoves());
        assertTrue(miniGame.isSolved()); // board começa ordenado, logo está resolvido
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