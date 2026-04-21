package pt.isec.pa.deepsea.model.data.surface;

import pt.isec.pa.deepsea.model.data.Position;
import pt.isec.pa.deepsea.model.data.ship.Ship;

import java.util.Random;

import static pt.isec.pa.deepsea.model.data.GameConfig.*;

/**
 * Represents the surface ocean grid where the ship navigates.
 * Each cell has a trench and a sea floor.
 * Artifacts are distributed via an auxiliary integer map before cells are created,
 * ensuring each SurfaceCell is initialized with the correct artifact count.
 *
 * @author Beja
 * @version v1.3.0 (Artifact map approach)
 */
public class SeaSurface {
    private Ship ship;
    private final int rows;
    private final int cols;
    private final SurfaceCell[][] grid;

    /** Creates the surface grid and distributes artifacts. */
    public SeaSurface(int rows, int cols, Ship ship) {
        this.rows = rows;
        this.cols = cols;
        this.ship = ship;
        this.grid = new SurfaceCell[rows][cols];

        Random random = new Random();

        // distribute artifacts in an auxiliary integer map (all start at 0)
        int[][] artifactMap = new int[rows][cols];
        if (DEFENSE_MODE) {
            distributeArtifactsDM(random, artifactMap);
        } else {
            distributeArtifacts(random, artifactMap);
        }

        // create SurfaceCells already with the correct artifactCount
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new SurfaceCell(
                        DEFAULT_SEATRENCH_ROWS,
                        DEFAULT_SEATRENCH_COLS,
                        artifactMap[r][c],
                        DEFAULT_SEAFLOOR_ROWS,
                        DEFAULT_SEAFLOOR_COLS
                );
            }
        }
    }

    /** Distributes artifacts randomly across all cells. */
    private void distributeArtifacts(Random random, int[][] artifactMap) {
        int placed = 0;
        while (placed < MAX_ARTIFACTS) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);
            if (artifactMap[r][c] < MAX_CELL_ARTIFACTS) {
                artifactMap[r][c]++;
                placed++;
            }
        }
    }

    /** Distributes artifacts only in the central 3x3 area (defense mode). */
    private void distributeArtifactsDM(Random random, int[][] artifactMap) {
        int startRow = rows / 2 - 1;
        int startCol = cols / 2 - 1;
        int endRow   = startRow + 2;
        int endCol   = startCol + 2;

        int totalCapacity = (endRow - startRow + 1) * (endCol - startCol + 1) * MAX_CELL_ARTIFACTS;
        int artifactsToPlace = Math.min(MAX_ARTIFACTS, totalCapacity);

        int placed = 0;
        while (placed < artifactsToPlace) {
            int r = random.nextInt(endRow - startRow + 1) + startRow;
            int c = startCol + random.nextInt(endCol - startCol + 1);
            if (artifactMap[r][c] < MAX_CELL_ARTIFACTS) {
                artifactMap[r][c]++;
                placed++;
            }
        }
    }

    /** Gets cell at Position. Returns null if invalid. */
    public SurfaceCell getCell(Position pos) {
        return getCell(pos.getRow(), pos.getCol());
    }

    /** Gets cell at (row, col). Returns null if invalid. */
    public SurfaceCell getCell(int row, int col) {
        if (!isValidPosition(row, col)) return null;
        return grid[row][col];
    }

    /** Returns the full grid. */
    public SurfaceCell[][] getGrid() { return grid; }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean isValidPosition(Position pos) {
        return isValidPosition(pos.getRow(), pos.getCol());
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public boolean isVisited(Position pos) {
        SurfaceCell cell = getCell(pos);
        return cell != null && cell.isVisited();
    }

    public void markVisited(Position pos) {
        SurfaceCell cell = getCell(pos);
        if (cell != null) cell.setVisited(true);
    }

    /** Copy constructor. */
    public SeaSurface(SeaSurface other) {
        this.rows = other.rows;
        this.cols = other.cols;
        this.ship = other.ship;
        this.grid = new SurfaceCell[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                this.grid[r][c] = new SurfaceCell(other.grid[r][c]);
    }

    @Override
    public SeaSurface clone() { return new SeaSurface(this); }

    @Override
    public String toString() {
        return "SeaSurface{rows=" + rows + ", cols=" + cols + "}";
    }
}