package pt.isec.pa.deepsea.model.data.entity.animal;

import pt.isec.pa.deepsea.model.data.entity.LivingEntity;

/**
 * Represents a marine animal obstacle found in the trench during
 * descent and ascent phases.
 *
 * <p>Animals are living entities that damage the drone's hull on contact
 * but do not block passage. They are generated lazily when a trench
 * becomes active.</p>
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 1)
 */
public class Animal extends LivingEntity {

    /**
     * Creates an animal with a given name and health value.
     *
     * @param name   name of the animal
     * @param health initial health of the animal
     */
    public Animal(String name, int health) {
        super(name, health);
    }

    /**
     * Creates an animal as a copy of another animal.
     *
     * @param other animal to copy
     */
    public Animal(Animal other) {
        super(other.getName(), other.getHealth());
    }

    /**
     * Creates and returns a copy of this animal.
     *
     * @return cloned animal
     */
    @Override
    public Animal clone() {
        return new Animal(this);
    }
}