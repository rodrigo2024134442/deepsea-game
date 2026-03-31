package pt.isec.pa.deepsea.model.data.surface;

import pt.isec.pa.deepsea.model.data.trench.SeaTrench;

/**
 * Represents a single cell of the surface ocean grid.
 * Each cell has an associated {@link SeaTrench} for the descent/ascent phase
 * and will have an associated SeaFloor for the exploration phase (not yet implemented).
 *
 * @author Beja
 * @version v1.0.0 (Stage 1)
 */
public class SurfaceCell {

    private final SeaTrench trench;
    private boolean visited;
    private int hasArtifact;

    // TODO: private SeaFloor seaFloor;
    // Will represent the sea floor grid for this cell (future stage).

    /** Creates a cell and initializes its trench. */
    public SurfaceCell(int trenchRows, int trenchCols,int hasArtifactInfo) {
        this.trench = new SeaTrench(trenchRows, trenchCols);
        this.trench.generate();
        this.visited = false; // by default start unvisited
        this.hasArtifact = hasArtifactInfo;
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

    // TODO: public SeaFloor getSeaFloor() { return seaFloor; }

    @Override
    public String toString() {
        return "SurfaceCell{trench=" + trench.getRows() + "x" + trench.getCols() + "}";
    }
}