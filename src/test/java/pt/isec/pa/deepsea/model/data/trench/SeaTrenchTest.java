package pt.isec.pa.deepsea.model.data.trench;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeaTrenchTest {

    @Test
    void testGenerateCreatesGrid() {
        SeaTrench trench = new SeaTrench(10, 10);
        trench.generate();

        assertNotNull(trench.getGrid());
        assertEquals(10, trench.getRows());
        assertEquals(10, trench.getCols());
    }

    @Test
    void testCellsAreNotNullAfterGenerate() {
        SeaTrench trench = new SeaTrench(5, 5);
        trench.generate();

        for(int i = 0; i < trench.getRows(); i++){
            for(int j = 0; j < trench.getCols(); j++){
                assertNotNull(trench.getCell(i,j));
            }
        }
    }
}