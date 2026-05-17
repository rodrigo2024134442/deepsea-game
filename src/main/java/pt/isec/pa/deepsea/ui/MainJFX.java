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

        scene.setOnKeyPressed(e -> {
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

        stage.setScene(scene);
        stage.setTitle("Deep Sea Mining");
        stage.show();
    }

}