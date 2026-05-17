package pt.isec.pa.deepsea.ui.shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
 * @author João Carvalho - 2024126292
 * @version v2.0.0 (Stage 3)
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
        setTitle("⚓ Workshop");
        setResizable(false);

        createViews();
        registerHandlers();
        update();

        setOnCloseRequest(e -> manager.leaveShop());
    }

    private void createViews() {
        // Título
        Label lblTitle = new Label("⚓ WORKSHOP");
        lblTitle.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 22px;
                -fx-font-weight: bold;
                -fx-effect: dropshadow(gaussian, black, 4, 0.5, 0, 0);
                """);

        shipInfoBox = new VBox(6);
        shipInfoBox.setAlignment(Pos.CENTER_LEFT);
        shipInfoBox.setPadding(new Insets(10));
        shipInfoBox.setStyle("""
                -fx-background-color: rgba(0,0,0,0.5);
                -fx-background-radius: 8;
                """);

        dronesArea = new VBox(10);
        dronesArea.setAlignment(Pos.CENTER_LEFT);
        dronesArea.setPadding(new Insets(10));
        dronesArea.setStyle("""
            -fx-background-color: rgba(0,0,0,0.5);
            -fx-background-radius: 8;
            """);

        // ScrollPane para os drones
        ScrollPane scrollDrones = new ScrollPane(dronesArea);
        scrollDrones.setFitToWidth(true);
        scrollDrones.setPrefHeight(400);
        scrollDrones.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        btnClose = new Button("✖ Close");
        btnClose.setPrefWidth(140);
        btnClose.setStyle("""
            -fx-background-color: #e94560;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 13px;
            -fx-background-radius: 8;
            -fx-cursor: hand;
            """);

        content = new VBox(15,
                lblTitle,
                shipInfoBox,
                new Separator(),
                scrollDrones,  // ← ScrollPane em vez de dronesArea diretamente
                btnClose);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(25));
        content.setStyle("-fx-background-color: transparent;");

        ImageView bgView = new ImageView(IMG_SHOP);
        bgView.setFitWidth(560);
        bgView.setFitHeight(600);

        StackPane root = new StackPane(bgView, content);
        setScene(new Scene(root, 560, 600));

    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_SHIP,   e -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONES, e -> update());
        btnClose.setOnAction(e -> {
            manager.leaveShop();
            close();
        });
    }

    private void update() {
        // --- SHIP info ---
        shipInfoBox.getChildren().clear();

        Label shipTitle = new Label("🚢  SHIP STATUS");
        shipTitle.setStyle("-fx-text-fill: #f5a623; -fx-font-weight: bold; -fx-font-size: 13px;");

        int shipFuel    = manager.getShipFuel();
        int shipMaxFuel = manager.getShipMaxFuel();

        Label lblFuel = new Label("Fuel: " + shipFuel + " / " + shipMaxFuel);
        lblFuel.setStyle("-fx-text-fill: white;");

        ProgressBar shipFuelBar = new ProgressBar((double) shipFuel / shipMaxFuel);
        shipFuelBar.setPrefWidth(440);
        shipFuelBar.setPrefHeight(12);
        shipFuelBar.setStyle("-fx-accent: #27ae60; -fx-background-color: rgba(0,0,0,0.5);");

        Label lblMinerals = new Label("💎  Minerals: " + manager.getShipMinerals());
        lblMinerals.setStyle("-fx-text-fill: white;");

        shipInfoBox.getChildren().addAll(shipTitle, lblFuel, shipFuelBar, lblMinerals);

        // --- DRONES ---
        dronesArea.getChildren().clear();

        Label dronesTitle = new Label("🤖  DRONES");
        dronesTitle.setStyle("-fx-text-fill: #f5a623; -fx-font-weight: bold; -fx-font-size: 13px;");
        dronesArea.getChildren().add(dronesTitle);

        boolean anyDrone = false;
        for (int id : manager.getDroneIds()) {
            int fuel    = manager.getDroneFuel(id);
            int maxFuel = manager.getDroneMaxFuel(id);
            int hull    = manager.getDroneHullIntegrity(id);
            int maxHull = manager.getDroneMaxHullIntegrity(id);

            if (hull == 0) continue;
            anyDrone = true;

            dronesArea.getChildren().add(buildDroneRow(id, fuel, maxFuel, hull, maxHull));
        }

        if (!anyDrone) {
            Label none = new Label("(no active drones)");
            none.setStyle("-fx-text-fill: #aaaaaa;");
            dronesArea.getChildren().add(none);
        }
    }

    private VBox buildDroneRow(int id, int fuel, int maxFuel, int hull, int maxHull) {
        VBox box = new VBox(6);
        box.setPadding(new Insets(8));
        box.setStyle("""
                -fx-background-color: rgba(255,255,255,0.08);
                -fx-background-radius: 6;
                """);

        Label lblDrone = new Label("Drone #" + id);
        lblDrone.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // Fuel
        Label lblFuel = new Label("Fuel: " + fuel + " / " + maxFuel);
        lblFuel.setStyle("-fx-text-fill: #e94560; -fx-font-size: 11px;");
        ProgressBar fuelBar = new ProgressBar((double) fuel / maxFuel);
        fuelBar.setPrefWidth(360);
        fuelBar.setPrefHeight(12);
        fuelBar.setStyle("-fx-accent: #27ae60; -fx-background-color: rgba(0,0,0,0.5);");

        // Hull
        Label lblHull = new Label("Hull: " + hull + " / " + maxHull);
        lblHull.setStyle("-fx-text-fill: #e94560; -fx-font-size: 11px;");
        ProgressBar hullBar = new ProgressBar((double) hull / maxHull);
        hullBar.setPrefWidth(360);
        hullBar.setStyle(hullBarStyle(hull, maxHull));

        // Botões
        Button btnRefuel = new Button("⛽ Refuel");
        Button btnRepair = new Button("🔧 Repair");

        styleButton(btnRefuel, "#0f3460");
        styleButton(btnRepair, "#0f3460");

        btnRefuel.setDisable(fuel >= maxFuel);
        btnRepair.setDisable(hull >= maxHull);

        btnRefuel.setOnAction(e -> manager.refuel(id));
        btnRepair.setOnAction(e -> manager.repair(id));

        HBox buttons = new HBox(10, btnRefuel, btnRepair);
        buttons.setAlignment(Pos.CENTER_LEFT);

        box.getChildren().addAll(lblDrone, lblFuel, fuelBar, lblHull, hullBar, buttons);
        return box;
    }

    private void styleButton(Button btn, String color) {
        btn.setPrefWidth(110);
        btn.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 6;
                -fx-cursor: hand;
                """, color));
    }

    private String fuelBarStyle(int current, int max) {
        double ratio = (double) current / max;
        String color = ratio > 0.5 ? "#27ae60" : ratio > 0.25 ? "#f39c12" : "#e74c3c";
        return "-fx-accent: " + color + ";";
    }

    private String hullBarStyle(int current, int max) {
        double ratio = (double) current / max;
        String color = ratio > 0.6 ? "#3498db" : ratio > 0.3 ? "#f39c12" : "#e74c3c";
        return "-fx-accent: " + color + ";";
    }
}