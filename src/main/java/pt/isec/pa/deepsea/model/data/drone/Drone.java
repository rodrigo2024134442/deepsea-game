package pt.isec.pa.deepsea.model.data.drone;

import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.Position;
import pt.isec.pa.deepsea.model.data.entity.Artifact;
import pt.isec.pa.deepsea.model.data.entity.Mineral;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a submarine exploration drone in the Deep Sea Mining game.
 *
 * <p>Each drone manages its own fuel, hull integrity, position, state and
 * collected resources. Hull damage follows a prime number sequence
 * (1, 2, 3, 5, 7, ...) that resets at the start of each expedition.</p>
 *
 * <p>Drones are uniquely identified by an auto-incremented integer ID,
 * which allows them to be correctly managed in a {@link java.util.Set}.</p>
 *
 * <p>For ordering drones by fuel or hull integrity see {@link DroneComparator}.</p>
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.2.0
 */
public class Drone implements GameConfig, Serializable {
    private static final long serialVersionUID = 1L;
    private final int id;
    private static int nextId = 1;

    // FUEL MANAGEMENT
    private int currentFuel;
    private final int maxFuel = DRONE_MAX_FUEL;

    // HULL INTEGRITY
    private int hullIntegrity;
    private final int maxHullIntegrity = DRONE_MAX_INTEGRITY;

    // PRIME SEQUENCE FOR DAMAGE
    private static final int[] PRIMES = {1, 2, 3, 5, 7, 11, 13, 17, 19, 23};
    private int primeIndex;

    // POSITION
    private Position position;

    // STATE
    private DroneState state;

    // RESOURCES
    private List<Artifact> collectedArtifacts;
    private List<Mineral> collectedMinerals;
    private int minerals;

    /**
     * Creates a new drone with full fuel, full hull integrity,
     * no position (on ship) and an auto-assigned unique ID.
     */
    public Drone() {
        this.id = nextId++;
        this.currentFuel = maxFuel;
        this.hullIntegrity = maxHullIntegrity;
        this.primeIndex = 0;
        this.state = DroneState.ON_SHIP;
        this.position = null; // no position while on ship
        this.collectedArtifacts = new ArrayList<>();
        this.collectedMinerals = new ArrayList<>();
        this.minerals = 0;
    }

    /** Consumes fuel. Ignores values <= 0. Fuel cannot go below zero. */
    public void consumeFuel(int amount) {
        if (amount <= 0) return;
        currentFuel = Math.max(0, currentFuel - amount);
    }

    /** Refuels up to max capacity. Ignores values <= 0. */
    public void refuel(int amount) {
        if (amount <= 0) return;
        currentFuel = Math.min(currentFuel + amount, maxFuel);
    }

    /** Returns true if fuel is zero. */
    public boolean isOutOfFuel() {
        return currentFuel == 0;
    }

    /** Returns true if fuel is enough for one movement. */
    public boolean hasEnoughFuelForMovement() {
        return currentFuel >= FUEL_COST_DRONE_MOVE;
    }

    /**
     * Applies damage following the prime sequence (1, 2, 3, 5, 7, ...).
     * Hull cannot go below zero.
     */
    public void takeDamage() {
        int damage = PRIMES[Math.min(primeIndex, PRIMES.length - 1)];
        primeIndex++;
        hullIntegrity = Math.max(0, hullIntegrity - damage);
    }

    public void destroy() {
        hullIntegrity = 0;
    }

    /** Resets the prime damage sequence. Call at the start of each expedition. */
    public void resetPrimeSequence() {
        primeIndex = 0;
    }

    /** Fully restores hull integrity. */
    public void repair() {
        hullIntegrity = maxHullIntegrity;
    }

    /** Returns true if hull integrity reached zero. */
    public boolean isDestroyed() {
        return hullIntegrity == 0;
    }

    /** Sets the drone's position. */
    public void setPosition(Position position) {
        this.position = position;
    }

    /** Sets the drone's position by row and col. */
    public void setPosition(int row, int col) {
        this.position = new Position(row, col);
    }

    /** Reset position to null. */
    public void clearPosition() {
        this.position = null;
    }

    /** Gets the drone's current position (null if on ship). */
    public Position getPosition() {
        return position;
    }

    /** Places drone at top center of trench and sets state to DESCENDING. */
    public void placeOnTrench(int topRow, int pitCols) {
        setPosition(topRow, pitCols / 2);
        if (topRow == 1)
            setState(DroneState.DESCENDING);
        else
            setState(DroneState.ASCENDING);
    }

    /** Places drone at bottom center of trench and sets state to ASCENDING. */
    public void placeForAscent(int pitCols, int pitRows) {
        setPosition(pitRows - 1, pitCols / 2);
        setState(DroneState.ASCENDING);
    }

    /** Places drone at center of sea floor and sets state to ON_SEABED. */
    public void placeOnSeabed(int seabedCols, int seabedRows) {
        setPosition(seabedRows / 2, seabedCols / 2);
        setState(DroneState.ON_SEABED);
    }

    /** Places drone on ship. */
    public void placeOnShip() {
        setState(DroneState.ON_SHIP);
    }

    /** Adds a collected artifact. Ignores null. */
    public void addArtifact(Artifact artifact) {
        if (artifact != null) collectedArtifacts.add(artifact);
    }

    /** Adds a collected mineral. Ignores null. */
    public void addMineral(Mineral mineral) {
        if (mineral != null) {
            collectedMinerals.add(mineral);
            Random rand = new Random();
            this.minerals += rand.nextInt(MAX_MINERAL_VALUE) + 1;
        }
    }

    /** Unloads all collected artifacts. */
    public void unloadArtifacts() {
        collectedArtifacts.clear();
    }

    /** Unloads all collected minerals. */
    public void unloadMinerals() {
        collectedMinerals.clear();
    }

    /** Sets the current state. */
    public void setState(DroneState state) { this.state = state; }

    /** @return unique identifier */
    public int getId() { return id; }

    /** @return current fuel */
    public int getCurrentFuel() { return currentFuel; }

    /** @return true if fuel is at max capacity */
    public boolean isFuelFull() {
        return currentFuel >= maxFuel;
    }

    /** @return max fuel */
    public int getMaxFuel() { return maxFuel; }

    /** @return current hull integrity */
    public int getHullIntegrity() { return hullIntegrity; }

    /** @return max hull integrity */
    public int getMaxHullIntegrity() { return maxHullIntegrity; }

    /** @return true if hull is below max integrity */
    public boolean needsRepair() {
        return hullIntegrity < maxHullIntegrity;
    }

    /** @return current state */
    public DroneState getState() { return state; }

    /** @return unmodifiable list of collected artifacts */
    public List<Artifact> getCollectedArtifacts() { return List.copyOf(collectedArtifacts); }

    /** @return unmodifiable list of collected minerals */
    public List<Mineral> getCollectedMinerals() { return List.copyOf(collectedMinerals); }

    /** @return current minerals value */
    public int getMinerals() {
        return minerals;
    }

    /** Sets the minerals value. */
    public void setMinerals(int mineral) {
        minerals = mineral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drone d)) return false;
        return id == d.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Drone[id=" + id +
                ", fuel=" + currentFuel + "/" + maxFuel +
                ", integrity=" + hullIntegrity + "/" + maxHullIntegrity +
                ", state=" + state +
                ", pos=" + position + "]";
    }
}