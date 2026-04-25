package pt.isec.pa.deepsea.model.data.ship;

import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.Position;
import pt.isec.pa.deepsea.model.data.drone.Drone;
import pt.isec.pa.deepsea.model.data.entity.Artifact;
import pt.isec.pa.deepsea.model.data.entity.Mineral;

import java.util.*;

/**
 * Represents the ship in the Deep Sea Mining game.
 * The ship is the main base of operations, managing fuel, position,
 * drones, and the player's collected resources.
 *
 * <p>No internal references are exposed directly — all getters return
 * copies or unmodifiable views to preserve encapsulation.</p>
 *
 * @author Beja N2024134442
 * @version v1.1.0
 */
public class Ship {
    private int currentFuel;
    private Position position;
    private final Set<Drone> drones;
    private final List<Artifact> collectedArtifacts;
    private final List<Mineral> collectedMinerals;

    /** Creates a ship at (0,0) with full fuel and the configured number of drones. */
    public Ship() {
        this.currentFuel = GameConfig.SHIP_MAX_FUEL;
        this.position = new Position(-1, -1);
        this.drones = new HashSet<>();
        this.collectedArtifacts = new ArrayList<>();
        this.collectedMinerals = new ArrayList<>();

        for (int i = 0; i < GameConfig.MAX_DRONES; i++) {
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

    /** Returns true if fuel is empty. */
    public boolean isOutOfFuel() { return currentFuel <= 0; }

    /** @return current fuel */
    public int getCurrentFuel() { return currentFuel; }

    /** @return max fuel */
    public int getMaxFuel() { return GameConfig.SHIP_MAX_FUEL; }

    /** @return current position (immutable) */
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

    /** @return active drones sorted by fuel (highest first) using {@link Drone#BY_FUEL} */
    public List<Drone> getDronesByFuel() {
        return drones.stream()
                .filter(d -> !d.isDestroyed())
                .sorted(Drone.BY_FUEL)
                .toList();
    }

    /** @return active drones sorted by hull integrity (highest first) using {@link Drone#BY_INTEGRITY} */
    public List<Drone> getDronesByIntegrity() {
        return drones.stream()
                .filter(d -> !d.isDestroyed())
                .sorted(Drone.BY_INTEGRITY)
                .toList();
    }

    @Override
    public String toString() {
        return "Ship{fuel=" + currentFuel + "/" + GameConfig.SHIP_MAX_FUEL +
                ", pos=" + position +
                ", drones=" + drones.size() + "}";
    }
}
