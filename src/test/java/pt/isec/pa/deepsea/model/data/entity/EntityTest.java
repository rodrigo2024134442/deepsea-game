package pt.isec.pa.deepsea.model.data.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Entity base behavior,
 * using an existing concrete subclass.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
class EntityTest {

    /**
     * Verifies that the entity stores the name correctly.
     */
    @Test
    void entityNameCorrect() {
        Entity entity = new Monster("Monster", 100);

        assertEquals("Monster", entity.getName());
    }

    /**
     * Verifies that two entities receive different IDs.
     */
    @Test
    void entityCreateDifferentIds() {
        Entity entity1 = new Monster("Monster1", 100);
        Entity entity2 = new Monster("Monster2", 100);

        assertNotEquals(entity1.getId(), entity2.getId());
    }
}