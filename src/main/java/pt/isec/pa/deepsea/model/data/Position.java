package pt.isec.pa.deepsea.model.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a 2D position with row and column coordinates.
 * Used throughout the game to avoid passing raw (x, y) pairs.
 *
 * @version v1.0.0
 */
public class Position implements Serializable {
    private static final long serialVersionUID = 1L;
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    /** Returns a new Position offset by the given deltas. */
    public Position translate(int dRow, int dCol) {
        return new Position(row + dRow, col + dCol);
    }
    /** Updates the current Position by applying the given deltas. */
    public void translateInPlace(int dRow, int dCol) {
        this.row += dRow;
        this.col += dCol;
    }

    public boolean validatePosition(int gridRow, int gridCol){
        if(row<0 || col<0 || gridRow>=row || gridCol>=col)
            return true;

        return false;
    }

    public Position clone(){
        return new Position (this.row,this.col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position p)) return false;
        return row == p.row && col == p.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}