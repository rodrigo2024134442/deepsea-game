package pt.isec.pa.deepsea.model.data.surface;

import pt.isec.pa.deepsea.model.data.seafloor.SeaFloor;
import pt.isec.pa.deepsea.model.data.trench.SeaTrench;

import java.util.Random;

/**
 * Represents a single cell of the surface ocean grid.
 * Each cell has a {@link SeaTrench} for the descent/ascent phase
 * and a {@link SeaFloor} for the exploration phase.
 *
 * <p>The SeaFloor is created immediately in the constructor because
 * SeaSurface already computes the correct artifactCount via the
 * artifact map before any SurfaceCell is instantiated.</p>
 *
 * @author Beja
 * @version v1.3.0 (Direct SeaFloor init via artifact map)
 */
public class SurfaceCell {

    private final SeaTrench trench;
    private final SeaFloor seafloor;
    private boolean visited;
    private int artifactCount;

    /** Creates a cell, its trench, and its sea floor with the correct artifact count. */
    public SurfaceCell(int trenchRows, int trenchCols, int artifactCount, int seafloorRows, int seafloorCols) {
        this.trench = new SeaTrench(trenchRows, trenchCols);
        this.trench.generate();
        this.trench.generateObstacles(new Random());
        this.visited = false;
        this.artifactCount = artifactCount;
        this.seafloor = new SeaFloor(seafloorRows, seafloorCols, artifactCount);
    }

    /** Gets the sea floor for this cell. */
    public SeaFloor getSeaFloor() {
        return seafloor;
    }

    /** Gets the trench for this cell. */
    public SeaTrench getTrench() {
        return trench;
    }

    /** Returns true if this cell has been visited by the ship. */
    public boolean isVisited() { return visited; }

    /** Sets visited state. */
    public void setVisited(boolean visited) { this.visited = visited; }

    /** Gets the number of artifacts assigned to this cell. */
    public int getArtifactCount() { return artifactCount; }

    /** Removes and returns all artifacts from this cell. */
    public int collectArtifacts() {
        int collected = artifactCount;
        artifactCount = 0;
        return collected;
    }

    /** Copy constructor. */
    public SurfaceCell(SurfaceCell other) {
        this.trench = new SeaTrench(other.trench);
        this.visited = other.visited;
        this.artifactCount = other.artifactCount;
        this.seafloor = new SeaFloor(other.seafloor);
    }

    @Override
    public SurfaceCell clone() {
        return new SurfaceCell(this);
    }

    @Override
    public String toString() {
        return "SurfaceCell{trench=" + trench.getRows() + "x" + trench.getCols() +
                ", artifacts=" + artifactCount + ", visited=" + visited + "}";
    }
}