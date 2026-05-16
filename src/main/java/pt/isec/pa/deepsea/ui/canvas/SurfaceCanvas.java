package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pt.isec.pa.deepsea.model.DeepSeaManager;

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

    private int cols,rows;
    private double sizeCell, offsetX,offsetY;
    private static final Image IMG_SHIP = new Image(
            SurfaceCanvas.class.getResourceAsStream("/images/vehicles/research_ship_heavy_support.png"));


    public SurfaceCanvas(DeepSeaManager manager) {
        super(500, 500);                // initial size, will be resized by RootPane
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
        clearScreen(gc);
        drawBoard(gc);
        drawShip(gc);

    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    private void changeBoardGeometry (){
        rows = manager.getSurfaceRows();
        cols = manager.getSurfaceCols();
        sizeCell = Math.min(getWidth() / cols, getHeight() / rows);
        offsetX = (getWidth()  - sizeCell * cols) / 2;
        offsetY = (getHeight()  - sizeCell * rows) / 2;
    }

    private double colToX(int c) { return offsetX + c * sizeCell; }
    private double rowToY(int r) { return offsetY + r * sizeCell; }

    private void drawBoard(GraphicsContext gc){
        gc.setFill(Color.CORNFLOWERBLUE);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                gc.fillRect(colToX(c), rowToY(r), sizeCell, sizeCell);
            }
        }

        gc.setStroke(Color.GOLD);
        gc.setLineWidth(1);
        for (int r = 0; r <= rows; r++) {
            double y = rowToY(r);
            gc.strokeLine(offsetX, y, offsetX + sizeCell * cols, y);
        }
        for (int c = 0; c <= cols; c++) {
            double x = colToX(c);
            gc.strokeLine(x, offsetY, x, offsetY + sizeCell * rows);
        }
    }

    private void drawShip(GraphicsContext gc) {
        int row = manager.getShipRow();
        int col = manager.getShipCol();
        double x = colToX(col);
        double y = rowToY(row);

        gc.setFill(Color.RED);                                       // teste
        gc.fillRect(x, y, sizeCell, sizeCell);
        gc.drawImage(IMG_SHIP, x, y, sizeCell, sizeCell);

        gc.setStroke(Color.GOLD);
        gc.setLineWidth(3);
        gc.strokeRect(x, y, sizeCell, sizeCell);
    }

}
