package pt.isec.pa.deepsea.ui.res;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton that loads and caches all game images.
 * Images are loaded once and reused across all views.
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage Final)
 */
public class ImageManager {

    private static ImageManager instance;
    private final Map<String, Image> cache = new HashMap<>();

    // Keys
    public static final String SHIP            = "ship";
    public static final String DRONE           = "drone";
    public static final String ROCK            = "rock";
    public static final String ANIMAL          = "animal";
    public static final String CURRENT         = "current";
    public static final String MINERAL_1       = "mineral_1";
    public static final String MINERAL_2       = "mineral_2";
    public static final String ARTIFACT_1      = "artifact_1";
    public static final String ARTIFACT_2      = "artifact_2";
    public static final String MONSTER_1       = "monster_1";
    public static final String MONSTER_2       = "monster_2";
    public static final String BG_SURFACE      = "bg_surface";
    public static final String BG_SEAFLOOR     = "bg_seafloor";
    public static final String BG_CLOUD        = "bg_cloud";
    public static final String BG_SHOP         = "bg_shop";
    public static final String WAVE_1          = "wave_1";
    public static final String WAVE_2          = "wave_2";
    public static final String ARTIFACT_MARKER = "artifact_marker";
    public static final String TRENCH_BG       = "trench_bg";
    public static final String ROCK_TRENCH     = "rock";
    public static final String CURRENT_TRENCH  = "current";
    public static final String ANIMAL_1        = "animal_1";
    public static final String ANIMAL_2        = "animal_2";
    public static final String ANIMAL_3        = "animal_3";
    public static final String ANIMAL_4        = "animal_4";
    public static final String ANIMAL_5        = "animal_5";
    public static final String ANIMAL_6        = "animal_6";

    private ImageManager() {
        load(SHIP,            "/images/vehicles/research_ship_heavy_support.png");
        load(DRONE,           "/images/vehicles/mining_drone_heavy_rov.png");
        load(ROCK,            "/images/objects/rock.png");
        load(ANIMAL,          "/images/creatures/animal.png");
        load(CURRENT,         "/images/objects/current.png");
        load(MINERAL_1,       "/images/objects/mineral_purple_crystal_cluster.png");
        load(MINERAL_2,       "/images/objects/mineral_gold_ore_cluster.png");
        load(ARTIFACT_1,      "/images/objects/artifact_ancient_stone_mask.png");
        load(ARTIFACT_2,      "/images/objects/artifact_sunken_treasure_chest.png");
        load(MONSTER_1,       "/images/creatures/monster6.png");
        load(MONSTER_2,       "/images/creatures/monster7.png");
        load(BG_SURFACE,      "/images/backgrounds/SeaSurface.png");
        load(BG_SEAFLOOR,     "/images/backgrounds/sea_floor_grid_background.png");
        load(BG_CLOUD,        "/images/backgrounds/DarkUnderwaterCloud.png");
        load(BG_SHOP,         "/images/backgrounds/shop.png");
        load(WAVE_1,          "/images/backgrounds/wave1.png");
        load(WAVE_2,          "/images/backgrounds/wave2.png");
        load(ARTIFACT_MARKER, "/images/backgrounds/artifactMarker.png");
        load(TRENCH_BG,       "/images/backgrounds/SeaTrench.png");
        load(ROCK_TRENCH,     "/images/objects/rocha1.png");
        load(CURRENT_TRENCH,  "/images/objects/TrenchCurrent.png");
        load(ANIMAL_1,        "/images/creatures/trench_animal1.png");
        load(ANIMAL_2,        "/images/creatures/trench_animal2.png");
        load(ANIMAL_3,        "/images/creatures/trench_animal3.png");
        load(ANIMAL_4,        "/images/creatures/trench_animal4.png");
        load(ANIMAL_5,        "/images/creatures/trench_animal5.png");
        load(ANIMAL_6,        "/images/creatures/trench_animal6.png");
    }

    /**
     * Returns the single instance of ImageManager.
     *
     * @return singleton instance
     */
    public static ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    /**
     * Returns the cached image for the given key.
     *
     * @param key image key (use the constants defined in this class)
     * @return cached image, or null if not found
     */
    public Image get(String key) {
        return cache.get(key);
    }

    private void load(String key, String path) {
        try {
            cache.put(key, new Image(ImageManager.class.getResourceAsStream(path)));
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path);
        }
    }
}