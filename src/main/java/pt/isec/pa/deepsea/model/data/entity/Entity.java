package pt.isec.pa.deepsea.model.data.entity;

import static pt.isec.pa.deepsea.model.data.GameConfig.ID_START;

/**
 * Abstract base class for all game entities.
 * This class has two main derived classes: LivingEntity and NonlivingEntity that extend this class.
 * Each entity has a unique identifier and a name.
 * The identifier is assigned automatically in sequence starting from the configured initial value in GameConfig file.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
public abstract class Entity {
    private static int nextId = ID_START;
    private final int id;
    private final String name;

    /**
     * Creates a new entity with the given name.
     * The entity receives an automatically generated unique identifier
     *
     * @param name name of the entity
     */
    public Entity(String name) {
        this.id = nextId++;
        this.name = name;
    }

    /**
     * Gets the name of the entity.
     *
     * @return entity name
     */
    public String getName() {
        return name;
    }

    /**
     * Creates and returns a copy of the entity.
     *
     * @return cloned entity
     */
    public abstract Entity clone();

    /**
     * Gets the unique identifier of the entity.
     *
     * @return entity identifier
     */
    public int getId() {
        return id;
    }
}