package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.ui.res.ImageManager;

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

    public TrenchCanvas(DeepSeaManager manager) {
        super(500, 500);                // initial size, will be resized by RootPane
        this.manager = manager;
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GRID, e -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONES, e -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME_STATE, e -> update());
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
        if (rows == 0 || cols == 0) return;
        drawBoard(gc);
        drawActiveDrone(gc);
    }

    private void setBackground(GraphicsContext gc) {
        Image bg = ImageManager.getInstance().get(ImageManager.TRENCH_BG);
        gc.drawImage(bg, 0, 0, getWidth(), getHeight());
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
        ImageManager im = ImageManager.getInstance();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                String entity = manager.getTrenchCellEntity(r, c);
                double x = positionInX(c);
                double y = positionInY(r);

                switch (entity) {
                    case "ROCK"    -> drawEntity(gc, im.get(ImageManager.ROCK_TRENCH),     x, y);
                    case "ANIMAL"  -> drawEntity(gc, im.get(ImageManager.ANIMAL_1), x, y);
                    case "CURRENT" -> drawEntity(gc, im.get(ImageManager.CURRENT_TRENCH),  x, y);
                }
            }
        }
    }

    private void drawEntity(GraphicsContext gc, Image image, double x, double y) {
        double margin = 2;
        if (image != null) {
            gc.drawImage(image, x + margin, y + margin, sizeCell - 2 * margin, sizeCell - 2 * margin);
        } else {
            gc.setFill(Color.GRAY);
            gc.fillRect(x + margin, y + margin, sizeCell - 2 * margin, sizeCell - 2 * margin);
        }
    }

    /*private void drawActiveDrone(GraphicsContext gc) {
        int droneRow = manager.getActiveDroneRow();
        int droneCol = manager.getActiveDroneCol();

        if (droneRow < 0 || droneCol < 0) return;

        double x = positionInX(droneCol);
        double y = positionInY(droneRow);
        double margin = sizeCell * 0.1;

        Image drone = ImageManager.getInstance().get(ImageManager.DRONE);
        gc.drawImage(drone, x + margin, y + margin, sizeCell - 2 * margin, sizeCell - 2 * margin);
    }*/

    private void drawActiveDrone(GraphicsContext gc) {
        GameState state = manager.getState();
        if (state != GameState.TRENCH_DESCENT && state != GameState.TRENCH_ASCENT) return;

        int droneRow = manager.getActiveDroneRow();
        int droneCol = manager.getActiveDroneCol();

        if (droneRow < 0 || droneCol < 0) return;

        double x = positionInX(droneCol);
        double y = positionInY(droneRow);
        double margin = sizeCell * 0.1;

        Image drone = ImageManager.getInstance().get(ImageManager.DRONE);
        gc.drawImage(drone, x + margin, y + margin, sizeCell - 2 * margin, sizeCell - 2 * margin);
    }

}