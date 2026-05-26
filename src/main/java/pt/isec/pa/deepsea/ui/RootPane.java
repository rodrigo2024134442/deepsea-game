package pt.isec.pa.deepsea.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.ui.canvas.BottomCanvas;
import pt.isec.pa.deepsea.ui.canvas.PuzzleCanvas;
import pt.isec.pa.deepsea.ui.canvas.SurfaceCanvas;
import pt.isec.pa.deepsea.ui.canvas.TrenchCanvas;
import pt.isec.pa.deepsea.ui.log.LogStage;
import pt.isec.pa.deepsea.ui.res.SoundManager;
import pt.isec.pa.deepsea.ui.side.CommandsBox;
import pt.isec.pa.deepsea.ui.side.DronesInfoBox;
import pt.isec.pa.deepsea.ui.side.ShipInfoBox;
import pt.isec.pa.deepsea.ui.shop.ShopPop;
import pt.isec.pa.deepsea.ui.res.VideoManager;

public class RootPane extends BorderPane {
    private final DeepSeaManager manager;

    // Canvases
    private SurfaceCanvas surfaceCanvas;
    private TrenchCanvas trenchCanvas;
    private BottomCanvas bottomCanvas;
    private PuzzleCanvas puzzleCanvas;
    private CommandsBox commandsBox;

    // Side panels
    private ShipInfoBox shipInfoBox;
    private DronesInfoBox dronesInfoBox;
    private ShopPop shopPop;

    private Pane areaPane;

    public RootPane(DeepSeaManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        // TOP - Menu
        DeepSeaMenuBar menuBar = new DeepSeaMenuBar(manager);
        setTop(menuBar);

        // CANVASES
        surfaceCanvas = new SurfaceCanvas(manager);
        trenchCanvas = new TrenchCanvas(manager);
        bottomCanvas = new BottomCanvas(manager);
        puzzleCanvas = new PuzzleCanvas(manager);

        StackPane stackPane = new StackPane(
                surfaceCanvas,
                trenchCanvas,
                puzzleCanvas,
                bottomCanvas
        );

        areaPane = new Pane(stackPane);
        setCenter(areaPane);

        // RIGHT PANEL
        shipInfoBox = new ShipInfoBox(manager);
        dronesInfoBox = new DronesInfoBox(manager);

        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.getChildren().addAll(shipInfoBox, dronesInfoBox);
        rightPanel.setStyle("-fx-background-color: #16213e;");

        ScrollPane scrollPane = new ScrollPane(rightPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(220);
        scrollPane.setMaxWidth(220);
        scrollPane.setStyle("-fx-background: #16213e; "
                + "-fx-background-color: #16213e; "
                + "-fx-border-color: #0f3460; "
                + "-fx-border-width: 1;");

        setRight(scrollPane);

        // LEFT
        commandsBox = new CommandsBox(manager);
        setLeft(commandsBox);

    }

    private void registerHandlers() {
        areaPane.widthProperty().addListener(obs -> updateCanvasSizes());
        areaPane.heightProperty().addListener(obs -> updateCanvasSizes());

        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE_DEPLOY, e -> {
            if (getScene() != null) {
                VideoManager.getInstance().play(VideoManager.DRONE_DEPLOY, getScene().getWindow(), null);
            }
        });

        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE_DESTROYED, e -> {
            if (getScene() != null) {
                VideoManager.getInstance().play(VideoManager.DRONE_DESTROYED, getScene().getWindow(), null);
            }
        });

        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME_LOST, e -> {
            if (getScene() != null) {
                VideoManager.getInstance().play(
                        VideoManager.GAME_LOST,
                        getScene().getWindow(),
                        Platform::exit
                );
            } else {
                Platform.exit();
            }
        });

        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME_WON, e -> {
            if (getScene() != null) {
                VideoManager.getInstance().play(
                        VideoManager.GAME_WON,
                        getScene().getWindow(),
                        Platform::exit
                );
            } else {
                Platform.exit();
            }
        });

        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE_DEPLOY, e -> {
            SoundManager.getInstance().play(SoundManager.DRONE_DEPLOY);
            if (getScene() != null) {
                VideoManager.getInstance().play(VideoManager.DRONE_DEPLOY, getScene().getWindow(), null);
            }
        });

        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE_DESTROYED, e -> {
            SoundManager.getInstance().play(SoundManager.DRONE_DESTROYED);
            if (getScene() != null) {
                VideoManager.getInstance().play(VideoManager.DRONE_DESTROYED, getScene().getWindow(), null);
            }
        });

        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME_LOST, e -> {
            SoundManager.getInstance().play(SoundManager.GAME_LOST);
            // ... resto igual
        });

        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME_WON, e -> {
            SoundManager.getInstance().play(SoundManager.GAME_WON);
            // ... resto igual
        });

        manager.addPropertyChangeListener(
                DeepSeaManager.PROP_GAME_STATE,
                e -> update()
        );

        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> manager.move("w");
                case A -> manager.move("a");
                case S -> manager.move("s");
                case D -> manager.move("d");

                case E -> manager.enterShop();

                //DRONE DESCENT
                case DIGIT1 -> manager.descend(1);
                case DIGIT2 -> manager.descend(2);
                case DIGIT3 -> manager.descend(3);

                //BOTTOM CONTROLS
                case M -> manager.mining();
                case N -> manager.retrieveArtifact();
                case B -> manager.ascend();

                default -> { }
            }
        });

        setFocusTraversable(true);
        requestFocus();
    }

    private void updateCanvasSizes() {
        double w = areaPane.getWidth();
        double h = areaPane.getHeight();

        surfaceCanvas.updateSize(w, h);
        trenchCanvas.updateSize(w, h);
        puzzleCanvas.updateSize(w, h);
        bottomCanvas.updateSize(w, h);
    }

    private void update() {
        GameState state = manager.getState();

        surfaceCanvas.setVisible(
                state == GameState.SURFACE ||
                        state == GameState.SHOP
        );

        trenchCanvas.setVisible(
                state == GameState.TRENCH_DESCENT ||
                        state == GameState.TRENCH_ASCENT
        );

        puzzleCanvas.setVisible(
                state == GameState.MINIGAME
        );

        bottomCanvas.setVisible(state == GameState.BOTTOM);

        if (state == GameState.SHOP) {
            if (getScene() != null && (shopPop == null || !shopPop.isShowing())) {
                shopPop = new ShopPop(manager, getScene().getWindow());
                shopPop.show();
            }
        } else {
            if (shopPop != null && shopPop.isShowing()) {
                shopPop.close();
            }
        }
    }
}