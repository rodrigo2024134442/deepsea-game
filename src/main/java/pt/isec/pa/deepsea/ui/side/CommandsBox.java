package pt.isec.pa.deepsea.ui.side;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.state.GameState;

/**
 * Side panel with command buttons.
 * Buttons enable/disable themselves based on the current FSM state.
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 3)
 */
public class CommandsBox extends VBox {
    DeepSeaManager manager;

    Button btnW, btnA, btnS, btnD;
    Button btnDeploy1, btnDeploy2, btnDeploy3, btnShop;
    Button btnMining, btnRetrieve, btnAscend;

    public CommandsBox(DeepSeaManager manager) {
        this.manager = manager;
        setPadding(new Insets(10));
        setSpacing(8);
        setPrefWidth(200);
        setMaxWidth(200);
        setStyle("-fx-background-color: #16213e; -fx-border-color: #0f3460; -fx-border-width: 1;");

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        Label title = new Label("🎮 COMMANDS");
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        getChildren().addAll(title, styledSeparator());

        // --- Movement ---
        getChildren().add(sectionLabel("Movement"));

        btnW = dpadButton("W");
        btnA = dpadButton("A");
        btnS = dpadButton("S");
        btnD = dpadButton("D");

        GridPane dpad = new GridPane();
        dpad.setAlignment(Pos.CENTER);
        dpad.setHgap(3);
        dpad.setVgap(3);
        dpad.add(btnW, 1, 0);
        dpad.add(btnA, 0, 1);
        dpad.add(btnS, 1, 2);
        dpad.add(btnD, 2, 1);
        getChildren().add(dpad);

        getChildren().add(styledSeparator());

        // --- Surface ---
        getChildren().add(sectionLabel("Surface"));
        btnDeploy1 = actionButton("1 — Deploy Drone 1");
        btnDeploy2 = actionButton("2 — Deploy Drone 2");
        btnDeploy3 = actionButton("3 — Deploy Drone 3");
        btnShop    = actionButton("E — Enter Shop");
        getChildren().addAll(btnDeploy1, btnDeploy2, btnDeploy3, btnShop);

        getChildren().add(styledSeparator());

        // --- Bottom ---
        getChildren().add(sectionLabel("Bottom"));
        btnMining   = actionButton("M — Mining");
        btnRetrieve = actionButton("N — Retrieve Artifact");
        btnAscend   = actionButton("B — Ascend");
        getChildren().addAll(btnMining, btnRetrieve, btnAscend);
    }

    private Button dpadButton(String text) {
        Button btn = new Button(text);
        btn.setPrefSize(50, 35);
        btn.setStyle("""
                -fx-background-color: #0f3460;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-font-size: 14px;
                -fx-background-radius: 6;
                -fx-cursor: hand;
                """);
        return btn;
    }

    private Button actionButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("""
                -fx-background-color: #0f3460;
                -fx-text-fill: white;
                -fx-font-size: 11px;
                -fx-background-radius: 6;
                -fx-cursor: hand;
                -fx-alignment: CENTER_LEFT;
                """);
        return btn;
    }

    private Label sectionLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: #f5a623; -fx-font-weight: bold; -fx-font-size: 11px;");
        return lbl;
    }

    private Separator styledSeparator() {
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #0f3460;");
        return sep;
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME_STATE, e -> update());

        Button[] all = {btnW, btnA, btnS, btnD,
                btnDeploy1, btnDeploy2, btnDeploy3, btnShop,
                btnMining, btnRetrieve, btnAscend};
        for (Button b : all) {
            b.setOnAction(e -> { });
        }
    }

    private void update() {
        GameState state = manager.getState();

        boolean canMove = (state == GameState.SURFACE
                || state == GameState.TRENCH_DESCENT
                || state == GameState.TRENCH_ASCENT
                || state == GameState.BOTTOM);
        btnW.setDisable(!canMove);
        btnA.setDisable(!canMove);
        btnS.setDisable(!canMove);
        btnD.setDisable(!canMove);

        boolean canSurface = (state == GameState.SURFACE);
        btnDeploy1.setDisable(!canSurface);
        btnDeploy2.setDisable(!canSurface);
        btnDeploy3.setDisable(!canSurface);
        btnShop   .setDisable(!canSurface);

        boolean canBottom = (state == GameState.BOTTOM);
        btnMining  .setDisable(!canBottom);
        btnRetrieve.setDisable(!canBottom);
        btnAscend  .setDisable(!canBottom);

        // Estilo dos botões desativados
        Button[] all = {btnW, btnA, btnS, btnD,
                btnDeploy1, btnDeploy2, btnDeploy3, btnShop,
                btnMining, btnRetrieve, btnAscend};
        for (Button b : all) {
            if (b.isDisabled()) {
                b.setStyle(b.getStyle() + "-fx-opacity: 0.4;");
            } else {
                b.setStyle(b.getStyle().replace("-fx-opacity: 0.4;", ""));
            }
        }
    }
}