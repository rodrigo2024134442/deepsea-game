package pt.isec.pa.deepsea.ui;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import pt.isec.pa.deepsea.model.DeepSeaManager;

import java.io.File;

/**
 * Menu bar for the Deep Sea Mining UI.
 *
 * <p>Provides the following menus:</p>
 * <ul>
 *   <li><b>Game:</b> New, Open, Save As, Exit</li>
 *   <li><b>Log:</b> Show/Hide, Save Log, Clear Log</li>
 * </ul>
 */
public class DeepSeaMenuBar extends MenuBar {
    DeepSeaManager manager;
    Menu mnGame, mnLog;
    MenuItem mnNew, mnOpen, mnLoadRecent, mnSaveAs, mnExit;
    MenuItem mnShowLog, mnSaveLog, mnClearLog;

    public DeepSeaMenuBar(DeepSeaManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        mnGame = new Menu("_Game");
        mnLog  = new Menu("_Log");
        getMenus().addAll(mnGame, mnLog);

        mnNew         = new MenuItem("_New");
        mnOpen        = new MenuItem("_Open...");
        mnLoadRecent  = new MenuItem("Open _recent");
        mnSaveAs      = new MenuItem("_Save as...");
        mnExit        = new MenuItem("E_xit");
        mnGame.getItems().addAll(mnNew, mnOpen, mnLoadRecent,
                new SeparatorMenuItem(), mnSaveAs,
                new SeparatorMenuItem(), mnExit);

        mnShowLog  = new MenuItem("_Show logs");
        mnSaveLog  = new MenuItem("_Save logs");
        mnClearLog = new MenuItem("_Clear logs");
        mnLog.getItems().addAll(mnShowLog, mnSaveLog,
                new SeparatorMenuItem(), mnClearLog);
    }

    private void registerHandlers() {
        mnNew.setOnAction(e -> manager.newGame());
        mnExit.setOnAction(e -> Platform.exit());

        mnOpen.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("File open...");
            fc.setInitialDirectory(new File(pt.isec.pa.deepsea.model.DeepSeaManager.getSavesDirectory()));
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("DeepSea (*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fc.showOpenDialog(this.getScene().getWindow());
            if (hFile != null) manager.loadGame(hFile);
        });

        mnLoadRecent.setOnAction(e -> {
            new RecentGamesDialog(manager, getScene().getWindow()).show();
        });

        mnSaveAs.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("File save...");
            fc.setInitialDirectory(new File(pt.isec.pa.deepsea.model.DeepSeaManager.getSavesDirectory()));
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("DeepSea (*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fc.showSaveDialog(this.getScene().getWindow());
            if (hFile != null) manager.saveGame(hFile);
        });

        mnShowLog.setOnAction(e -> {
            new pt.isec.pa.deepsea.ui.log.LogStage(manager, getScene().getWindow()).show();
        });

        mnSaveLog.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Save logs as...");
            fc.setInitialDirectory(new File(pt.isec.pa.deepsea.model.DeepSeaManager.getLogsDirectory()));
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fc.showSaveDialog(this.getScene().getWindow());
            if (hFile != null) {
                if (manager.saveLogToFile(hFile)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Log saved successfully to " + hFile.getName());
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to save log.");
                    alert.showAndWait();
                }
            }
        });

        mnClearLog.setOnAction(e -> manager.clearLog());
    }

    private void update() {
        // Currently no dynamic updates needed
    }
}
