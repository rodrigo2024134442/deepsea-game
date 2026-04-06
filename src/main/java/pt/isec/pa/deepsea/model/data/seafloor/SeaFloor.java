package pt.isec.pa.deepsea.model.data.seafloor;

import pt.isec.pa.deepsea.model.data.cell.UnderwaterCell;
import pt.isec.pa.deepsea.model.data.entity.Entity;
import pt.isec.pa.deepsea.model.data.entity.artifact.Artifact;
import pt.isec.pa.deepsea.model.data.entity.mineral.Mineral;
import pt.isec.pa.deepsea.model.data.entity.monster.Monster;

import java.util.Random;
import static pt.isec.pa.deepsea.model.data.GameConfig.DEFENSE_MODE;

/**
 * Represents the sea floor grid of a surface zone.
 * Each position of the grid is represented by an underwater cell that may contain
 * minerals, artifacts, or monsters.
 * The sea floor supports both normal mode generation and defense mode generation.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
public class SeaFloor {
    private final UnderwaterCell[][] grid;
    private final int row;
    private final int col;

    /**
     * Creates a sea floor grid with the given dimensions.
     * The grid cells are initialized and the contents are generated according
     * to the current mode and the quantity of artifacts requested.
     *
     * @param rows number of rows in the sea floor grid
     * @param cols number of columns in the sea floor grid
     * @param quantity number of artifacts to generate
     */
    public SeaFloor(int rows, int cols, int quantity) {
        this.row = rows;
        this.col = cols;
        this.grid = new UnderwaterCell[row][col];

        createUnderwaterCells();

        if (DEFENSE_MODE) {
            generateDefenseMode();
        } else {
            generateMinerals(4);
        }

        if (quantity > 0) {
            generateArtifacts(quantity);
        }

    }

    /**
     * Initializes all cells of the sea floor grid.
     */
    private void createUnderwaterCells() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                grid[r][c] = new UnderwaterCell();
            }
        }
    }

    /**
     * Generates the fixed layout used in defense mode.
     * All cells become revealed, two minerals are placed in fixed positions,
     * and one monster is placed approximately in the center.
     */
    private void generateDefenseMode() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                grid[r][c].setRevealed(true);
            }
        }
        placeMineral(1, 1, 1);
        placeMineral(row - 2, col - 2, 100);
        placeMonster(row / 2, col / 2);
    }

    /**
     * Generates a given number of minerals in random empty cells.
     *
     * @param amount number of minerals to generate
     */
    private void generateMinerals(int amount) {
        Random randNum = new Random();
        int placed = 0;

        while (placed < amount) {
            int r = randNum.nextInt(row);
            int c = randNum.nextInt(col);

            if (isCellEmpty(r, c)) {
                int quantity = randNum.nextInt(5) + 1;
                grid[r][c].setEntity(new Mineral("Ouro"));
                placed++;
            }
        }
    }

    /**
     * Generates a given number of artifacts in random empty cells.
     *
     * @param amount number of artifacts to generate
     */
    private void generateArtifacts(int amount) {
        int placed = 0;
        Random randNum = new Random();

        while (placed < amount) {
            int r = randNum.nextInt(row);
            int c = randNum.nextInt(col);

            if (isCellEmpty(r, c)) {
                grid[r][c].setEntity(new Artifact("Tesouro"));
                placed++;
            }
        }
    }

    /**
     * Places a mineral in the given position if the target cell is valid and empty.
     *
     * @param r row of the target cell
     * @param c column of the target cell
     * @param quantity quantity associated with the mineral placement
     */
    private void placeMineral(int r, int c, int quantity) {
        if (!isValidPosition(r, c) || !isCellEmpty(r, c)) {
            return;
        }

        grid[r][c].setEntity(new Mineral("Ouro"));
    }

    /**
     * Places a monster in the given position if the target cell is valid and empty.
     *
     * @param r row of the target cell
     * @param c column of the target cell
     */
    private void placeMonster(int r, int c) {
        if (!isValidPosition(r, c) || !isCellEmpty(r, c)) {
            return;
        }

        grid[r][c].setEntity(new Monster("Monster", 100));
    }

    /**
     * Gets a cloned copy of the sea floor grid.
     *
     * @return cloned grid of underwater cells
     */
    public UnderwaterCell[][] getGrid() {
        UnderwaterCell[][] otherGrid = new UnderwaterCell[row][col];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                otherGrid[r][c] = grid[r][c].clone();
            }
        }

        return otherGrid;
    }

    /**
     * Gets the number of columns of the sea floor grid.
     *
     * @return number of columns
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the number of rows of the sea floor grid.
     *
     * @return number of rows
     */
    public int getRow() {
        return row;
    }

    /**
     * Checks whether the sea floor is being managed in defense mode.
     *
     * @return true if defense mode is active, false otherwise
     */
    public boolean isDefense() {
        return DEFENSE_MODE;
    }

    /**
     * Checks whether a given position is valid inside the grid.
     *
     * @param row row to validate
     * @param col column to validate
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < this.row && col >= 0 && col < this.col;
    }

    /**
     * Gets a cloned copy of the underwater cell at the given position.
     *
     * @param row row of the target cell
     * @param col column of the target cell
     * @return cloned underwater cell, or null if the position is invalid
     */
    public UnderwaterCell getCell(int row, int col) {
        if (!isValidPosition(row, col)) {
            return null;
        }
        return grid[row][col].clone();
    }

    /**
     * Places a non-living entity in a given cell if the position is valid.
     *
     * @param row row of the target cell
     * @param col column of the target cell
     * @param occupant non-living entity to place
     * @return true if the entity was placed successfully, false otherwise
     */
    public boolean setCellEntity(int row, int col, Entity occupant) {
        if (!isValidPosition(row, col)) {
            return false;
        }

        grid[row][col].setEntity(occupant);
        return true;
    }

    /**
     * Checks whether the given cell is empty.
     *
     * @param row row of the target cell
     * @param col column of the target cell
     * @return true if the cell has no entity, false otherwise
     */
    public boolean isCellEmpty(int row, int col) {
        if (!isValidPosition(row, col)) {
            return false;
        }

        return grid[row][col].getEntity() == null;
    }
}