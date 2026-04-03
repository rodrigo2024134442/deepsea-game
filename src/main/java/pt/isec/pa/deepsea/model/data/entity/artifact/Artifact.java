package pt.isec.pa.deepsea.model.data.entity.artifact;

/**
 * Represents an ancient artifact to be recovered in the Deep Sea Mining game.
 * Each artifact has a unique ID and a position on the sea floor grid.
 *
 * @author Beja
 * @version v1.0.0 (Stage 1)
 */
public class Artifact {

    private final int id;
    private int x;
    private int y;
    private boolean collected;

    /** Creates an artifact with id and position. */
    public Artifact(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.collected = false;
    }

    /** Gets artifact id. */
    public int getId() {
        return id;
    }

    /** Gets X position. */
    public int getX() {
        return x;
    }

    /** Sets X position. */
    public void setX(int x) {
        this.x = x;
    }

    /** Gets Y position. */
    public int getY() {
        return y;
    }

    /** Sets Y position. */
    public void setY(int y) {
        this.y = y;
    }

    /** Checks if collected. */
    public boolean isCollected() {
        return collected;
    }

    /** Sets collected state. */
    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    @Override
    public String toString() {
        return "Artifact{id=" + id + ", pos=(" + x + "," + y + "), collected=" + collected + "}";
    }
}