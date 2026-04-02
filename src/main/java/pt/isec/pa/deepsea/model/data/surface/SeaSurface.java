package pt.isec.pa.deepsea.model.data.surface;

import pt.isec.pa.deepsea.model.data.GameConfig;

import java.util.List;
import java.util.Random;

/**
 * Represents the surface ocean grid where the ship navigates.
 * Each cell of this grid corresponds to a dive trench and a sea floor zone.
 * Artifacts are randomly distributed across the grid at game start (Req 7).
 *
 * @author Beja
 * @version v1.0.0 (Stage 1)
 */
public class SeaSurface {
    private final int rows;
    private final int cols;
    private final SurfaceCell[][] grid;
    private int hasArtifact = 0;
    private int artifactAtual = 0;

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

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                if (artifactAtual < GameConfig.MAX_ARTIFACTS) {
                    hasArtifact = random.nextInt(GameConfig.MAX_CELL_ARTIFACTS); // 0,1,2,3

                    if (hasArtifact > 0) {
                        artifactAtual += hasArtifact;
                    }
                }

                grid[r][c] = new SurfaceCell(GameConfig.DEFAULT_SEATRENCH_ROWS, GameConfig.DEFAULT_SEATRENCH_COLS,  hasArtifact);
            }
        }
    }

    /** Gets cell at position (null if invalid). */
    public SurfaceCell getCell(int x, int y) {
        if (!isValidPosition(x, y)) return null;
        return grid[y][x];
    }

    /** Gets full grid. */
    public SurfaceCell[][] getGrid() {
        return grid;
    }

    /** Checks if position is valid. */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    /** Gets number of rows. */
    public int getRows() {
        return rows;
    }

    /** Gets number of columns. */
    public int getCols() {
        return cols;
    }

    /** Checks if position is already used. */
    private boolean positionUsed(List<int[]> used, int x, int y) {
        return used.stream().anyMatch(p -> p[0] == x && p[1] == y);
    }
    /** Checks if position is already visited. */
    public boolean isVisited(int x, int y) {
        SurfaceCell cell = getCell(x, y);
        return cell != null && cell.isVisited();
    }
    /** Sets position to visited. */
    public void markVisited(int x, int y) {
        SurfaceCell cell = getCell(x, y);
        if (cell != null) {
            cell.setVisited(true);
        }
    }
    @Override
    public String toString() {
        return "SeaSurface{rows=" + rows + ", cols=" + cols+ "}";
    }
}