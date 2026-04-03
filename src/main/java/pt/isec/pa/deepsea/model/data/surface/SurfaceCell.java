package pt.isec.pa.deepsea.model.data.surface;

import pt.isec.pa.deepsea.model.data.trench.SeaTrench;

/**
 * Represents a single cell of the surface ocean grid.
 * Each cell has an associated {@link SeaTrench} for the descent/ascent phase
 * and will have an associated SeaFloor for the exploration phase (not yet implemented).
 *
 * @author Beja
 * @version v1.1.0 (Refactored)
 */
public class SurfaceCell {

    private final SeaTrench trench;
    private boolean visited;
    private int artifactCount;

    // TODO: private SeaFloor seaFloor;
    // Will represent the sea floor grid for this cell (future stage).

    /** Creates a cell and initializes its trench. */
    public SurfaceCell(int trenchRows, int trenchCols) {
        this.trench = new SeaTrench(trenchRows, trenchCols);
        this.trench.generate();
        this.visited = false; // by default start unvisited
        this.artifactCount = 0; // initially no artifacts in this cell

        // TODO: this.seaFloor = new SeaFloor(seaFloorRows, seaFloorCols);
        // TODO: this.seaFloor.generate();
    }

    /** Gets trench. */
    public SeaTrench getTrench() {
        return trench;
    }

    /** Check if this cell is visited */
    public boolean isVisited() {
        return visited;
    }

    /** Set cell to visited */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /** Gets number of artifacts in this cell */
    public int getArtifactCount() {
        return artifactCount;
    }

    /** Adds one artifact to this cell (used during distribution phase) */
    public void addArtifact() {
        artifactCount++;
    }

    /** Collects all artifacts from this cell (future gameplay use) */
    public int collectArtifacts() {
        int collected = artifactCount;
        artifactCount = 0;
        return collected;
    }

    // TODO: public SeaFloor getSeaFloor() { return seaFloor; }

    @Override
    public String toString() {
        return "SurfaceCell{trench=" + trench.getRows() + "x" + trench.getCols() +
                ", artifacts=" + artifactCount + "}";
    }
}