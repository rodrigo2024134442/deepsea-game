package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.deepsea.model.DeepSeaManager;

import java.util.Random;

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
    private int rows, cols;
    private double sizeCell, offsetX, offsetY;
    private Random rand = new Random();

    private static final Image IMG_DRONE = new Image(
            TrenchCanvas.class.getResourceAsStream("/images/vehicles/underwater_drone_yellow.png"));
    private static final Image IMG_TRENCH_BG = new Image(
            TrenchCanvas.class.getResourceAsStream("/images/backgrounds/trench_grid_background.png"));

    private static final Image IMG_ROCK = new Image(
            TrenchCanvas.class.getResourceAsStream("/images/objects/obstacle_dark_trench_rock.png"));

    private static final Image[] animalCollection = {
            new Image(TrenchCanvas.class.getResourceAsStream("/images/creatures/trench_animal1.png")),
            new Image(TrenchCanvas.class.getResourceAsStream("/images/creatures/trench_animal2.png")),
            new Image(TrenchCanvas.class.getResourceAsStream("/images/creatures/trench_animal3.png")),
            new Image(TrenchCanvas.class.getResourceAsStream("/images/creatures/trench_animal4.png")),
            new Image(TrenchCanvas.class.getResourceAsStream("/images/creatures/trench_animal5.png")),
            new Image(TrenchCanvas.class.getResourceAsStream("/images/creatures/trench_animal6.png"))
    };

    private static final Image IMG_CURRENT = new Image(
            TrenchCanvas.class.getResourceAsStream("/images/objects/hazard_ocean_current_spiral.png"));

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
        changeBoardGeometry();
        setBackground(gc);
        drawBoard(gc);
        drawActiveDrone(gc);
    }

    private void setBackground(GraphicsContext gc) {
        if (IMG_TRENCH_BG.getException() == null) {
            gc.drawImage(IMG_TRENCH_BG, 0, 0, getWidth(), getHeight());
        } else {
            gc.setFill(Color.DARKBLUE);
            gc.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void changeBoardGeometry() {
        rows = manager.getTrenchRows();
        cols = manager.getTrenchCols();

        if (rows == 0 || cols == 0) return;

        sizeCell = Math.min(getWidth() / cols, getHeight() / rows);

        offsetX = (getWidth() - sizeCell * cols) / 2;
        offsetY = (getHeight() - sizeCell * rows) / 2;
    }

    private double positionInX(int c) {
        return offsetX + c * sizeCell;
    }

    private double positionInY(int r) {
        return offsetY + r * sizeCell;
    }

    private void drawBoard(GraphicsContext gc) {
        drawEntities(gc);
        drawGrid(gc);
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.LIGHTBLUE);
        gc.setLineWidth(1);

        // Linhas horizontais
        for (int r = 0; r <= rows; r++) {
            double y = positionInY(r);
            gc.strokeLine(offsetX, y, offsetX + sizeCell * cols, y);
        }

        // Linhas verticais
        for (int c = 0; c <= cols; c++) {
            double x = positionInX(c);
            gc.strokeLine(x, offsetY, x, offsetY + sizeCell * rows);
        }
    }

    private void drawEntities(GraphicsContext gc) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                String entity = manager.getTrenchCellEntity(r, c);
                double x = positionInX(c);
                double y = positionInY(r);

                switch (entity) {
                    case "ROCK":
                        drawEntity(gc, IMG_ROCK, x, y);
                        break;
                    case "ANIMAL":
                        drawEntity(gc, animalCollection[rand.nextInt(6)], x, y);
                        break;
                    case "CURRENT":
                        drawEntity(gc, IMG_CURRENT, x, y);
                        break;
                }
            }
        }
    }

    private void drawEntity(GraphicsContext gc, Image image, double x, double y) {
        double margin = 2;
        if (image.getException() == null) {
            gc.drawImage(image, x + margin, y + margin, sizeCell - 2 * margin, sizeCell - 2 * margin);
        } else {
            // Fallback shapes if images fail to load
            gc.setFill(Color.GRAY);
            gc.fillRect(x + margin, y + margin, sizeCell - 2 * margin, sizeCell - 2 * margin);
        }
    }

    private void drawActiveDrone(GraphicsContext gc) {
        int droneRow = manager.getActiveDroneRow();
        int droneCol = manager.getActiveDroneCol();

        if (droneRow < 0 || droneCol < 0) return;

        double x = positionInX(droneCol);
        double y = positionInY(droneRow);
        double margin = sizeCell * 0.1;

        // Desenhar imagem do drone
        if (IMG_DRONE.getException() == null) {
            gc.drawImage(IMG_DRONE, x + margin, y + margin, sizeCell - 2 * margin, sizeCell - 2 * margin);
        } else {
            // Fallback: desenhar circle se imagem falhar
            gc.setFill(Color.GOLD);
            gc.fillOval(x + margin, y + margin, sizeCell - 2 * margin, sizeCell - 2 * margin);
            gc.setStroke(Color.YELLOW);
            gc.setLineWidth(2);
            gc.strokeOval(x + margin, y + margin, sizeCell - 2 * margin, sizeCell - 2 * margin);
        }
    }

}