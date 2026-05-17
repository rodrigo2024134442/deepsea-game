package pt.isec.pa.deepsea.ui.side;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;

/**
 * Side panel displaying the ship's current status.
 * Shows fuel level, minerals and collected artifacts.
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 3)
 */
public class ShipInfoBox extends VBox {

    private final DeepSeaManager manager;

    private Label lblFuel;
    private Label lblMinerals;
    private Label lblArtifacts;
    private ProgressBar fuelBar;

    public ShipInfoBox(DeepSeaManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setPadding(new Insets(10));
        setSpacing(8);
        setPrefWidth(200);
        setStyle("-fx-background-color: #16213e; -fx-border-color: #0f3460; -fx-border-width: 1;");

        Label title = new Label("⚓ SHIP");
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");

        lblFuel      = new Label();
        lblMinerals  = new Label();
        lblArtifacts = new Label();
        fuelBar      = new ProgressBar(1.0);

        lblFuel     .setStyle("-fx-text-fill: #e94560;");
        lblMinerals .setStyle("-fx-text-fill: #e94560;");
        lblArtifacts.setStyle("-fx-text-fill: #e94560;");
        fuelBar.setPrefWidth(180);

        getChildren().addAll(title, lblFuel, fuelBar, lblMinerals, lblArtifacts);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_SHIP, e -> update());
    }

    private void update() {
        int fuel    = manager.getShipFuel();
        int maxFuel = manager.getShipMaxFuel();

        lblFuel     .setText("Fuel: " + fuel + " / " + maxFuel);
        lblMinerals .setText("Minerals: " + manager.getShipMinerals());
        lblArtifacts.setText("Artifacts: " + manager.getCollectedArtifactsCount());
        fuelBar.setProgress((double) fuel / maxFuel);
    }
}