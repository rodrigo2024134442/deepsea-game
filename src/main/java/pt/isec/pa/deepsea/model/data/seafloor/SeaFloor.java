package pt.isec.pa.deepsea.model.data.seafloor;

import pt.isec.pa.deepsea.model.data.cell.UnderwaterCell;
import pt.isec.pa.deepsea.model.data.entity.NonlivingEntity;
import pt.isec.pa.deepsea.model.data.entity.artifact.Artifact;
import pt.isec.pa.deepsea.model.data.entity.mineral.Mineral;
import pt.isec.pa.deepsea.model.data.entity.monster.Monster;

import java.util.Random;
import static pt.isec.pa.deepsea.model.data.GameConfig.DEFENSE_MODE;

public class SeaFloor {
    private final UnderwaterCell[][] grid;
    private final int row;
    private final int col;

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

    private void createUnderwaterCells() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                grid[r][c] = new UnderwaterCell();
            }
        }
    }

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

    private void generateMinerals(int amount) {
        Random randNum = new Random();
        int placed = 0;

        while (placed < amount) {
            int r = randNum.nextInt(row);
            int c = randNum.nextInt(col);

            if (isCellEmpty(r, c)) {
                int quantity = randNum.nextInt(5) + 1;
                //grid[r][c].setEntity(new Mineral());
                placed++;
            }
        }
    }

    private void generateArtifacts(int amount) {
        int placed = 0;
        Random randNum = new Random();

        while (placed < amount) {
            int r = randNum.nextInt(row);
            int c = randNum.nextInt(col);

            if (isCellEmpty(r, c)) {
                //grid[r][c].setEntity(new Artifact());
                placed++;
            }
        }
    }

    //METER QUANTITY NO MINERAL
    private void placeMineral(int r, int c, int quantity) {
        if (!isValidPosition(r, c) || !isCellEmpty(r, c)) {
            return;
        }

        //grid[r][c].setEntity(new Mineral());
    }

    private void placeMonster(int r, int c) {
        if (!isValidPosition(r, c) || !isCellEmpty(r, c)) {
            return;
        }

        grid[r][c].setEntity(new Monster("Monster", 100));
    }



    public UnderwaterCell[][] getGrid() {
        UnderwaterCell[][] otherGrid = new UnderwaterCell[row][col];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                otherGrid[r][c] = grid[r][c].clone();
            }
        }

        return otherGrid;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean isDefense() {
        return DEFENSE_MODE;
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < this.row && col >= 0 && col < this.col;
    }

    public UnderwaterCell getCell(int row, int col) {
        if (!isValidPosition(row, col)) {
            return null;
        }
        return grid[row][col].clone();
    }

    public boolean setCellEntity(int row, int col, NonlivingEntity occupant) {
        if (!isValidPosition(row, col)) {
            return false;
        }

        grid[row][col].setEntity(occupant);
        return true;
    }

    public boolean isCellEmpty(int row, int col) {
        if (!isValidPosition(row, col)) {
            return false;
        }

        return grid[row][col].getEntity() == null;
    }
}