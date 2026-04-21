package pt.isec.pa.deepsea.model.data.trench;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.cell.UnderwaterCell;
import pt.isec.pa.deepsea.model.data.entity.Rock;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SeaTrenchTest {

    @Test
    void testRocksNeverExceedHalfWidth() {
        SeaTrench trench = new SeaTrench(12, 6);
        trench.generate();

        for (int r = 0; r < trench.getRows(); r++) {
            long rocks = 0;
            for (int c = 0; c < trench.getCols(); c++) {
                UnderwaterCell cell = trench.getCell(r, c);
                if (cell.getEntity() instanceof Rock) rocks++;
            }
            assertTrue(rocks <= trench.getCols() / 2,
                    "Row " + r + " has too many rocks: " + rocks);
        }
    }

    @Test
    void testObstaclesGeneratedOnlyWhenActivated() {
        SeaTrench trench = new SeaTrench(12, 6);
        trench.generate();

        assertFalse(trench.areObstaclesGenerated());
        trench.generateObstacles(new Random());
        assertTrue(trench.areObstaclesGenerated());
    }

    @Test
    void testObstaclesResetCorrectly() {
        SeaTrench trench = new SeaTrench(12, 6);
        trench.generate();
        trench.generateObstacles(new Random());
        assertTrue(trench.areObstaclesGenerated());

        trench.resetObstacles();
        assertFalse(trench.areObstaclesGenerated());
    }

    @Test
    void testInvalidCellReturnsNull() {
        SeaTrench trench = new SeaTrench(12, 6);
        assertNull(trench.getCell(-1, 0));
        assertNull(trench.getCell(0, -1));
        assertNull(trench.getCell(12, 0));
    }
}