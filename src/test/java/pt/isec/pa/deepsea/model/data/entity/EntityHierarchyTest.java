package pt.isec.pa.deepsea.model.data.entity;
import org.junit.jupiter.api.*;
import pt.isec.pa.deepsea.model.data.entity.artifact.Artifact;
import pt.isec.pa.deepsea.model.data.entity.mineral.Mineral;
import pt.isec.pa.deepsea.model.data.entity.monster.Monster;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Unit tests for the Entity class hierarchy (polymorphism, IDs, clone).
 *
 * @author Rodrigo Costa - 2024134442
 * @version v1.0.0 (Stage 1)
 */

@TestMethodOrder(MethodOrderer.DisplayName.class)
class EntityHierarchyTest {

    @BeforeEach
    void resetNextId() throws Exception {
        Field field = Entity.class.getDeclaredField("nextId");
        field.setAccessible(true);
        field.set(null, 1);
    }

    // Entity

    @Test
    void entity_idStartsAtIdStart() {
        Artifact a = new Artifact("X");
        assertEquals(1, a.getId());
    }

    @Test
    void entity_idsAreUniqueAndIncremental() {
        Artifact a1 = new Artifact("A");
        Artifact a2 = new Artifact("B");
        Mineral  m1 = new Mineral("C");

        assertTrue(a2.getId() > a1.getId());
        assertTrue(m1.getId() > a2.getId());
    }

    // Artifact

    @Test
    void artifact_copyConstructorCopiesName() {
        Artifact original = new Artifact("Amphora");
        Artifact copy     = new Artifact(original);
        assertEquals(original.getName(), copy.getName());
    }

    @Test
    void artifact_copyConstructorNewId() {
        Artifact original = new Artifact("Amphora");
        Artifact copy     = new Artifact(original);
        assertNotEquals(original.getId(), copy.getId());
    }

    @Test
    void artifact_cloneReturnsArtifact() {
        Artifact a = new Artifact("Urn");
        assertInstanceOf(Artifact.class, a.clone());
    }

    @Test
    void artifact_cloneDifferentId() {
        Artifact a = new Artifact("Urn");
        assertNotEquals(a.getId(), a.clone().getId());
    }

    // Mineral

    @Test
    void mineral_constructorSetsName() {
        Mineral m = new Mineral("Quartz");
        assertEquals("Quartz", m.getName());
    }

    @Test
    void mineral_copyConstructorCopiesName() {
        Mineral original = new Mineral("Quartz");
        Mineral copy     = new Mineral(original);
        assertEquals(original.getName(), copy.getName());
    }

    @Test
    void mineral_copyConstructorNewId() {
        Mineral original = new Mineral("Quartz");
        Mineral copy     = new Mineral(original);
        assertNotEquals(original.getId(), copy.getId());
    }

    @Test
    void mineral_cloneReturnsMineralInstance() {
        Mineral m = new Mineral("Obsidian");
        assertInstanceOf(Mineral.class, m.clone());
    }

    @Test
    void mineral_cloneSameName() {
        Mineral m = new Mineral("Obsidian");
        assertEquals(m.getName(), m.clone().getName());
    }

    @Test
    void mineral_cloneDifferentId() {
        Mineral m = new Mineral("Obsidian");
        assertNotEquals(m.getId(), m.clone().getId());
    }

    @Test
    void mineral_cloneDifferentReference() {
        Mineral m = new Mineral("Obsidian");
        assertNotSame(m, m.clone());
    }

    // Polymorphism — NonlivingEntity

    @Test
    void polymorphism_artifactIsNonlivingEntity() {
        Entity e = new Artifact("Relic");
        assertInstanceOf(NonlivingEntity.class, e);
    }

    @Test
    void polymorphism_mineralIsNonlivingEntity() {
        Entity e = new Mineral("Iron");
        assertInstanceOf(NonlivingEntity.class, e);
    }

    @Test
    void polymorphism_cloneNonlivingEntityCorrectType() {
        Entity artifact = new Artifact("Relic");
        Entity mineral  = new Mineral("Iron");

        assertInstanceOf(Artifact.class, artifact.clone());
        assertInstanceOf(Mineral.class, mineral.clone());
    }

    // Polymorphism — LivingEntity (Monster)

    @Test
    void polymorphism_monsterIsLivingEntity() {
        Entity e = new Monster("Kraken", 100);
        assertInstanceOf(LivingEntity.class, e);
    }

    @Test
    void polymorphism_monsterIsEntity() {
        Monster m = new Monster("Kraken", 100);
        assertInstanceOf(Entity.class, m);
    }

    @Test
    void polymorphism_cloneViaEntityReferenceReturnsMonster() {
        Entity e = new Monster("Kraken", 100);
        assertInstanceOf(Monster.class, e.clone());
    }
}