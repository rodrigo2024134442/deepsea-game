package pt.isec.pa.deepsea.model.data.trench;

import java.util.Random;

/**
 * The SeaTrench class represents the grid structure of a marine trench
 * used during the descent and ascent phases of the "Deep Sea Mining" game.
 *
 * <p>This class is responsible for creating and managing a bidimensional
 * grid of {@link TrenchCell} elements, which model the internal structure
 * of a trench, including rock formations and navigable paths.</p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Store the dimensions of the trench grid</li>
 *   <li>Maintain a 2D structure of {@link TrenchCell}</li>
 *   <li>Generate the trench layout with rock boundaries and empty paths</li>
 *   <li>Provide access to grid data for consultation</li>
 * </ul>
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 1)
 */
public class SeaTrench {
    private int rows;
    private int cols;
    private TrenchCell[][] grid;

    public SeaTrench(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new TrenchCell[rows][cols];
    }

    public void generate() {
        Random rand = new Random();
        int left = cols / 4;
        int right = cols / 4;

        for (int i = 0; i < rows; i++) {

            // variar entre -1, 0, +1
            left += rand.nextInt(3) - 1;
            right += rand.nextInt(3) - 1;

            // garantir limites mínimos
            left = Math.max(1, left);
            right = Math.max(1, right);

            // garantir regra dos 50%
            if (left + right > cols / 2) {
                right = (cols / 2) - left;
            }

            for (int j = 0; j < cols; j++) {
                grid[i][j] = ( (j < left) || (j >= cols - right) ) ? new TrenchCell(TrenchCellType.ROCK) : new TrenchCell(TrenchCellType.EMPTY);
            }
        }
    }

    /* GETTERS */
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public TrenchCell[][] getGrid() {
        return grid;
    }

    public TrenchCell getCell(int row, int col){
        if(row < 0 || row >= rows || col < 0 || col >= cols)
            return null;
        return grid[row][col];
    }
}
