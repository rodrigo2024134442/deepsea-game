package pt.isec.pa.deepsea.model.data.entity;

/**
 * Represents a maritime current obstacle found in the trench during
 * descent and ascent phases.
 *
 * <p>Currents are non-living entities that do not block passage but force
 * the drone to consume double the normal fuel cost per movement.
 * They are generated lazily when a trench becomes active.</p>
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 1)
 */
public class Current extends Entity {
    private static final long serialVersionUID = 1L;
    /**
     * Creates a current with the given name.
     *
     * @param name name of the current
     */
    public Current(String name) {
        super(name);
    }

    /**
     * Creates a current as a copy of another current.
     *
     * @param other current to copy
     */
    public Current(Current other) {
        super(other.getName());
    }

    /**
     * Creates and returns a copy of this current.
     *
     * @return cloned current
     */
    @Override
    public Current clone() {
        return new Current(this);
    }
}