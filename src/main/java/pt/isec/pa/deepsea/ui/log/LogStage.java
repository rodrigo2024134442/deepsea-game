package pt.isec.pa.deepsea.ui.log;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.isec.pa.deepsea.model.DeepSeaManager;

import java.io.File;

/**
 * Separate window (Stage) that displays all log messages generated
 * during the game session. Can be shown or hidden via the Log menu.
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 3)
 */
public class LogStage extends Stage {

    private final DeepSeaManager manager;
    private ListView<String> logList;

    public LogStage(DeepSeaManager manager, Window owner) {
        this.manager = manager;
        initOwner(owner);
        setTitle("Deep Sea — Logs");
        setAlwaysOnTop(false);
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        logList = new ListView<>();
        logList.setStyle("-fx-background-color: #1a1a2e; -fx-control-inner-background: #1a1a2e;");

        Button btnSave  = new Button("Save Logs");
        Button btnClear = new Button("Clear Logs");

        btnSave .setStyle("-fx-background-color: #0f3460; -fx-text-fill: white;");
        btnClear.setStyle("-fx-background-color: #e94560; -fx-text-fill: white;");

        btnSave.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Save logs as...");
            fc.setInitialDirectory(new File("."));
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Log files (*.txt)", "*.txt"));
            File file = fc.showSaveDialog(this);
            if (file != null) manager.saveLog();
        });

        btnClear.setOnAction(e -> manager.clearLog());

        HBox buttons = new HBox(10, btnSave, btnClear);
        buttons.setPadding(new Insets(10));
        buttons.setStyle("-fx-background-color: #16213e;");

        BorderPane root = new BorderPane();
        root.setCenter(logList);
        root.setBottom(buttons);
        root.setStyle("-fx-background-color: #1a1a2e;");

        setScene(new Scene(root, 600, 400));
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_LOG, e -> update());
    }

    private void update() {
        logList.getItems().setAll(manager.getLog());
        // scroll para a última mensagem
        if (!logList.getItems().isEmpty()) {
            logList.scrollTo(logList.getItems().size() - 1);
        }
    }
}