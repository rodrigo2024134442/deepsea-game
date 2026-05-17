package pt.isec.pa.deepsea.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.isec.pa.deepsea.model.DeepSeaManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Dialog for loading recently saved games (5 most recent).
 * Displays a list of recent game files with their modification times.
 */
public class RecentGamesDialog extends Stage {

    private final DeepSeaManager manager;
    private ListView<File> gameList;
    private File selectedGame = null;

    public RecentGamesDialog(DeepSeaManager manager, Window owner) {
        this.manager = manager;
        initOwner(owner);
        setTitle("Load Recent Games");
        setAlwaysOnTop(false);
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        gameList = new ListView<>();

        // Custom cell factory to display file name and date
        gameList.setCellFactory(lv -> new ListCell<File>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                if (empty || file == null) {
                    setText(null);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String text = file.getName() + "  [" + sdf.format(new Date(file.lastModified())) + "]";
                    setText(text);
                }
            }
        });

        gameList.setStyle("-fx-background-color: #1a1a2e; -fx-control-inner-background: #1a1a2e;");

        // Buttons
        Button btnLoad   = new Button("Load");
        Button btnCancel = new Button("Cancel");

        btnLoad.setStyle("-fx-background-color: #0f3460; -fx-text-fill: white;");
        btnCancel.setStyle("-fx-background-color: #e94560; -fx-text-fill: white;");

        btnLoad.setPrefWidth(80);
        btnCancel.setPrefWidth(80);

        btnLoad.setOnAction(e -> {
            selectedGame = gameList.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                manager.loadGame(selectedGame);
                close();
            }
        });

        btnCancel.setOnAction(e -> close());

        HBox buttons = new HBox(10, btnLoad, btnCancel);
        buttons.setPadding(new Insets(10));
        buttons.setStyle("-fx-background-color: #16213e;");

        BorderPane root = new BorderPane();
        root.setCenter(gameList);
        root.setBottom(buttons);
        root.setStyle("-fx-background-color: #1a1a2e;");

        setScene(new Scene(root, 500, 300));
    }

    private void registerHandlers() {
        // No-op
    }

    private void update() {
        List<File> recentGames = manager.getRecentGames();
        if (recentGames.isEmpty()) {
            gameList.setPlaceholder(new javafx.scene.control.Label("No recent games found"));
        } else {
            gameList.getItems().setAll(recentGames);
            gameList.getSelectionModel().selectFirst();
        }
    }
}
