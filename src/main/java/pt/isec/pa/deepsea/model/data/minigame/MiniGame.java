package pt.isec.pa.deepsea.model.data.minigame;

import pt.isec.pa.deepsea.model.data.GameConfig;

/**
 * Represents the sliding puzzle minigame (15-puzzle) used to collect artifacts.
 * Manages the state of the 4x4 grid with 15 numbered pieces and one empty space,
 * and tracks the number of moves made.
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 1)
 */
public class MiniGame implements GameConfig {
    private final int[][] board;
    private int emptyRow;
    private int emptyCol;
    private int moveCount;

    /**
     * Creates a new minigame with a shuffled board and move counter at zero.
     */
    public MiniGame() {
        this.board = new int[PUZZLE_SIZE][PUZZLE_SIZE];
        this.moveCount = 0;
        initialize();
    }

    /**
     * Initializes the board with pieces 1-15 and one empty space (represented by 0).
     * The empty space starts at the bottom-right corner.
     */
    private void initialize() {
        int num = 1;
        for (int r = 0; r < PUZZLE_SIZE; r++) {
            for (int c = 0; c < PUZZLE_SIZE; c++) {
                board[r][c] = num++;
            }
        }
        // Empty space represented by 0, starts at bottom-right
        board[PUZZLE_SIZE - 1][PUZZLE_SIZE - 1] = 0;
        emptyRow = PUZZLE_SIZE - 1;
        emptyCol = PUZZLE_SIZE - 1;
    }

    /**
     * Gets the value of a board cell.
     *
     * @param row row of the cell
     * @param col column of the cell
     * @return value at that position (0 represents empty space)
     */
    public int getCell(int row, int col) {
        if (row < 0 || row >= PUZZLE_SIZE || col < 0 || col >= PUZZLE_SIZE)
            return -1;
        return board[row][col];
    }

    /**
     * Gets the current move count.
     *
     * @return number of moves made
     */
    public int getMoveCount() { return moveCount; }

    /**
     * Gets the row of the empty space.
     *
     * @return empty space row
     */
    public int getEmptyRow() { return emptyRow; }

    /**
     * Gets the column of the empty space.
     *
     * @return empty space column
     */
    public int getEmptyCol() { return emptyCol; }

    /**
     * Checks if the move limit has been exceeded.
     *
     * @return true if move count reached the maximum
     */
    public boolean isOutOfMoves() { return moveCount >= PUZZLE_MAX_MOVES; }

    /**
     * Checks if the puzzle is solved (pieces 1-15 in order, empty space at end).
     *
     * @return true if solved
     */
    public boolean isSolved() {
        int expected = 1;
        for (int r = 0; r < PUZZLE_SIZE; r++) {
            for (int c = 0; c < PUZZLE_SIZE; c++) {
                if (r == PUZZLE_SIZE - 1 && c == PUZZLE_SIZE - 1) {
                    return board[r][c] == 0;
                }
                if (board[r][c] != expected++) return false;
            }
        }
        return true;
    }

    /**
     * Resets the board and move counter for a new attempt.
     */
    public void reset() {
        moveCount = 0;
        initialize();
    }

    /**
     * Gets a copy of the full board state.
     *
     * @return copy of the board
     */
    public int[][] getBoard() {
        int[][] copy = new int[PUZZLE_SIZE][PUZZLE_SIZE];
        for (int r = 0; r < PUZZLE_SIZE; r++)
            copy[r] = board[r].clone();
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < PUZZLE_SIZE; r++) {
            for (int c = 0; c < PUZZLE_SIZE; c++) {
                sb.append(board[r][c] == 0 ? "  " : String.format("%2d", board[r][c]));
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
