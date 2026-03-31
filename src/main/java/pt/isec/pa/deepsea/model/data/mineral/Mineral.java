package pt.isec.pa.deepsea.model.data.mineral;

/**
 * Represents a mineral resource found on the sea floor.
 * Minerals can be collected and traded for drone upgrades at the workshop.
 *
 * @author Beja
 * @version v1.0.0 (Stage 1)
 */
public class Mineral {

    private final int id;
    private int x;
    private int y;
    private boolean collected;

    /** Creates a mineral with id and position. */
    public Mineral(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.collected = false;
    }

    /** Gets mineral id. */
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
        return "Mineral{id=" + id + ", pos=(" + x + "," + y + "), collected=" + collected + "}";
    }
}