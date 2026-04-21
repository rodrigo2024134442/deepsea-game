package pt.isec.pa.deepsea.model.data.entity;


/**
 * Represents an ancient artifact to be recovered in the Deep Sea Mining game.
 * Each artifact has a unique ID and a position on the sea floor grid.
 *
 * @author Beja
 * @version v1.0.0 (Stage 1)
 */
public class Artifact extends NonlivingEntity {

    /** Creates an artifact with id and position. */
    public Artifact(String name) {
        super(name);
    }
    public Artifact(Artifact other) {
        super(other.getName());
    }

    @Override
    public Artifact clone() {
        return new Artifact(this);
    }


    /*
    @Override
    public String toString() {
        return "Artifact{id=" + id + ", pos=(" + x + "," + y + "), collected=" + collected + "}";
    }*/
}