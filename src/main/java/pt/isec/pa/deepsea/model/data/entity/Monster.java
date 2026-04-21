package pt.isec.pa.deepsea.model.data.entity;

/**
 * Represents a monster entity found in the game.
 * A monster is a living entity, meaning it has health and can be alive or dead.
 * Each monster can also produce a predefined sound.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
public class Monster extends Entity {
    private final String speak= "Roar!!! Coff...Coff...";

    /**
     * Creates a monster with a given name and health value.
     *
     * @param name name of the monster
     * @param health initial health of the monster
     */
    public Monster(String name, int health) {
        super(name);
    }

    /**
     * Creates a monster as a copy of another monster.
     *
     * @param other monster to copy
     */
    public Monster(Monster other) {
        super(other.getName());
    }

    /**
     * Creates and returns a copy of this monster.
     *
     * @return cloned monster
     */
    @Override
    public Monster clone() {
        return new Monster(this);
    }

    /**
     * Returns the sound produced by the monster.
     *
     * @return monster sound
     */
    public String talk (){return speak;}
}