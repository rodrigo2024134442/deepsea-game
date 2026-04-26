package pt.isec.pa.deepsea.model.data.entity;

/**
 * Represents a rock formation on the lateral margins of a trench.
 *
 * <p>Rocks are non-living entities that block passage. Contact with a rock
 * reduces the drone's hull integrity. They are generated at game start
 * following the navigability rules defined in {@link pt.isec.pa.deepsea.model.data.trench.SeaTrench}.</p>
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 1)
 */
public class Rock extends Entity {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a rock with the default name.
     */
    public Rock() {
        super("Rock");
    }

    /**
     * Creates a rock as a copy of another rock.
     *
     * @param other rock to copy
     */
    public Rock(Rock other) {
        super(other.getName());
    }

    /**
     * Creates and returns a copy of this rock.
     *
     * @return cloned rock
     */
    @Override
    public Rock clone() {
        return new Rock(this);
    }
}