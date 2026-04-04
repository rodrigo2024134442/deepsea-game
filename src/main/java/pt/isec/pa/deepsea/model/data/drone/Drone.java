package pt.isec.pa.deepsea.model.data.drone;

import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.entity.artifact.Artifact;
import pt.isec.pa.deepsea.model.data.entity.mineral.Mineral;

import java.util.ArrayList;
import java.util.List;

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
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 1)
 */
public class Drone implements GameConfig {
    private final int id;
    private static int nextId = 1;

    // FUEL MANAGEMENT
    private int currentFuel;
    private final int maxFuel = DRONE_MAX_FUEL;

    // HULL INTEGRITY (casco)
    private int hullIntegrity;
    private final int maxHullIntegrity = DRONE_MAX_INTEGRITY;

    // PRIME SEQUENCE FOR DAMAGE
    private static final int[] PRIMES = {1, 2, 3, 5, 7, 11, 13, 17, 19, 23};
    private int primeIndex;

    // GRID POSITION
    private int posX;
    private int posY;

    // WHERE IS THE DRONE
    private DroneState state;

    // RESOURCES COLLECTION
    private List<Artifact> collectedArtifacts;
    private List<Mineral> collectedMinerals;

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
        this.posX = -1;
        this.posY = -1;

        this.collectedArtifacts = new ArrayList<>();
        this.collectedMinerals = new ArrayList<>();
    }

    /**
     * Consumes the given amount of fuel. Ignores values <= 0.
     * Fuel cannot go below zero.
     *
     * @param amount amount of fuel to consume
     */
    public void consumeFuel(int amount) {
        if (amount <= 0) return;
        currentFuel -= amount;
        if (currentFuel < 0) currentFuel = 0;
    }

    /**
     * Refuels the drone by the given amount, up to the maximum capacity.
     * Ignores values <= 0.
     *
     * @param amount amount of fuel to add
     */
    public void refuel(int amount) {
        if (amount <= 0) return;
        currentFuel = Math.min(currentFuel + amount, maxFuel);
    }

    /**
     * Checks whether the drone has run out of fuel.
     *
     * @return true if current fuel is zero
     */
    public boolean isOutOfFuel() {
        return currentFuel == 0;
    }

    /**
     * Applies damage to the hull following the prime number sequence.
     * Each consecutive call uses the next prime (1, 2, 3, 5, 7, ...).
     * Hull integrity cannot go below zero.
     */
    public void takeDamage() {
        int damage = PRIMES[Math.min(primeIndex, PRIMES.length - 1)];
        primeIndex++;
        hullIntegrity = Math.max(0, hullIntegrity - damage);
    }

    /**
     * Resets the prime damage sequence to the beginning.
     * Should be called at the start of each new expedition to the sea floor.
     */
    public void resetPrimeSequence() {
        primeIndex = 0;
    }

    /**
     * Fully restores the hull integrity to its maximum value.
     * Used when the drone is repaired at the ship's workshop.
     */
    public void repair() {
        hullIntegrity = maxHullIntegrity;
    }

    /**
     * Checks whether the drone has been destroyed.
     *
     * @return true if hull integrity has reached zero
     */
    public boolean isDestroyed() {
        return hullIntegrity == 0;
    }

    /**
     * Sets the drone's position on the current grid.
     *
     * @param x column position
     * @param y row position
     */
    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * Places the drone at the top center of the trench and sets state to DESCENDING.
     * Used at the start of a descent expedition.
     *
     * @param pitCols number of columns in the trench grid
     */
    public void placeForDescent(int pitCols) {
        setPosition(pitCols / 2, 0);
        setState(DroneState.DESCENDING);
    }

    /**
     * Places the drone at the bottom center of the trench and sets state to ASCENDING.
     * Used at the start of an ascent.
     *
     * @param pitCols number of columns in the trench grid
     * @param pitRows number of rows in the trench grid
     */
    public void placeForAscent(int pitCols, int pitRows) {
        setPosition(pitCols / 2, pitRows - 1);
        setState(DroneState.ASCENDING);
    }

    /**
     * Places the drone approximately at the center of the sea floor grid
     * and sets state to ON_SEABED.
     *
     * @param seabedCols number of columns in the sea floor grid
     * @param seabedRows number of rows in the sea floor grid
     */
    public void placeOnSeabed(int seabedCols, int seabedRows) {
        setPosition(seabedCols / 2, seabedRows / 2);
        setState(DroneState.ON_SEABED);
    }

    /**
     * Adds a collected artifact to the drone's cargo.
     * Ignores null values.
     *
     * @param artifact artifact to add
     */
    public void addArtifact(Artifact artifact) {
        if (artifact != null) collectedArtifacts.add(artifact);
    }

    /**
     * Adds a collected mineral to the drone's cargo.
     * Ignores null values.
     *
     * @param mineral mineral to add
     */
    public void addMineral(Mineral mineral) {
        if (mineral != null) collectedMinerals.add(mineral);
    }

    /**
     * Unloads all resources from the drone and returns the collected artifacts.
     * Both artifact and mineral lists are cleared after unloading.
     * Used when returning to the ship or when the drone is lost.
     *
     * @return list of artifacts that were carried
     */
    public List<Artifact> unloadResources() {
        List<Artifact> artifacts = new ArrayList<>(collectedArtifacts);
        collectedArtifacts.clear();
        collectedMinerals.clear();
        return artifacts;
    }

    /**
     * Sets the current state of the drone.
     *
     * @param state new drone state
     */
    public void setState(DroneState state) {
        this.state = state;
    }

    /** @return unique identifier of this drone */
    public int getId() { return id; }

    /** @return current fuel level */
    public int getCurrentFuel() { return currentFuel; }

    /** @return maximum fuel capacity */
    public int getMaxFuel() { return maxFuel; }

    /** @return current hull integrity */
    public int getHullIntegrity() { return hullIntegrity; }

    /** @return maximum hull integrity */
    public int getMaxHullIntegrity() { return maxHullIntegrity; }

    /** @return current column position (-1 if on ship) */
    public int getPosX() { return posX; }

    /** @return current row position (-1 if on ship) */
    public int getPosY() { return posY; }

    /** @return current drone state */
    public DroneState getState() { return state; }

    /** @return unmodifiable list of collected artifacts */
    public List<Artifact> getCollectedArtifacts() { return List.copyOf(collectedArtifacts); }

    /** @return unmodifiable list of collected minerals */
    public List<Mineral> getCollectedMinerals() { return List.copyOf(collectedMinerals); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drone)) return false;
        return id == ((Drone) o).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Drone[fuel=" + currentFuel + "/" + maxFuel +
                ", integrity=" + hullIntegrity + "/" + maxHullIntegrity +
                ", state=" + state +
                ", pos=(" + posX + "," + posY + ")]";
    }
}