package pt.isec.pa.deepsea.model.data.trench;

import pt.isec.pa.deepsea.model.data.*;
import pt.isec.pa.deepsea.model.data.cell.UnderwaterCell;
import pt.isec.pa.deepsea.model.data.entity.Entity;
import pt.isec.pa.deepsea.model.data.entity.LivingEntity;
import pt.isec.pa.deepsea.model.data.entity.animal.Animal;
import pt.isec.pa.deepsea.model.data.entity.current.Current;
import pt.isec.pa.deepsea.model.data.entity.rock.Rock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the grid structure of a marine trench used during the
 * descent and ascent phases of the Deep Sea Mining game.
 *
 * <p>The trench is modelled as a bidimensional grid of {@link UnderwaterCell}
 * elements. Rock formations are generated along both lateral margins at game
 * start, following these rules:</p>
 * <ul>
 *   <li>Generation proceeds top to bottom.</li>
 *   <li>The innermost rock column on each side cannot shift more than one
 *       column relative to the previous row.</li>
 *   <li>The total number of rocks on both sides per row cannot exceed 50%
 *       of the grid width.</li>
 * </ul>
 *
 * <p>Dynamic obstacles — {@link Animal} and {@link Current} — are generated
 * lazily (only when the trench becomes active) via
 * {@link #generateObstacles(Random)}. In defense mode, exactly one animal
 * and one current are placed vertically aligned at the center.</p>
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 1)
 */
public class SeaTrench {
    private int rows;
    private int cols;
    private UnderwaterCell[][] grid;
    private boolean obstaclesGenerated = false;

    /**
     * Creates a trench grid with the given dimensions.
     * All cells are initialized as empty {@link UnderwaterCell} instances.
     *
     * @param rows number of rows in the trench grid
     * @param cols number of columns in the trench grid
     */
    public SeaTrench(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new UnderwaterCell[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new UnderwaterCell();
    }

    /**
     * Generates the rock layout of the trench.
     * Delegates to defense mode or normal mode generation depending on
     * {@link GameConfig#DEFENSE_MODE}.
     */
    public void generate() {
        if (GameConfig.DEFENSE_MODE) {
            generateDefenseMode();
        } else {
            generateNormalMode();
        }
    }

    /**
     * Generates rock formations following the navigability rules:
     * top-to-bottom, max 1 column shift per row, max 50% width occupied.
     */
    private void generateNormalMode() {
        Random rand = new Random();
        int leftInner  = cols / 4;
        int rightInner = cols - cols / 4 - 1;

        for (int i = 0; i < rows; i++) {
            leftInner  = Math.max(0, Math.min(leftInner + rand.nextInt(3) - 1, cols / 2 - 1));
            rightInner = Math.max(leftInner + 2, Math.min(rightInner + rand.nextInt(3) - 1, cols - 1));

            while ((leftInner + 1) + (cols - rightInner) > cols / 2) {
                if (leftInner > 0) leftInner--;
                else rightInner++;
            }

            for (int j = 0; j < cols; j++) {
                if (j <= leftInner || j >= rightInner)
                    grid[i][j].setEntity(new Rock());
                else
                    grid[i][j].setEntity(null);
            }
        }
    }

    /**
     * Generates the simplified defense mode layout:
     * exactly one rock on each side (leftmost and rightmost columns) per row.
     */
    private void generateDefenseMode() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j == 0 || j == cols - 1)
                    grid[i][j].setEntity(new Rock());
                else
                    grid[i][j].setEntity(null);
            }
        }
    }

    /**
     * Generates dynamic obstacles (animals and currents) when the trench
     * becomes active. This method is idempotent — calling it more than once
     * has no effect until {@link #resetObstacles()} is called.
     *
     * <p>In defense mode, exactly one animal and one current are placed
     * vertically aligned at the center of the grid.</p>
     *
     * @param rand shared {@link Random} instance
     */
    public void generateObstacles(Random rand) {
        if (obstaclesGenerated) return;

        if (GameConfig.DEFENSE_MODE) {
            generateObstaclesDefenseMode();
        } else {
            generateObstaclesNormalMode(rand);
        }

        obstaclesGenerated = true;
    }

    /**
     * Randomly places up to {@link GameConfig#MAX_OBSTACLES} animals or
     * currents in free cells of the trench.
     *
     * @param rand shared {@link Random} instance
     */
    private void generateObstaclesNormalMode(Random rand) {
        int count = rand.nextInt(GameConfig.MAX_OBSTACLES + 1);
        List<int[]> freeCells = new ArrayList<>();

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (grid[r][c].getEntity() == null)
                    freeCells.add(new int[]{r, c});

        for (int i = 0; i < count && !freeCells.isEmpty(); i++) {
            int[] pos = freeCells.remove(rand.nextInt(freeCells.size()));
            Entity obstacle = rand.nextBoolean()
                    ? new Animal("Animal", 100)
                    : new Current("Current");
            grid[pos[0]][pos[1]].setEntity(obstacle);
        }
    }

    /**
     * Places exactly one animal and one current vertically aligned at the
     * center of the trench grid (defense mode only).
     */
    private void generateObstaclesDefenseMode() {
        int centerCol = cols / 2;
        int centerRow = rows / 2;

        grid[centerRow][centerCol].setEntity(new Animal("Animal", 100));
        grid[centerRow + 1][centerCol].setEntity(new Current("Current"));
    }

    /**
     * Removes all dynamic obstacles (animals and currents) from the grid
     * and resets the generation flag so obstacles can be regenerated.
     * Rock formations are preserved.
     */
    public void resetObstacles() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (grid[r][c].getEntity() instanceof LivingEntity ||
                        grid[r][c].getEntity() instanceof Current)
                    grid[r][c].setEntity(null);
        obstaclesGenerated = false;
    }

    /**
     * Checks whether dynamic obstacles have already been generated.
     *
     * @return true if obstacles have been generated
     */
    public boolean areObstaclesGenerated() { return obstaclesGenerated; }

    /**
     * Gets a cloned copy of the cell at the given position.
     *
     * @param row row of the cell
     * @param col column of the cell
     * @return cloned cell, or null if position is invalid
     */
    public UnderwaterCell getCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return null;
        return grid[row][col].clone();
    }

    /** @return number of rows in the trench grid */
    public int getRows() { return rows; }

    /** @return number of columns in the trench grid */
    public int getCols() { return cols; }

    /** @return the full trench grid */
    public UnderwaterCell[][] getGrid() { return grid; }
}