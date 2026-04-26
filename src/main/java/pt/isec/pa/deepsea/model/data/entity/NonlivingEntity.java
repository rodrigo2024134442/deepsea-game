package pt.isec.pa.deepsea.model.data.entity;

/**
 * Abstract base class for all non-living entities in the game.
 * This class extends Entity and represents the non-living branch of the entity hierarchy.
 * Non-living entities do not have health and are used for static or collectible game elements.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
public abstract class NonlivingEntity extends Entity {

    /**
     * Creates a non-living entity with the given name.
     *
     * @param name name of the non-living entity
     */
    public NonlivingEntity(String name) {
        super(name);
    }

    /**
     * Creates and returns a copy of the non-living entity.
     *
     * @return cloned non-living entity
     */
    @Override
    public abstract NonlivingEntity clone();
}