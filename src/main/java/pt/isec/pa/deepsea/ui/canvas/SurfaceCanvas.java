package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pt.isec.pa.deepsea.model.DeepSeaManager;

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

    private int cols,rows;
    private double sizeCell, offsetX,offsetY;

    private static final Image IMG_SHIP = new Image(
            SurfaceCanvas.class.getResourceAsStream("/images/vehicles/research_ship_heavy_support.png"));

    private static final Image [] waveCollection = {new Image(
            SurfaceCanvas.class.getResourceAsStream("/images/backgrounds/wave1.png")), new Image(
            SurfaceCanvas.class.getResourceAsStream("/images/backgrounds/wave2.png")), new Image(
            SurfaceCanvas.class.getResourceAsStream("/images/backgrounds/wave1.png"))
};

    private static final Image [] artifactMarkerCollection = {new Image(
            SurfaceCanvas.class.getResourceAsStream("/images/backgrounds/artifactMarker.png")), new Image(
            SurfaceCanvas.class.getResourceAsStream("/images/backgrounds/artifactMarker1.png"))
    };

    private static final Image IMG_SURFACE = new Image(
            SurfaceCanvas.class.getResourceAsStream("/images/backgrounds/SeaSurface.png"));

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
        gc.drawImage(IMG_SURFACE,0,0,getWidth(),getHeight());
    }

    private void changeBoardGeometry (){
        rows = manager.getSurfaceRows();
        cols = manager.getSurfaceCols();

        sizeCell = Math.min(getWidth() / cols, getHeight() / rows);

        offsetX = (getWidth()  - sizeCell * cols) / 2;
        offsetY = (getHeight()  - sizeCell * rows) / 2;
    }

    private double positionInX(int c) { return offsetX + c * sizeCell; }
    private double positionInY(int r) { return offsetY + r * sizeCell; }

    private void drawBoard(GraphicsContext gc){
        gc.drawImage(IMG_SURFACE,0,0,getWidth(),getHeight());
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = positionInX(c);
                double y = positionInY(r);
                if(manager.surfaceCellHasArtifacts(r,c)){
                    drawArtifactMarker(gc,r,c,x,y);
                }else
                    drawWaves(gc,r,c,x,y);
            }
        }

        gc.setStroke(Color.GOLD);
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
        double x = positionInX(col);
        double y = positionInY(row);

        gc.drawImage(IMG_SHIP, x, y, sizeCell, sizeCell);
    }

    private void drawWaves(GraphicsContext gc, int r, int c, double x, double y){
        Random rnd = new Random();
        int randomNum = rnd.nextInt(6);
        switch (randomNum) {
            case 0  -> gc.drawImage(waveCollection[randomNum],  x, y, sizeCell, sizeCell);
            case 1 -> gc.drawImage(waveCollection[randomNum], x, y, sizeCell, sizeCell);
            case 2 -> gc.drawImage(waveCollection[randomNum], x, y, sizeCell, sizeCell);
            default -> { }
        }
    }

    private void drawArtifactMarker(GraphicsContext gc, int r, int c, double x, double y){
        Random rnd = new Random();
        int randomNum = rnd.nextInt(1);
        switch (randomNum) {
            case 0  -> gc.drawImage(artifactMarkerCollection[randomNum],  x, y, sizeCell, sizeCell);
            case 1 -> gc.drawImage(artifactMarkerCollection[randomNum], x, y, sizeCell, sizeCell);
            default -> { }
        }
    }

}
