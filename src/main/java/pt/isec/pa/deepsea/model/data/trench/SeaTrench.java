package pt.isec.pa.deepsea.model.data.trench;

import pt.isec.pa.deepsea.model.data.*;
import pt.isec.pa.deepsea.model.data.cell.UnderwaterCell;
import pt.isec.pa.deepsea.model.data.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the grid structure of a marine trench used during the
 * descent and ascent phases of the Deep Sea Mining game.
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.1.0 (Position refactor)
 */
public class SeaTrench {
    private int rows;
    private int cols;
    private UnderwaterCell[][] grid;
    private boolean obstaclesGenerated = false;

    public SeaTrench(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new UnderwaterCell[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new UnderwaterCell();
    }

    public void generate() {
        if (GameConfig.DEFENSE_MODE) {
            generateDefenseMode();
        } else {
            generateNormalMode();
        }
    }

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

    public void generateObstacles(Random rand) {
        if (obstaclesGenerated) return;

        if (GameConfig.DEFENSE_MODE) {
            generateObstaclesDefenseMode();
        } else {
            generateObstaclesNormalMode(rand);
        }

        obstaclesGenerated = true;
    }

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

    private void generateObstaclesDefenseMode() {
        int centerCol = cols / 2;
        int centerRow = rows / 2;
        grid[centerRow][centerCol].setEntity(new Animal("Animal", 100));
        grid[centerRow + 1][centerCol].setEntity(new Current("Current"));
    }

    public void resetObstacles() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (grid[r][c].getEntity() instanceof LivingEntity ||
                        grid[r][c].getEntity() instanceof Current)
                    grid[r][c].setEntity(null);
        obstaclesGenerated = false;
    }

    public boolean areObstaclesGenerated() { return obstaclesGenerated; }

    /** Gets cell at Position. Returns null if invalid. */
    public UnderwaterCell getCell(Position pos) {
        return getCell(pos.getRow(), pos.getCol());
    }

    /** Gets cell at (row, col). Returns null if invalid. */
    public UnderwaterCell getCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return null;
        return grid[row][col].clone();
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public UnderwaterCell[][] getGrid() { return grid; }

    /** Copy constructor. */
    public SeaTrench(SeaTrench other) {
        this.rows = other.rows;
        this.cols = other.cols;
        this.obstaclesGenerated = other.obstaclesGenerated;
        this.grid = new UnderwaterCell[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                this.grid[r][c] = other.grid[r][c].clone();
    }
}
