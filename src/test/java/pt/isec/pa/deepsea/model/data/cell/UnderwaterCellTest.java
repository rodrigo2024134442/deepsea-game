package pt.isec.pa.deepsea.model.data.cell;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.entity.Entity;
import pt.isec.pa.deepsea.model.data.entity.Monster;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UnderwaterCell class.
 * These tests validate the basic behavior of empty cells,
 * revealed state changes, and entity storage.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */

class UnderwaterCellTest {

    /**
     * Verifies that a new underwater cell is created empty
     * and starts as unrevealed.
     */

    @Test
    void constructorDefaultTest() {
        UnderwaterCell cell = new UnderwaterCell();

        assertNull(cell.getEntity());
        assertFalse(cell.isRevealed());
    }

    /**
     * Verifies that an entity placed in the cell can be retrieved.
     */

    @Test
    void constructorWithEntityTest() {
        UnderwaterCell cell = new UnderwaterCell();
        Entity monster = new Monster("Monster", 100);

        cell.setEntity(monster);

        Entity entityFromCell = cell.getEntity();

        assertNotNull(entityFromCell);
        assertEquals(monster.getName(), entityFromCell.getName());
        assertNotSame(monster, entityFromCell);
    }
}
