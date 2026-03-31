package pt.isec.pa.deepsea.model.data.seafloor;

import pt.isec.pa.deepsea.model.data.cell.UnderwaterCell;

public class SeaFloor {
    private UnderwaterCell[][] grid;
    private int row;
    private int col;

    public SeaFloor(int rows, int cols) {
        this.row = rows;
        this.col = cols;
        grid = new UnderwaterCell[row][col];
    }

    public UnderwaterCell[][] getGrid() {
        return grid;
    } //mudar isto para clone

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }


    public UnderwaterCell getCell(int row, int col){
        if(row<0 || row >= this.row ) return null;
        if(col <0|| col >= this.col) return null;
        return grid[row][col].clone();
    }

}
