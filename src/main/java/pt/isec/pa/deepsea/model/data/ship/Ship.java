package pt.isec.pa.deepsea.model.data.ship;

import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.Position;
import pt.isec.pa.deepsea.model.data.drone.Drone;
import pt.isec.pa.deepsea.model.data.drone.DroneComparator;
import pt.isec.pa.deepsea.model.data.entity.Artifact;
import pt.isec.pa.deepsea.model.data.entity.Mineral;

import java.io.Serializable;
import java.util.*;

import static pt.isec.pa.deepsea.model.data.GameConfig.FUEL_COST_SHIP_MOVE;

/**
 * Represents the ship in the Deep Sea Mining game.
 * The ship is the main base of operations, managing fuel, position,
 * drones, and the player's collected resources.
 *
 * <p>No internal references are exposed directly — all getters return
 * copies or unmodifiable views to preserve encapsulation.</p>
 *
 * @author Beja N2024134442
 * @version v1.2.0
 */
public class Ship implements Serializable {
    private static final long serialVersionUID = 1L;
    private int currentFuel;
    private Position position;
    private int maxFuel;
    private final Set<Drone> drones;
    private List<Artifact> collectedArtifacts;
    private List<Mineral> collectedMinerals;
    private int minerals;

    /** Creates a ship at (-1,-1) with full fuel and the configured number of drones. */
    public Ship() {
        this.currentFuel = GameConfig.SHIP_MAX_FUEL;
        this.maxFuel = GameConfig.SHIP_MAX_FUEL;
        this.position = new Position(-1, -1);
        this.drones = new HashSet<>();
        this.collectedArtifacts = new ArrayList<>();
        this.collectedMinerals = new ArrayList<>();
        this.minerals = 0;

        // start with configured initial number of drones
        int start = Math.max(0, Math.min(GameConfig.START_DRONES, GameConfig.MAX_DRONES));
        for (int i = 0; i < start; i++) {
            drones.add(new Drone());
        }
    }

    /** Consumes fuel. Ignores values <= 0. Fuel cannot go below zero. */
    public void consumeFuel(int amount) {
        if (amount <= 0) return;
        currentFuel = Math.max(0, currentFuel - amount);
    }

    /** Adds an artifact to the ship's collection. Ignores null. */
    public void addArtifact(Artifact artifact) {
        if (artifact != null) collectedArtifacts.add(artifact);
    }

    /** Adds a mineral to the ship's collection. Ignores null. */
    public void addMineral(Mineral mineral) {
        if (mineral != null) collectedMinerals.add(mineral);
    }

    /** Sets ship position. */
    public void setPosition(int row, int col) {
        this.position = new Position(row, col);
    }

    /** Sets ship position. */
    public void setPosition(Position position) {
        this.position = position;
    }

    /** Sets ship fuel. */
    public void setCurrentFuel(int currentFuel) {
        this.currentFuel = currentFuel;
    }

    /** Returns true if fuel is empty. */
    public boolean isOutOfFuel() { return currentFuel <= 0; }

    /** Returns true if fuel is enough for one ship movement. */
    public boolean hasEnoughFuelForMovement() { return currentFuel >= FUEL_COST_SHIP_MOVE; }

    /** @return current fuel */
    public int getCurrentFuel() { return currentFuel; }

    /** @return max fuel (instance, upgradeable) */
    public int getMaxFuel() { return maxFuel; }

    /** Upgrade ship max fuel by given units. */
    public void upgradeMaxFuel(int units) {
        if (units <= 0) return;
        this.maxFuel += units;
        // if current fuel was at previous max, keep ship topped to new max
        this.currentFuel = Math.min(this.currentFuel, this.maxFuel);
    }

    /** Adds a new drone if below MAX_DRONES. Returns true if added. */
    public boolean addNewDrone() {
        if (drones.size() >= GameConfig.MAX_DRONES) return false;
        return drones.add(new Drone());
    }

    /** @return current position (clone) */
    public Position getPosition() { return position.clone(); }

    /**
     * Returns an unmodifiable view of the drone set.
     * Callers cannot add or remove drones through this reference.
     */
    public Set<Drone> getDrones() {
        return Collections.unmodifiableSet(drones);
    }

    /** Returns true if all drones are destroyed. */
    public boolean allDronesLost() {
        return drones.stream().allMatch(Drone::isDestroyed);
    }

    /** Returns the drone with the given id, or null if not found. */
    public Drone getDroneById(int id) {
        return drones.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /** @return unmodifiable list of collected artifacts */
    public List<Artifact> getCollectedArtifacts() {
        return Collections.unmodifiableList(collectedArtifacts);
    }

    /** @return unmodifiable list of collected minerals */
    public List<Mineral> getCollectedMinerals() {
        return Collections.unmodifiableList(collectedMinerals);
    }

    /** @return active drones sorted by fuel (highest first) using {@link DroneComparator} */
    public List<Drone> getDronesByFuel() {
        return drones.stream()
                .filter(d -> !d.isDestroyed())
                .sorted(new DroneComparator(DroneComparator.Mode.FUEL))
                .toList();
    }

    /** @return active drones sorted by hull integrity (highest first) using {@link DroneComparator} */
    public List<Drone> getDronesByIntegrity() {
        return drones.stream()
                .filter(d -> !d.isDestroyed())
                .sorted(new DroneComparator(DroneComparator.Mode.INTEGRITY))
                .toList();
    }

    /** @return current minerals value */
    public int getMinerals() {
        return minerals;
    }

    /** Sets the minerals value. */
    public void setMinerals(int minerals) {
        this.minerals = minerals;
    }

    @Override
    public String toString() {
        return "Ship{fuel=" + currentFuel + "/" + maxFuel +
                ", pos=" + position +
                ", drones=" + drones.size() + "}";
    }

}