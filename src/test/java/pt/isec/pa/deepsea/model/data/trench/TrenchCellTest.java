package pt.isec.pa.deepsea.model.data.trench;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrenchCellTest {

    @Test
    void testInitialType() {
        TrenchCell cell = new TrenchCell(TrenchCellType.ROCK);
        assertEquals(TrenchCellType.ROCK, cell.getType());
    }

    @Test
    void testSetType() {
        TrenchCell cell = new TrenchCell(TrenchCellType.EMPTY);
        cell.setType(TrenchCellType.ROCK);

        assertEquals(TrenchCellType.ROCK, cell.getType());
    }
}