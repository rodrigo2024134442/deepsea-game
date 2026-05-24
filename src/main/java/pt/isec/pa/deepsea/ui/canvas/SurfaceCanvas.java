package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.ui.res.ImageManager;

import java.util.Random;

/**
 * Canvas that shows the sea surface (ship grid).
 *
 * <p>Follows the MVC@PA pattern from the DrawingApp lecture:
 * the constructor registers handlers and triggers the first draw,
 * and subsequent redraws are triggered by PropertyChange events
 * coming from the {@link DeepSeaManager}.</p>
 */

public class SurfaceCanvas extends Canvas {
    DeepSeaManager manager;
    private int cols, rows;
    private double sizeCell, offsetX, offsetY;
    private final Random rnd = new Random();

    public SurfaceCanvas(DeepSeaManager manager) {
        super(500, 500);
        this.manager = manager;
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GRID, e -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_SHIP, e -> update());
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
        drawShip(gc);
    }

    private void setBackground(GraphicsContext gc) {
        gc.drawImage(ImageManager.getInstance().get(ImageManager.BG_SURFACE), 0, 0, getWidth(), getHeight());
    }

    private void changeBoardGeometry() {
        rows = manager.getSurfaceRows();
        cols = manager.getSurfaceCols();
        if (rows == 0 || cols == 0) return;
        sizeCell = Math.min(getWidth() / cols, getHeight() / rows);
        offsetX  = (getWidth()  - sizeCell * cols) / 2;
        offsetY  = (getHeight() - sizeCell * rows) / 2;
    }

    private double positionInX(int c) { return offsetX + c * sizeCell; }
    private double positionInY(int r) { return offsetY + r * sizeCell; }

    private void drawBoard(GraphicsContext gc){
        if (rows == 0 || cols == 0) return;

        ImageManager im = ImageManager.getInstance();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = positionInX(c);
                double y = positionInY(r);

                if (manager.surfaceCellHasArtifacts(r, c)) {
                    gc.drawImage(im.get(ImageManager.ARTIFACT_MARKER), x, y, sizeCell, sizeCell);
                } else {
                    int roll = rnd.nextInt(6);
                    if (roll == 0) {
                        gc.drawImage(im.get(ImageManager.WAVE_1), x, y, sizeCell, sizeCell);
                    } else if (roll == 1) {
                        gc.drawImage(im.get(ImageManager.WAVE_2), x, y, sizeCell, sizeCell);
                    }
                }
            }
        }

        gc.setStroke(Color.DARKGREY);
        gc.setLineWidth(1);
        for (int r = 0; r <= rows; r++) {
            double y = positionInY(r);
            gc.strokeLine(offsetX, y, offsetX + sizeCell * cols, y);
        }
        for (int c = 0; c <= cols; c++) {
            double x = positionInX(c);
            gc.strokeLine(x, offsetY, x, offsetY + sizeCell * rows);
        }
    }

    private void drawShip(GraphicsContext gc) {
        int row = manager.getShipRow();
        int col = manager.getShipCol();
        gc.drawImage(ImageManager.getInstance().get(ImageManager.SHIP), positionInX(col), positionInY(row), sizeCell, sizeCell);
    }

}
