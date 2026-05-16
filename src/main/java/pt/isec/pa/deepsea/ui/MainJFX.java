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
        Scene scene = new Scene(root, 1000, 700); //MUDA_PA - possivel mudança para configfile
        stage.setScene(scene);
        stage.setTitle("Deep Sea Game");
        stage.show();
    }
}