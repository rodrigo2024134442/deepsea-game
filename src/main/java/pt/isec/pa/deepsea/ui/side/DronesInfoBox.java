package pt.isec.pa.deepsea.ui.side;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.drone.DroneState;

/**
 * Side panel displaying the status of all drones.
 * Shows fuel, hull integrity, state and carried resources for each drone.
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 3)
 */
public class DronesInfoBox extends VBox {

    private final DeepSeaManager manager;

    public DronesInfoBox(DeepSeaManager manager) {
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
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONES, e -> update());
    }

    private void update() {
        getChildren().clear();

        Label title = new Label("DRONES");
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        getChildren().add(title);

        for (int id : manager.getDroneIds()) {
            getChildren().add(buildDroneBox(id));
        }
    }

    private VBox buildDroneBox(int id) {
        VBox box = new VBox(4);
        box.setPadding(new Insets(5));
        box.setStyle("-fx-background-color: #0f3460; -fx-border-radius: 5;");

        DroneState state   = manager.getDroneState(id);
        boolean isActive   = state != DroneState.ON_SHIP;

        // Título
        Label lblTitle = new Label("Drone #" + id + (isActive ? " ★" : ""));
        lblTitle.setStyle("-fx-text-fill: " + (isActive ? "#f5a623" : "white") +
                "; -fx-font-weight: bold;");

        // Estado
        Label lblState = new Label("State: " + state);
        lblState.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 11;");

        // Combustível
        int fuel    = manager.getDroneFuel(id);
        int maxFuel = manager.getDroneMaxFuel(id);
        Label lblFuel = new Label("Fuel: " + fuel + " / " + maxFuel);
        lblFuel.setStyle("-fx-text-fill: #e94560;");
        ProgressBar fuelBar = new ProgressBar((double) fuel / maxFuel);
        fuelBar.setPrefWidth(170);

        // Integridade
        int hull    = manager.getDroneHullIntegrity(id);
        int maxHull = manager.getDroneMaxHullIntegrity(id);
        Label lblHull = new Label("Hull: " + hull + " / " + maxHull);
        lblHull.setStyle("-fx-text-fill: #e94560;");
        ProgressBar hullBar = new ProgressBar((double) hull / maxHull);
        hullBar.setPrefWidth(170);

        // Recursos
        Label lblMinerals  = new Label("Minerals: " + manager.getDroneMinerals(id));
        Label lblArtifacts = new Label("Artifacts: " + manager.getDroneArtifactsCount(id));
        lblMinerals .setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 11;");
        lblArtifacts.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 11;");

        box.getChildren().addAll(
                lblTitle, lblState,
                lblFuel, fuelBar,
                lblHull, hullBar,
                lblMinerals, lblArtifacts
        );

        return box;
    }
}