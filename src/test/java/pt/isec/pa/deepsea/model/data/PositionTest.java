package pt.isec.pa.deepsea.model.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void translate() {
        Position p = new Position(3, 5);
        Position translated = p.translate(2, -1);

        assertEquals(3, p.getRow());
        assertEquals(5, p.getCol());

        assertEquals(5, translated.getRow());
        assertEquals(4, translated.getCol());
    }

    @Test
    void translateInPlace() {
        Position p = new Position(3, 5);
        p.translateInPlace(2, -1);

        assertEquals(5, p.getRow());
        assertEquals(4, p.getCol());
    }

    @Test
    void validatePosition() {
        Position p = new Position(3, 5);

        assertTrue(p.validatePosition(10, 10));

        assertFalse(p.validatePosition(2, 10));

        assertFalse(p.validatePosition(10, 4));

        Position negativa = new Position(-1, 5);
        assertFalse(negativa.validatePosition(10, 10));
    }
}