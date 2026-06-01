package pt.isec.pa.deepsea.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.deepsea.model.DeepSeaManager;

/**
 * Main JavaFX application class for Deep Sea Mining.
 *
 * <p>Lifecycle:</p>
 * <ul>
 *   <li>{@code init()} — creates the {@link DeepSeaManager} (off the FX thread)</li>
 *   <li>{@code start(Stage)} — builds the main window and shows it</li>
 * </ul>
 */
public class MainJFX extends Application {
    private DeepSeaManager manager;

    @Override
    public void init() throws Exception {
        super.init();
        manager = new DeepSeaManager();
    }

    @Override
    public void start(Stage stage) {
        RootPane root = new RootPane(manager);
        Scene scene = new Scene(root, 1500, 1000);

        stage.setScene(scene);
        stage.setTitle("Deep Sea Mining");
        stage.show();

        // Second window
        Stage secondStage = new Stage();
        RootPane root2 = new RootPane(manager);
        Scene scene2 = new Scene(root2, 1500, 1000);

        secondStage.setScene(scene2);
        secondStage.setTitle("Deep Sea Mining — Window 2");
        secondStage.show();
    }

}