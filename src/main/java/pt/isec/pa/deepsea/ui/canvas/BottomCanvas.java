package pt.isec.pa.deepsea.ui.canvas;

import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pt.isec.pa.deepsea.model.DeepSeaManager;

public class BottomCanvas extends Canvas {
    DeepSeaManager manager;

    private int cols,rows;
    private double sizeCell, offsetX, offsetY;

    private static final Image IMG_DRONE= new Image(
            BottomCanvas.class.getResourceAsStream("/images/vehicles/mining_drone_heavy_rov.png"));

    private static final Image IMG_BACKGROUND= new Image(
            BottomCanvas.class.getResourceAsStream("/images/backgrounds/sea_floor_grid_background.png"));

    private static final Image IMG_CLOUD= new Image(
            BottomCanvas.class.getResourceAsStream("/images/backgrounds/DarkUnderwaterCloud.png"));

    private static final Image[] mineralColection = {
            new Image(BottomCanvas.class.getResourceAsStream("/images/objects/mineral_gold_ore_cluster.png")),
            new Image(BottomCanvas.class.getResourceAsStream("/images/objects/mineral_purple_crystal_cluster.png")),
    };

    private static final Image[] artifactColection = {
            new Image(BottomCanvas.class.getResourceAsStream("/images/objects/artifact_ancient_stone_mask.png")),
            new Image(BottomCanvas.class.getResourceAsStream("/images/objects/artifact_sunken_treasure_chest.png")),
    };

    private static final Image[] monsterColection = {
            new Image(BottomCanvas.class.getResourceAsStream("/images/creatures/monster6.png")),
            new Image(BottomCanvas.class.getResourceAsStream("/images/creatures/monster7.png")),
    };

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
        gc.drawImage(IMG_BACKGROUND, 0, 0, getWidth(), getHeight());
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
                    gc.drawImage(IMG_CLOUD, x, y, sizeCell, sizeCell);
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

    private void drawDrone(GraphicsContext gc){
        int row = manager.getActiveDroneRow();
        int col = manager.getActiveDroneCol();

        double x = positionInX(col);
        double y = positionInY(row);
        gc.drawImage(IMG_DRONE,x,y,sizeCell,sizeCell);
    }

    private void drawEntity(GraphicsContext gc, int r, int c, double x, double y) {
        String entity = manager.getBottomCellEntity(r, c);
        Random rnd = new Random();
        int randomNum = rnd.nextInt(2);
        randomNum = 1;
        switch (entity) {
            case "MINERAL"  -> gc.drawImage(mineralColection[randomNum],  x, y, sizeCell, sizeCell);
            case "ARTIFACT" -> gc.drawImage(artifactColection[randomNum], x, y, sizeCell, sizeCell);
            case "MONSTER" -> gc.drawImage(monsterColection[randomNum], x, y, sizeCell, sizeCell);
            default -> { }
        }
    }

}
