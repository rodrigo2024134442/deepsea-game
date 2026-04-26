package pt.isec.pa.deepsea.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeepSeaLogTest {

    @BeforeEach
    void setUp() {
        DeepSeaLog.getInstance().clear();
    }

    @Test
    void getInstance() {
        DeepSeaLog a = DeepSeaLog.getInstance();
        DeepSeaLog b = DeepSeaLog.getInstance();
        assertSame(a, b);
    }

    @Test
    void log() {
        DeepSeaLog log = DeepSeaLog.getInstance();
        log.log("Mensagem de teste");

        assertEquals(1, log.size());
        assertTrue(log.getMessages().get(0).contains("Mensagem de teste"));
        assertTrue(log.getMessages().get(0).startsWith("["));
    }

    @Test
    void clear() {
        DeepSeaLog log = DeepSeaLog.getInstance();
        log.log("msg1");
        log.log("msg2");
        assertEquals(2, log.size());

        log.clear();
        assertEquals(0, log.size());
    }

    @Test
    void saveToFile() {
        DeepSeaLog log = DeepSeaLog.getInstance();
        log.log("Mensagem para ficheiro");

        assertTrue(log.saveToFile());
    }

}