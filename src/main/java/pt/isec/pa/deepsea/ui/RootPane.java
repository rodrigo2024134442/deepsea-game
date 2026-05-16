package pt.isec.pa.deepsea.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.ui.canvas.SurfaceCanvas;

/**
 * Top-level layout of the Deep Sea Mining UI.
 *
 * <p>Will eventually hold:</p>
 * <ul>
 *   <li>top: the menu bar</li>
 *   <li>center: the {@code StackPane} with the four canvases</li>
 *   <li>right: ship/drones info boxes and the shop launcher</li>
 * </ul>
 *
 * <p>For now it shows only a placeholder label so we can confirm the
 * window opens correctly.</p>
 */
public class RootPane extends BorderPane {
    DeepSeaManager manager;
    SurfaceCanvas surfaceCanvas;
    Pane areaPane;

    public RootPane(DeepSeaManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        surfaceCanvas = new SurfaceCanvas(manager);
        areaPane = new Pane(surfaceCanvas);
        setCenter(areaPane);
    }

    private void registerHandlers() {
        areaPane.widthProperty().addListener(obs ->
                surfaceCanvas.updateSize(areaPane.getWidth(), areaPane.getHeight()));
        areaPane.heightProperty().addListener(obs ->
                surfaceCanvas.updateSize(areaPane.getWidth(), areaPane.getHeight()));
    }

    private void update() {
        // todo
    }
}