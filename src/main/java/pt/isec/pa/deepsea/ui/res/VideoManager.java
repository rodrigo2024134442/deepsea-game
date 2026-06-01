package pt.isec.pa.deepsea.ui.res;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton that loads and caches all game videos.
 * Videos are loaded once and a new MediaPlayer is created for each playback.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage Final)
 */
public class VideoManager {

    private static VideoManager instance;
    private final Map<String, Media> cache = new HashMap<>();

    // Keys
    public static final String DRONE_DEPLOY    = "drone_deploy";
    public static final String DRONE_DESTROYED = "drone_destroyed";
    public static final String GAME_WON        = "game_won";
    public static final String GAME_LOST       = "game_lost";

    private VideoManager() {
        load(DRONE_DEPLOY,    "/videos/drone_deploy.mp4");
        load(DRONE_DESTROYED, "/videos/drone_destroyed.mp4");
        load(GAME_WON,        "/videos/game_won.mp4");
        load(GAME_LOST,       "/videos/game_lost.mp4");
    }

    /**
     * Returns the single instance of VideoManager.
     *
     * @return singleton instance
     */
    public static VideoManager getInstance() {
        if (instance == null) {
            instance = new VideoManager();
        }
        return instance;
    }

    /**
     * Returns the cached Media for the given key.
     *
     * @param key video key (use the constants defined in this class)
     * @return cached media, or null if not found
     */
    public Media get(String key) {
        return cache.get(key);
    }



    /**
     * Plays the video with the given key in a modal Stage.
     * The window closes automatically when playback ends or when the user clicks "Skip".
     *
     * @param key   video key (use the constants defined in this class)
     * @param owner owner window (can be null)
     */
    /**
     * Plays the video. When the window is closed (end-of-media, Skip, or X),
     * the given {@code onClose} is executed (if not null).
     *
     * @param key     video key (use the constants defined in this class)
     * @param owner   owner window (can be null)
     * @param onClose action to run when the video stage hides (can be null)
     */
    public void play(String key, Window owner, Runnable onClose) {
        Media media = cache.get(key);
        if (media == null) {
            if (onClose != null) onClose.run();
            return;
        }

        MediaPlayer player = new MediaPlayer(media);
        MediaView view = new MediaView(player);
        view.setFitWidth(640);
        view.setFitHeight(360);
        view.setPreserveRatio(true);

        Button btnSkip = new Button("Skip");
        btnSkip.setOnAction(e -> {
            player.stop();
            ((Stage) btnSkip.getScene().getWindow()).close();
        });

        VBox content = new VBox(10, view, btnSkip);
        content.setStyle("-fx-background-color: black; -fx-alignment: center; -fx-padding: 8;");

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        if (owner != null) stage.initOwner(owner);
        stage.setTitle("Video");
        stage.setResizable(false);
        stage.setScene(new Scene(content, 660, 410));

        player.setOnEndOfMedia(() -> {
            player.stop();
            stage.close();
        });
        stage.setOnCloseRequest(e -> player.stop());

        if (onClose != null) {
            stage.setOnHidden(e -> onClose.run());
        }

        stage.show();
        player.play();
    }

    private void load(String key, String path) {
        try {
            var url = VideoManager.class.getResource(path);
            if (url == null) {
                System.err.println("Video not found: " + path);
                return;
            }
            cache.put(key, new Media(url.toExternalForm()));
        } catch (Exception e) {
            System.err.println("Failed to load video: " + path);
        }
    }
}