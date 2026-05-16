package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        clearScreen(gc);
        // TODO (passo 16): desenhar a grelha
        // TODO (passo 18): desenhar o navio
    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        gc.setFill(Color.DARKBLUE);
        gc.setFont(Font.font(20));
        gc.fillText("SURFACE (placeholder)", 20, 30);
    }
}
