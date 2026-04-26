package pt.isec.pa.deepsea.model.data.minigame;

import pt.isec.pa.deepsea.model.data.GameConfig;

import java.util.Random;

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
        shuffle();
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
        shuffle();
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

    /**

     Shuffles the board by performing random valid moves.
     This guarantees the puzzle is always solvable.
     The number of shuffle moves is defined by {@link GameConfig#PUZZLE_SHUFFLE_MOVES}.*/
    public void shuffle() {
        Random rand = new Random();
        String[] directions = {"w", "a", "s", "d"};

        for (int i = 0; i < PUZZLE_SHUFFLE_MOVES; i++) {
            String dir = directions[rand.nextInt(4)];

            int newRow = emptyRow;
            int newCol = emptyCol;

            switch (dir) {
                case "w" -> newRow--;
                case "s" -> newRow++;
                case "a" -> newCol--;
                case "d" -> newCol++;
            }

            if (newRow >= 0 && newRow < PUZZLE_SIZE && newCol >= 0 && newCol < PUZZLE_SIZE) {
                board[emptyRow][emptyCol] = board[newRow][newCol];
                board[newRow][newCol] = 0;
                emptyRow = newRow;
                emptyCol = newCol;
            }
        }
    }

    /**
     Moves a piece adjacent to the empty space into it.
     Only pieces directly adjacent (up, down, left, right) can move.*
     @param row row of the piece to move
     @param col column of the piece to move
     @return true if the move was valid and executed*/
    public boolean movePiece(int row, int col) {
        if (isOutOfMoves()) return false;
        if (row < 0 || row >= PUZZLE_SIZE ||  col < 0 || col >= PUZZLE_SIZE) return false;

        int rowDiff = Math.abs(row - emptyRow);
        int colDiff = Math.abs(col - emptyCol);

        if (rowDiff + colDiff != 1) return false;

        board[emptyRow][emptyCol] = board[row][col];
        board[row][col] = 0;
        emptyRow = row;
        emptyCol = col;
        moveCount++;

        return true;
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