package pt.isec.pa.deepsea.model.data.entity;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.entity.monster.Monster;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the basic behavior inherited from LivingEntity,
 * using the Monster class as a concrete subclass.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
class LivingEntityTest {

    /**
     * Verifies that a living entity stores and returns its health correctly.
     */
    @Test
    void livingEntityHealthVerification() {
        LivingEntity entity = new Monster("Monster", 100);

        assertEquals(100, entity.getHealth());
    }

    /**
     * Verifies that a living entity is considered dead when health is zero.
     */
    @Test
    void livingEntityDeadVerification() {
        LivingEntity entity = new Monster("Monster", 100);

        entity.setHealth(0);

        assertFalse(entity.isAlive());
    }
}