package pt.isec.pa.deepsea.model.data.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntitySubclassesTest {

    @Test
    void animalCloneKeepsNameAndNewId() {
        Animal animal = new Animal("Shark", 10);
        Animal clone = animal.clone();

        assertNotSame(animal, clone);
        assertEquals(animal.getName(), clone.getName());
        assertNotEquals(animal.getId(), clone.getId());
    }

    @Test
    void artifactCopyConstructorKeepsNameAndNewId() {
        Artifact original = new Artifact("Idol");
        Artifact copy = new Artifact(original);

        assertEquals(original.getName(), copy.getName());
        assertNotEquals(original.getId(), copy.getId());
    }

    @Test
    void currentCloneKeepsNameAndNewId() {
        Current current = new Current("Current");
        Current clone = current.clone();

        assertEquals(current.getName(), clone.getName());
        assertNotEquals(current.getId(), clone.getId());
    }

    @Test
    void mineralValueIsAtLeastOne() {
        Mineral mineral = new Mineral("Gold", -5);
        assertEquals(1, mineral.getValue());

        Mineral valued = new Mineral("Gold", 5);
        assertEquals(5, valued.getValue());
    }

    @Test
    void rockDefaultsToRockNameAndCloneNewId() {
        Rock rock = new Rock();
        Rock clone = rock.clone();

        assertEquals("Rock", rock.getName());
        assertEquals(rock.getName(), clone.getName());
        assertNotEquals(rock.getId(), clone.getId());
    }
}
