package pt.isec.pa.deepsea.ui.canvas;

import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.ui.res.ImageManager;

/**
 * Canvas renderer for the sea floor grid and active drone position.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Render revealed cells and hide unrevealed cells with clouds.</li>
 *   <li>Draw minerals, artifacts, and monsters on revealed cells.</li>
 *   <li>Draw the active drone only in the bottom state.</li>
 *   <li>Redraw on grid/drone changes signaled by {@link DeepSeaManager}.</li>
 * </ul>
 */
public class BottomCanvas extends Canvas {
    DeepSeaManager manager;
    private int cols,rows;
    private double sizeCell, offsetX, offsetY;

    public BottomCanvas (DeepSeaManager manager){
        super(500,500);
        this.manager = manager;
        registerHandlers();
        update();
    }

    private void registerHandlers(){
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GRID, e -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONES, e -> update());
    }

    public void updateSize(double newWidth, double newHeight) {
        setWidth(newWidth);
        setHeight(newHeight);
        update();
    }

    private void update(){
        GraphicsContext gc = getGraphicsContext2D();
        changeBoardGeometry();
        setBackground(gc);
        drawBoard(gc);
        drawDrone(gc);
    }

    private void setBackground(GraphicsContext gc) {
        Image imgBgSeaFloor = ImageManager.getInstance().get(ImageManager.BG_SEAFLOOR);
        gc.drawImage(imgBgSeaFloor, 0, 0, getWidth(), getHeight());
    }

    private void changeBoardGeometry(){
        rows = manager.getBottomRows();
        cols = manager.getBottomCols();

        sizeCell = Math.min(getWidth()/ cols, getHeight()/rows);

        offsetX = (getWidth() - sizeCell * cols) / 2;
        offsetY = (getHeight() - sizeCell * rows) / 2;
    }

    private double positionInX(int c) { return offsetX + c * sizeCell; }
    private double positionInY(int r) { return offsetY + r * sizeCell; }

    private void drawBoard(GraphicsContext gc){
        gc.setFill(Color.STEELBLUE);
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                double x = positionInX(c);
                double y = positionInY(r);

                if (manager.isBottomCellRevealed(r, c)) {
                    drawEntity(gc, r, c, x, y);
                } else {
                    Image imgBgCloud = ImageManager.getInstance().get(ImageManager.BG_CLOUD);
                    gc.drawImage(imgBgCloud, x, y, sizeCell, sizeCell);
                }
            }
        }

        gc.setStroke(Color.INDIGO);
        gc.setLineWidth(2);

        for (int r = 0; r <= rows; r++){
            double y = positionInY(r);
            gc.strokeLine(offsetX,y,offsetX + sizeCell * cols, y);
        }

        for (int c = 0; c <= cols; c++){
            double x = positionInX(c);
            gc.strokeLine(x,offsetY,x,offsetY + sizeCell * rows);
        }

    }

    /*private void drawDrone(GraphicsContext gc){
        int row = manager.getActiveDroneRow();
        int col = manager.getActiveDroneCol();

        double x = positionInX(col);
        double y = positionInY(row);
        Image imgDrone = ImageManager.getInstance().get(ImageManager.DRONE);
        gc.drawImage(imgDrone, x, y, sizeCell, sizeCell);
    }*/

    private void drawDrone(GraphicsContext gc) {
        GameState state = manager.getState();
        if (state != GameState.BOTTOM) return;

        int row = manager.getActiveDroneRow();
        int col = manager.getActiveDroneCol();
        if (row == -1 || col == -1) return;

        gc.drawImage(ImageManager.getInstance().get(ImageManager.DRONE),
                positionInX(col), positionInY(row), sizeCell, sizeCell);
    }

    private void drawEntity(GraphicsContext gc, int r, int c, double x, double y) {
        String entity = manager.getBottomCellEntity(r, c);
        ImageManager im = ImageManager.getInstance();

        switch (entity) {
            case "MINERAL"  -> gc.drawImage(im.get(ImageManager.MINERAL_2),  x, y, sizeCell, sizeCell);
            case "ARTIFACT" -> gc.drawImage(im.get(ImageManager.ARTIFACT_2), x, y, sizeCell, sizeCell);
            case "MONSTER"  -> gc.drawImage(im.get(ImageManager.MONSTER_2),  x, y, sizeCell, sizeCell);
            default -> { }
        }
    }

}
