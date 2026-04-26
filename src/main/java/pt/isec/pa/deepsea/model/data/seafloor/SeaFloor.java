package pt.isec.pa.deepsea.model.data.seafloor;

import pt.isec.pa.deepsea.model.data.Position;
import pt.isec.pa.deepsea.model.data.cell.UnderwaterCell;
import pt.isec.pa.deepsea.model.data.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import static pt.isec.pa.deepsea.model.data.GameConfig.DEFENSE_MODE;

/**
 * Represents the sea floor grid of a surface zone.
 * Each position may contain a mineral, artifact, monster, or be empty.
 *
 * @author João Carvalho - 2024126292
 * @version v1.1.0 (Position refactor)
 */
public class SeaFloor implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UnderwaterCell[][] grid;
    private final int rows;
    private final int cols;

    public SeaFloor(int rows, int cols, int quantity) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new UnderwaterCell[rows][cols];

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

    /** Copy constructor — deep copies all cells. */
    public SeaFloor(SeaFloor other) {
        this.rows = other.rows;
        this.cols = other.cols;
        this.grid = new UnderwaterCell[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                this.grid[r][c] = other.grid[r][c].clone();
    }

    private void createUnderwaterCells() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new UnderwaterCell();
    }

    private void generateDefenseMode() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c].setRevealed(true);
        placeMineral(new Position(1, 1));
        placeMineral(new Position(rows - 2, cols - 2));
        placeMonster(new Position(rows / 2, cols / 2));
    }

    private void generateMinerals(int amount) {
        Random rand = new Random();
        int placed = 0;
        while (placed < amount) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (isCellEmpty(r, c)) {
                grid[r][c].setEntity(new Mineral("Ouro"));
                placed++;
            }
        }
    }

    private void generateArtifacts(int amount) {
        Random rand = new Random();
        int placed = 0;
        while (placed < amount) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (isCellEmpty(r, c)) {
                grid[r][c].setEntity(new Artifact("Tesouro"));
                placed++;
            }
        }
    }

    private void placeMineral(Position pos) {
        if (!isValidPosition(pos) || !isCellEmpty(pos)) return;
        grid[pos.getRow()][pos.getCol()].setEntity(new Mineral("Ouro"));
    }

    private void placeMonster(Position pos) {
        if (!isValidPosition(pos) || !isCellEmpty(pos)) return;
        grid[pos.getRow()][pos.getCol()].setEntity(new Monster("Monster", 100));
    }

    /** Returns a deep-copied grid. */
    public UnderwaterCell[][] getGrid() {
        UnderwaterCell[][] copy = new UnderwaterCell[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                copy[r][c] = grid[r][c].clone();
        return copy;
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean isDefense() { return DEFENSE_MODE; }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean isValidPosition(Position pos) {
        return isValidPosition(pos.getRow(), pos.getCol());
    }

    /** Gets a cloned cell at (row, col). Returns null if invalid. */
    public UnderwaterCell getCell(int row, int col) {
        if (!isValidPosition(row, col)) return null;
        return grid[row][col].clone();
    }

    /** Gets a cloned cell at Position. Returns null if invalid. */
    public UnderwaterCell getCell(Position pos) {
        return getCell(pos.getRow(), pos.getCol());
    }

    public boolean setCellEntity(int row, int col, Entity occupant) {
        if (!isValidPosition(row, col)) return false;
        grid[row][col].setEntity(occupant);
        return true;
    }

    public boolean setCellEntity(Position pos, Entity occupant) {
        return setCellEntity(pos.getRow(), pos.getCol(), occupant);
    }

    public boolean isCellEmpty(int row, int col) {
        if (!isValidPosition(row, col)) return false;
        return grid[row][col].getEntity() == null;
    }

    public boolean isCellEmpty(Position pos) {
        return isCellEmpty(pos.getRow(), pos.getCol());
    }

    public boolean revealCell(Position pos) {
        if (!isValidPosition(pos)) return false;
        grid[pos.getRow()][pos.getCol()].setRevealed(true);
        return true;
    }

    public boolean hasArtifact(Position pos) {
        if (!isValidPosition(pos)) return false;
        return grid[pos.getRow()][pos.getCol()].getEntity() instanceof Artifact;
    }

    public boolean hasMineral(Position pos) {
        if (!isValidPosition(pos)) return false;
        return grid[pos.getRow()][pos.getCol()].getEntity() instanceof Mineral;
    }

    public boolean hasMonster(Position pos) {
        if (!isValidPosition(pos)) return false;
        return grid[pos.getRow()][pos.getCol()].getEntity() instanceof Monster;
    }

    public Entity removeEntity(Position pos) {
        if (!isValidPosition(pos)) return null;

        Entity entity = grid[pos.getRow()][pos.getCol()].getEntity();
        grid[pos.getRow()][pos.getCol()].setEntity(null);
        return entity;
    }

    public void placeArtifacts(List<Artifact> listArtifacts) {
        Random rand = new Random();

        for (Artifact a : listArtifacts) {
            boolean placed = false;

            while (!placed) {
                int r = rand.nextInt(rows);
                int c = rand.nextInt(cols);

                if (isCellEmpty(r, c)) {
                    grid[r][c].setEntity(a);
                    placed = true;
                }
            }
        }
    }

    public void placeMinerals(List<Mineral> listMinerals) {
        Random rand = new Random();

        for (Mineral m : listMinerals) {
            boolean placed = false;

            while (!placed) {
                int r = rand.nextInt(rows);
                int c = rand.nextInt(cols);

                if (isCellEmpty(r, c)) {
                    grid[r][c].setEntity(m);
                    placed = true;
                }
            }
        }
    }

}
