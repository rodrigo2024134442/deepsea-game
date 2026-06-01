package pt.isec.pa.deepsea.model.data.entity;

/**
 * Represents a mineral resource found on the sea floor.
 * Minerals can be collected and traded for drone upgrades at the workshop.
 *
 * @author Beja
 * @version v1.0.0 (Stage 1)
 */
public class Mineral extends Entity {
    private static final long serialVersionUID = 1L;
    private final int value;

    /** Creates a mineral with default value 1. */
    public Mineral(String name) {
        super(name);
        this.value = 1;
    }

    /** Creates a mineral with explicit value. */
    public Mineral(String name, int value) {
        super(name);
        this.value = Math.max(1, value);
    }

    public Mineral(Mineral other) {
        super(other.getName());
        this.value = other.value;
    }

    @Override
    public Mineral clone() {
        return new Mineral(this);
    }

    /** Returns mineral's value. */
    public int getValue() { return value; }


   /* @Override
    public String toString() {
        return "Mineral{id=" + id + " collected = " + collected}";
    }*/
}