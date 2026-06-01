package pt.isec.pa.deepsea.model.data.drone;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroneComparatorTest {

    @Test
    void compareByFuelOrdersHighestFirst() {
        Drone a = new Drone();
        Drone b = new Drone();

        a.consumeFuel(20); // 80
        b.consumeFuel(50); // 50

        DroneComparator comparator = new DroneComparator(DroneComparator.Mode.FUEL);

        assertTrue(comparator.compare(a, b) < 0);
        assertTrue(comparator.compare(b, a) > 0);
    }

    @Test
    void compareByIntegrityOrdersHighestFirst() {
        Drone a = new Drone();
        Drone b = new Drone();

        a.takeDamage(); // 99

        DroneComparator comparator = new DroneComparator(DroneComparator.Mode.INTEGRITY);

        assertTrue(comparator.compare(b, a) < 0);
        assertTrue(comparator.compare(a, b) > 0);
    }
}
