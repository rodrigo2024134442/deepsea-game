package pt.isec.pa.deepsea.model.data.entity;

/**
 * Abstract base class for all living entities in the game.
 * This class extends Entity and represents the living branch of the entity hierarchy.
 * Living entities have health and may be alive or dead depending on their current health value.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
public abstract class LivingEntity extends Entity {
    private int health;

    /**
     * Creates a living entity with a given name and health value.
     *
     * @param name name of the living entity
     * @param health initial health value
     */
    public LivingEntity(String name, int health) {
        super(name);
        this.health = health;
    }

    /**
     * Gets the current health of the living entity.
     *
     * @return current health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * Updates the current health of the living entity.
     *
     * @param health new health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Checks whether the living entity is alive.
     *
     * @return true if health is greater than zero, false otherwise
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Creates and returns a copy of the living entity.
     *
     * @return cloned living entity
     */
    @Override
    public abstract LivingEntity clone();
}