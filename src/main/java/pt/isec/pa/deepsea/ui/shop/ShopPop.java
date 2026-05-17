package pt.isec.pa.deepsea.ui.shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.isec.pa.deepsea.model.DeepSeaManager;

/**
 * Modal Stage that shows the shop interface, opened when the FSM is in the SHOP state.
 *
 * <p>Built using the same MVC@PA pattern as the rest of the UI: constructor calls
 * createViews / registerHandlers / update. Subscribes to PROP_SHIP and PROP_DRONES
 * to refresh the displayed info. Closing the window (X button or Close button)
 * fires {@code manager.leaveShop()} so the FSM goes back to SURFACE.</p>
 */
public class ShopPop extends Stage {
    DeepSeaManager manager;
    VBox content;
    VBox shipInfoBox;
    VBox dronesArea;
    Button btnClose;

    private static final Image IMG_SHOP = new Image(
            ShopPop.class.getResourceAsStream("/images/backgrounds/shop.png"));

    public ShopPop(DeepSeaManager manager, Window owner) {
        this.manager = manager;
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setAlwaysOnTop(true);
        setTitle("Shop");
        setResizable(false);

        createViews();
        registerHandlers();
        update();

        setOnCloseRequest(e -> manager.leaveShop());
    }

    private void createViews() {
        Label lblTitle = new Label("SHOP");
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        shipInfoBox = new VBox(2);
        shipInfoBox.setAlignment(Pos.CENTER_LEFT);
        shipInfoBox.setStyle("-fx-background-color: transparent;");

        dronesArea = new VBox(8);
        dronesArea.setAlignment(Pos.CENTER_LEFT);
        dronesArea.setStyle("-fx-background-color: transparent;");

        btnClose = new Button("Close");
        btnClose.setPrefWidth(120);

        content = new VBox(15,
                lblTitle,
                shipInfoBox,
                new Separator(),
                dronesArea,
                new Separator(),
                btnClose);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: transparent;");

        ImageView bgView = new ImageView(IMG_SHOP);
        bgView.setFitWidth(460);
        bgView.setFitHeight(380);

        StackPane root = new StackPane(bgView, content);

        setScene(new Scene(root, 460, 380));
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_SHIP,   e -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONES, e -> update());

        btnClose.setOnAction(e -> close());
    }

    private void update() {
        // --- SHIP info ---
        shipInfoBox.getChildren().clear();
        shipInfoBox.getChildren().addAll(
                new Label("--- SHIP ---"),
                new Label("Fuel: "     + manager.getShipFuel() + " / " + manager.getShipMaxFuel()),
                new Label("Minerals: " + manager.getShipMinerals())
        );

        // --- DRONES area ---
        dronesArea.getChildren().clear();
        dronesArea.getChildren().add(new Label("--- DRONES ---"));

        for (int id : manager.getDroneIds()) {
            int fuel    = manager.getDroneFuel(id);
            int maxFuel = manager.getDroneMaxFuel(id);
            int hull    = manager.getDroneHullIntegrity(id);
            int maxHull = manager.getDroneMaxHullIntegrity(id);

            if (hull == 0) continue;

            Label info = new Label(String.format(
                    "Drone %d | Fuel: %d/%d | Hull: %d/%d",
                    id, fuel, maxFuel, hull, maxHull));
            info.setPrefWidth(240);

            Button btnRefuel = new Button("Refuel");
            Button btnRepair = new Button("Repair");
            btnRefuel.setPrefWidth(70);
            btnRepair.setPrefWidth(70);

            btnRefuel.setOnAction(e -> manager.refuel(id));
            btnRepair.setOnAction(e -> manager.repair(id));

            btnRefuel.setDisable(fuel >= maxFuel);
            btnRepair.setDisable(hull >= maxHull);

            HBox row = new HBox(8, info, btnRefuel, btnRepair);
            row.setAlignment(Pos.CENTER_LEFT);
            dronesArea.getChildren().add(row);
        }

        if (dronesArea.getChildren().size() == 1) {
            dronesArea.getChildren().add(new Label("(no active drones)"));
        }
    }
}