package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.deepsea.model.DeepSeaManager;

/**
 * Canvas that shows the trench grid during descent/ascent and bottom phases.
 *
 * <p>Follows the MVC@PA pattern from the DrawingApp lecture:
 * the constructor registers handlers and triggers the first draw,
 * and subsequent redraws are triggered by PropertyChange events
 * coming from the {@link DeepSeaManager}.</p>
 */
public class TrenchCanvas extends Canvas {
    DeepSeaManager manager;
    private double cellWidth = 50;
    private double cellHeight = 50;

    public TrenchCanvas(DeepSeaManager manager) {
        super(500, 500);                // initial size, will be resized by RootPane
        this.manager = manager;
        setFocusTraversable(true);
        registerHandlers();
        update();
        // RequestFocus on mouse click to ensure keyboard input works
        setOnMouseClicked(e -> requestFocus());
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GRID, e -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONES, e -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME_STATE, e -> update());

        // Keyboard handler para W,A,S,D
        setOnKeyPressed(this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        String command = null;

        switch (event.getCode()) {
            case W -> command = "w";  // up
            case A -> command = "a";  // left
            case S -> command = "s";  // down
            case D -> command = "d";  // right
            default -> { }
        }

        if (command != null) {
            manager.move(command);
            event.consume();
        }
    }

    public void updateSize(double newWidth, double newHeight) {
        setWidth(newWidth);
        setHeight(newHeight);
        update();
    }

    private void update() {
        GraphicsContext gc = getGraphicsContext2D();
        clearScreen(gc);

        int rows = manager.getTrenchRows();
        int cols = manager.getTrenchCols();

        if (rows == 0 || cols == 0) return;

        calculateCellSize(rows, cols);

        drawGrid(gc, rows, cols);
        drawEntities(gc, rows, cols);
        drawActiveDrone(gc);
    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    private void calculateCellSize(int rows, int cols) {
        cellWidth = getWidth() / cols;
        cellHeight = getHeight() / rows;
    }

    private void drawGrid(GraphicsContext gc, int rows, int cols) {
        gc.setStroke(Color.LIGHTBLUE);
        gc.setLineWidth(1);

        // Linhas horizontais
        for (int r = 0; r <= rows; r++) {
            double y = r * cellHeight;
            gc.strokeLine(0, y, getWidth(), y);
        }

        // Linhas verticais
        for (int c = 0; c <= cols; c++) {
            double x = c * cellWidth;
            gc.strokeLine(x, 0, x, getHeight());
        }
    }

    private void drawEntities(GraphicsContext gc, int rows, int cols) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                String entity = manager.getTrenchCellEntity(r, c);
                double x = c * cellWidth;
                double y = r * cellHeight;

                switch (entity) {
                    case "ROCK":
                        gc.setFill(Color.GRAY);
                        gc.fillRect(x + 2, y + 2, cellWidth - 4, cellHeight - 4);
                        break;
                    case "ANIMAL":
                        gc.setFill(Color.RED);
                        gc.fillOval(x + 5, y + 5, cellWidth - 10, cellHeight - 10);
                        break;
                    case "CURRENT":
                        gc.setFill(Color.CYAN);
                        gc.fillPolygon(
                                new double[]{x + cellWidth/2, x + cellWidth, x},
                                new double[]{y, y + cellHeight, y + cellHeight},
                                3
                        );
                        break;
                }
            }
        }
    }

    private void drawActiveDrone(GraphicsContext gc) {
        int droneRow = manager.getActiveDroneRow();
        int droneCol = manager.getActiveDroneCol();

        if (droneRow < 0 || droneCol < 0) return;

        double x = droneCol * cellWidth;
        double y = droneRow * cellHeight;
        double margin = 8;

        // Desenhar drone com border
        gc.setFill(Color.GOLD);
        gc.fillOval(x + margin, y + margin, cellWidth - 2 * margin, cellHeight - 2 * margin);

        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(2);
        gc.strokeOval(x + margin, y + margin, cellWidth - 2 * margin, cellHeight - 2 * margin);

        // Desenhar ID do drone (opcional)
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(10));
        gc.fillText("D", x + cellWidth / 2 - 5, y + cellHeight / 2 + 3);

    }

}