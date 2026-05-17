package pt.isec.pa.deepsea.model.data.drone;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator class for ordering {@link Drone} instances either by current
 * fuel or by hull integrity. In both cases drones with higher values come
 * first.
 *
 * <p>Implements {@link Serializable} so it can be safely used inside other
 * serializable model classes.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0
 */
public class DroneComparator implements Comparator<Drone>, Serializable {
    private static final long serialVersionUID = 1L;

    /** Available comparison criteria. */
    public enum Mode { FUEL, INTEGRITY }

    private final Mode mode;

    /**
     * Creates a new drone comparator for the given criterion.
     *
     * @param mode {@code FUEL} to compare by current fuel,
     *             {@code INTEGRITY} to compare by hull integrity
     */
    public DroneComparator(Mode mode) {
        this.mode = mode;
    }

    /**
     * Compares two drones using the configured mode. Higher values come first.
     *
     * @param a first drone
     * @param b second drone
     * @return negative if {@code a} should come before {@code b},
     *         positive if it should come after, zero if equal
     */
    @Override
    public int compare(Drone a, Drone b) {
        int valueA;
        int valueB;
        switch (mode) {
            case FUEL -> {
                valueA = a.getCurrentFuel();
                valueB = b.getCurrentFuel();
            }
            case INTEGRITY -> {
                valueA = a.getHullIntegrity();
                valueB = b.getHullIntegrity();
            }
            default -> {
                return 0;
            }
        }
        return Integer.compare(valueB, valueA); // highest first
    }
}