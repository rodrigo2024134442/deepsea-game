package pt.isec.pa.deepsea.ui.res;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton that loads and plays all game sound effects.
 *
 * <p>Provides two playback modes:</p>
 * <ul>
 *   <li>{@link #play(String)} — plays immediately, cancelling any previous sound.
 *       Adequate for fast-succession sounds (e.g. movement).</li>
 *   <li>{@link #playAfter(String)} — waits for the current sound to finish before
 *       playing. Adequate for "consequence" sounds (e.g. animal hit after move).</li>
 * </ul>
 *
 * <p>Provides {@link #enabledProperty()} (JavaFX BooleanProperty) so the UI
 * can bind a checkbox/menu item to toggle all sounds on/off.</p>
 *
 * @author Joao Carvalho - 2024126292
 * @version v1.0.0 (Stage Final)
 */
public class SoundManager {

    private static SoundManager instance;
    private final Map<String, Media> cache = new HashMap<>();
    private final BooleanProperty enabled = new SimpleBooleanProperty(true);
    private MediaPlayer currentPlayer;

    // Keys
    public static final String MOVE              = "move";
    public static final String DRONE_DEPLOY      = "drone_deploy";
    public static final String HIT        = "hit_animal";
    public static final String MINING            = "mining";
    public static final String RETRIEVE_ARTIFACT = "retrieve_artifact";

    private SoundManager() {
        load(MOVE,              "/sounds/move.wav");
        load(DRONE_DEPLOY,      "/sounds/drone_deploy.wav");
        load(HIT,        "/sounds/hit.wav");
        load(MINING,            "/sounds/mining.wav");
        load(RETRIEVE_ARTIFACT, "/sounds/retrieve_artifact.wav");
    }

    /**
     * Returns the single instance of SoundManager.
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * BooleanProperty controlling whether sounds are on or off.
     * Can be bound to UI controls (e.g. a CheckBox or menu item).
     */
    public BooleanProperty enabledProperty() {
        return enabled;
    }

    /** Convenience getter. */
    public boolean isEnabled() {
        return enabled.get();
    }

    /** Convenience setter. */
    public void setEnabled(boolean value) {
        enabled.set(value);
    }

    /**
     * Plays the sound immediately. If a sound is currently playing, it is stopped.
     * Use for fast-succession sounds (movement).
     */
    public void play(String key) {
        if (!enabled.get()) return;
        Media media = cache.get(key);
        if (media == null) return;

        if (currentPlayer != null) currentPlayer.stop();

        MediaPlayer player = new MediaPlayer(media);
        currentPlayer = player;
        player.setOnEndOfMedia(() -> {
            if (currentPlayer == player) currentPlayer = null;
        });
        player.play();
    }

    /**
     * Plays the sound after the current sound finishes (if any is playing).
     * If nothing is playing, plays immediately.
     * Use for "consequence" sounds (animal hit after move).
     */
    public void playAfter(String key) {
        if (!enabled.get()) return;
        Media media = cache.get(key);
        if (media == null) return;

        MediaPlayer previous = currentPlayer;
        if (previous == null || previous.getStatus() != MediaPlayer.Status.PLAYING) {
            play(key);
            return;
        }

        previous.setOnEndOfMedia(() -> {
            if (currentPlayer == previous) currentPlayer = null;
            play(key);
        });
    }

    /** Stops the current sound (if any). */
    public void stop() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer = null;
        }
    }

    private void load(String key, String path) {
        try {
            var url = SoundManager.class.getResource(path);
            if (url == null) {
                System.err.println("Sound not found: " + path);
                return;
            }
            cache.put(key, new Media(url.toExternalForm()));
        } catch (Exception e) {
            System.err.println("Failed to load sound: " + path);
        }
    }
}