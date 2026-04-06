package pt.isec.pa.deepsea.model.data.surface;

import pt.isec.pa.deepsea.model.data.GameConfig;

import java.util.Random;

import static pt.isec.pa.deepsea.model.data.GameConfig.*;

/**
 * Represents the surface ocean grid where the ship navigates.
 * Each cell of this grid corresponds to a dive trench and a sea floor zone.
 * Artifacts are randomly distributed across the grid at game start (Req 7).
 *
 * @author Beja
 * @version v1.1.0 (Refactored)
 */
public class SeaSurface {

    private final int rows;
    private final int cols;
    private final SurfaceCell[][] grid;

    /** Creates surface with default size. */
    public SeaSurface() {
        this(GameConfig.DEFAULT_SEASURFACE_ROWS, GameConfig.DEFAULT_SEASURFACE_COLS);
    }

    /** Creates surface and initializes all cells. */
    public SeaSurface(int rows, int cols) {

        this.rows = rows;
        this.cols = cols;
        this.grid = new SurfaceCell[rows][cols];

        Random random = new Random();

        // Initialize all cells without artifacts
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new SurfaceCell(
                        DEFAULT_SEATRENCH_ROWS,
                        DEFAULT_SEATRENCH_COLS
                );
            }
        }
        // Distribute artifacts randomly and fairly across the grid
        if(DEFENSE_MODE){
            distributeArtifactsDM(random);
        }else{
            distributeArtifacts(random);
        }


    }

    /** Distributes artifacts across random cells. */
    private void distributeArtifacts(Random random) {
        int placed = 0;

        while (placed < MAX_ARTIFACTS) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);

            SurfaceCell cell = grid[r][c];

            // Only add artifact if cell is not full
            if (cell.getArtifactCount() < MAX_CELL_ARTIFACTS) {
                cell.addArtifact();
                placed++;
            }
        }
    }
    /** Distributes artifacts ONLY in the middle bottom cells. */
    private void distributeArtifactsDM(Random random) {

        // Defines the boundaries for the central 3x3 area
        int startRow = rows / 2 - 1;
        int startCol = cols / 2 - 1;
        int endRow = startRow + 2;
        int endCol = startCol + 2;

        // Calculates safety limits to avoid infinite loops if the area is too small
        int totalCells = (endRow - startRow + 1) * (endCol - startCol + 1);
        int totalCapacity = totalCells * MAX_CELL_ARTIFACTS;
        int artifactsToPlace = Math.min(MAX_ARTIFACTS, totalCapacity);

        int placed = 0;
        while (placed < artifactsToPlace) {
            // Randomly selects a row and column within the defined central bounds
            int r = random.nextInt(endRow - startRow + 1) + startRow;
            int c = startCol + random.nextInt(endCol - startCol + 1);

            SurfaceCell cell = grid[r][c];

            // Only adds artifact if the specific cell capacity allows it
            if (cell.getArtifactCount() < MAX_CELL_ARTIFACTS) {
                cell.addArtifact();
                placed++;
            }
        }
    }

    /** Gets cell at position (row, col) (null if invalid). */
    public SurfaceCell getCell(int row, int col) {
        if (!isValidPosition(row, col)) return null;
        return grid[row][col];
    }

    /** Gets full grid. */
    public SurfaceCell[][] getGrid() {
        return grid;
    }

    /** Checks if position is valid. */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /** Gets number of rows. */
    public int getRows() {
        return rows;
    }

    /** Gets number of columns. */
    public int getCols() {
        return cols;
    }

    /** Checks if position is already visited. */
    public boolean isVisited(int row, int col) {
        SurfaceCell cell = getCell(row, col);
        return cell != null && cell.isVisited();
    }

    /** Sets position visited. */
    public void markVisited(int row, int col) {
        SurfaceCell cell = getCell(row, col);
        if (cell != null) {
            cell.setVisited(true);
        }
    }

    @Override
    public String toString() {
        return "SeaSurface{rows=" + rows + ", cols=" + cols + "}";
    }
}