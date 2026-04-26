package pt.isec.pa.deepsea.model.data.entity.monster;

import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.entity.Monster;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Monster class.
 *
 * @author João Carvalho - 2024126292
 * @author Rodrigo Costa - 2024134442 added clone amd copy constructor test
 * @version v1.0.0 (Stage 1)
 */
class MonsterTest {

    /**
     * Verifies that a monster returns its predefined sound.
     */
    @Test
    void monsterSoundCheck() {
        Monster monster = new Monster("Kraken", 100);

        assertEquals("Kraken",monster.getName());
        assertEquals("Roar!!! Coff...Coff...", monster.talk());
    }

    @Test
    void monster_copyConstructorNewId() {
        Monster original = new Monster("Kraken", 100);
        Monster copy = new Monster(original);

        assertNotEquals(original.getId(), copy.getId());
    }

    @Test
    void monster_cloneReturnsMonsterInstance() {
        Monster m = new Monster("Kraken", 100);
        assertInstanceOf(Monster.class, m.clone());
    }

    @Test
    void monster_cloneDifferentReference() {
        Monster m = new Monster("Kraken", 100);
        assertNotSame(m, m.clone());
    }


    @Test
    void monster_cloneDifferentId() {
        Monster m = new Monster("Kraken", 100);
        assertNotEquals(m.getId(), m.clone().getId());
    }
}