package pt.isec.pa.deepsea.model.data.entity;

/**
 * Represents a mineral resource found on the sea floor.
 * Minerals can be collected and traded for drone upgrades at the workshop.
 *
 * @author Beja
 * @version v1.0.0 (Stage 1)
 */
public class Mineral extends Entity {

    /** Creates a mineral with id and position. */
    public Mineral(String name) {
        super(name);

    }

    public Mineral(Mineral other) {
        super(other.getName());
    }

    @Override
    public Mineral clone() {
        return new Mineral(this);
    }


   /* @Override
    public String toString() {
        return "Mineral{id=" + id + " collected = " + collected}";
    }*/
}