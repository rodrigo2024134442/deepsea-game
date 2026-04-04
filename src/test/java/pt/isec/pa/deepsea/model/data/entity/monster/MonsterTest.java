package pt.isec.pa.deepsea.model.data.entity.monster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Monster class.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
class MonsterTest {

    /**
     * Verifies that a monster stores the given name and health correctly.
     */
    @Test
    void monsterNameVerification() {
        Monster monster = new Monster("Kraken", 100);

        assertEquals("Kraken", monster.getName());
        assertEquals(100, monster.getHealth());
    }

    /**
     * Verifies that a monster returns its predefined sound.
     */
    @Test
    void monsterSoundCheck() {
        Monster monster = new Monster("Kraken", 100);

        assertEquals("Kraken",monster.getName());
        assertEquals("Roar!!! Coff...Coff...", monster.talk());
    }
}