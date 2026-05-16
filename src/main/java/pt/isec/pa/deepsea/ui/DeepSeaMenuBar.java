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
    MenuItem mnNew, mnOpen, mnSaveAs, mnExit;
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

        mnNew    = new MenuItem("_New");
        mnOpen   = new MenuItem("_Open...");
        mnSaveAs = new MenuItem("_Save as...");
        mnExit   = new MenuItem("E_xit");
        mnGame.getItems().addAll(mnNew, mnOpen,
                new SeparatorMenuItem(), mnSaveAs,
                new SeparatorMenuItem(), mnExit);

        mnShowLog  = new MenuItem("_Show Log");
        mnSaveLog  = new MenuItem("_Save Log...");
        mnClearLog = new MenuItem("_Clear Log");
        mnLog.getItems().addAll(mnShowLog, mnSaveLog,
                new SeparatorMenuItem(), mnClearLog);
    }

    private void registerHandlers() {
        mnNew.setOnAction(e -> manager.newGame());
        mnExit.setOnAction(e -> Platform.exit());

        mnSaveAs.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("File save...");
            fc.setInitialDirectory(new File("."));
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("DeepSea (*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fc.showSaveDialog(this.getScene().getWindow());
            if (hFile != null) manager.saveGame(hFile);
        });

        mnOpen.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("File open...");
            fc.setInitialDirectory(new File("."));
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("DeepSea (*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fc.showOpenDialog(this.getScene().getWindow());
            if (hFile != null) manager.loadGame(hFile);
        });

        mnShowLog.setOnAction(e -> {
            // TODO: Leonardo irá criar LogStage e passar aqui
            // new LogStage(manager, getScene().getWindow()).show();
            System.out.println("Log Stage placeholder - Leonardo implementa");
        });

        mnSaveLog.setOnAction(e -> manager.saveLog());

        mnClearLog.setOnAction(e -> manager.clearLog());
    }

    private void update() {
        // Currently no dynamic updates needed
    }
}
