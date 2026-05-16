package pt.isec.pa.deepsea.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.ui.canvas.PuzzleCanvas;
import pt.isec.pa.deepsea.ui.canvas.SurfaceCanvas;
import pt.isec.pa.deepsea.ui.log.LogStage;
import pt.isec.pa.deepsea.ui.side.DronesInfoBox;
import pt.isec.pa.deepsea.ui.side.ShipInfoBox;

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
    PuzzleCanvas puzzleCanvas;
    ShipInfoBox shipInfoBox;
    DronesInfoBox dronesInfoBox;
    Pane areaPane;

    public RootPane(DeepSeaManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        surfaceCanvas = new SurfaceCanvas(manager);
        puzzleCanvas = new PuzzleCanvas(manager);
        shipInfoBox = new ShipInfoBox(manager);
        dronesInfoBox = new DronesInfoBox(manager);

        areaPane = new Pane();
        areaPane.getChildren().addAll(surfaceCanvas, puzzleCanvas);
        setCenter(areaPane);

        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.getChildren().addAll(shipInfoBox, dronesInfoBox);

        ScrollPane scrollPane = new ScrollPane(rightPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(220);
        scrollPane.setMaxWidth(220);
        scrollPane.setStyle("-fx-background-color: transparent;");
        setRight(scrollPane);

        // MUDA PA - PARA TESTAR OS LOGs ENQUANTO O MENU NAO ESTA IMPLEMENTADO
        Button btnLog = new Button("Show Logs");
        btnLog.setOnAction(e -> {
            LogStage logStage = new LogStage(manager, getScene().getWindow());
            logStage.show();
        });
        setBottom(btnLog);

        /*
        * // Declarar no RootPane ou no MenuBar
            LogStage logStage = new LogStage(manager, getScene().getWindow());

            // Show/Hide
            if (logStage.isShowing()) {
                logStage.hide();
            } else {
                logStage.show();
            }
        * */
    }

    private void registerHandlers() {
        areaPane.widthProperty().addListener(obs -> {
            surfaceCanvas.updateSize(areaPane.getWidth(), areaPane.getHeight());
            puzzleCanvas.updateSize(areaPane.getWidth(), areaPane.getHeight());
        });
        areaPane.heightProperty().addListener(obs -> {
            surfaceCanvas.updateSize(areaPane.getWidth(), areaPane.getHeight());
            puzzleCanvas.updateSize(areaPane.getWidth(), areaPane.getHeight());
        });

        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME_STATE, e -> update());
    }

    private void update() {
        var state = manager.getState();
        surfaceCanvas.setVisible(state == GameState.SURFACE || state == GameState.SHOP);
        puzzleCanvas.setVisible(state == GameState.MINIGAME);
    }
}