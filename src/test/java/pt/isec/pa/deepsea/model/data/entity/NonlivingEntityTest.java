package pt.isec.pa.deepsea.model.data.entity;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.entity.artifact.Artifact;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the basic behavior inherited from NonlivingEntity,
 * using the Artifact class as a concrete subclass.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
class NonlivingEntityTest {

    /**
     * Verifies that a non-living entity stores its name correctly.
     */
    @Test
    void nonlivingEntityNameVerification() {
        NonlivingEntity entity = new Artifact("Artifact");

        assertEquals("Artifact", entity.getName());
    }

    /**
     * Verifies that cloning a non-living entity creates a different object
     * with the same name.
     */
    @Test
    void nonlivingEntityCloneVerification() {
        NonlivingEntity entity = new Artifact("Artifact");
        NonlivingEntity clone = entity.clone();

        assertNotNull(clone);
        assertNotSame(entity, clone);
        assertEquals(entity.getName(), clone.getName());
    }
}