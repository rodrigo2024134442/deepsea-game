package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import pt.isec.pa.deepsea.model.DeepSeaManager;

/**
 * Canvas responsible for rendering the 15-puzzle minigame.
 * The player clicks on a piece adjacent to the empty space to slide it.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Draw the puzzle grid and numbered tiles.</li>
 *   <li>Show current move count and limit.</li>
 *   <li>Translate mouse clicks into {@link DeepSeaManager#movePiece(int, int)}.</li>
 *   <li>Redraw when the minigame state changes.</li>
 * </ul>
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 3)
 */
public class PuzzleCanvas extends Canvas {

    private final DeepSeaManager manager;

    private int size;
    private double cellSize;
    private double offsetX;
    private double offsetY;

    public PuzzleCanvas(DeepSeaManager manager) {
        super(500, 500);
        this.manager = manager;
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_MINIGAME, e -> update());

        setOnMouseClicked(this::handleMouseClick);
    }

    public void updateSize(double w, double h) {
        setWidth(w);
        setHeight(h);
        update();
    }

    private void update() {
        GraphicsContext gc = getGraphicsContext2D();
        recomputeGeometry();
        clearScreen(gc);
        if (size == 0) return;
        drawGrid(gc);
        drawPieces(gc);
        drawMoveCount(gc);
    }


    private void recomputeGeometry() {
        size     = manager.getMiniGameSize();
        cellSize = Math.min(getWidth() / size, getHeight() / size) * 0.8;
        offsetX  = (getWidth()  - cellSize * size) / 2;
        offsetY  = (getHeight() - cellSize * size) / 2;
    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.web("#1a1a2e"));
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.web("#16213e"));
        gc.setLineWidth(2);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                gc.strokeRect(colToX(c), rowToY(r), cellSize, cellSize);
            }
        }
    }

    private void drawPieces(GraphicsContext gc) {
        int[][] board = manager.getMiniGameBoard();
        if (board == null) return;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int value = board[r][c];

                if (value == 0) {
                    // Espaço vazio
                    gc.setFill(Color.web("#0f3460"));
                    gc.fillRect(colToX(c) + 2, rowToY(r) + 2, cellSize - 4, cellSize - 4);
                } else {
                    // Peça numerada
                    gc.setFill(Color.web("#e94560"));
                    gc.fillRoundRect(colToX(c) + 4, rowToY(r) + 4,
                            cellSize - 8, cellSize - 8, 10, 10);

                    gc.setFill(Color.WHITE);
                    gc.setFont(Font.font("Arial", cellSize * 0.35));
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.fillText(
                            String.valueOf(value),
                            colToX(c) + cellSize / 2,
                            rowToY(r) + cellSize / 2 + cellSize * 0.13
                    );
                }
            }
        }
    }

    private void drawMoveCount(GraphicsContext gc) {
        int moves    = manager.getMiniGameMoveCount();
        int maxMoves = manager.getMiniGameMaxMoves();

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 16));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(
                "Moves: " + moves + " / " + maxMoves,
                getWidth() / 2,
                offsetY - 15
        );
    }

    private void handleMouseClick(MouseEvent e) {
        int col = xToCol(e.getX());
        int row = yToRow(e.getY());

        if (col < 0 || col >= size || row < 0 || row >= size) return;

        manager.movePiece(row, col);
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------
    private double colToX(int c) { return offsetX + c * cellSize; }
    private double rowToY(int r) { return offsetY + r * cellSize; }
    private int    xToCol(double x) { return (int)((x - offsetX) / cellSize); }
    private int    yToRow(double y) { return (int)((y - offsetY) / cellSize); }
}