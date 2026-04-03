package pt.isec.pa.deepsea.model.data.ship;

import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.drone.Drone;
import pt.isec.pa.deepsea.model.data.entity.mineral.Mineral;
import pt.isec.pa.deepsea.model.data.entity.artifact.Artifact;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the ship in the Deep Sea Mining game.
 * The ship is the main base of operations, managing fuel, position,
 * drones, and the player's collected resources.
 *
 * @author Beja N2024134442
 * @version v1.0.0 (Stage 1)
 */
public class Ship {
    private int currentFuel;
    private int posX;
    private int posY;
    private final List<Drone> drones;
    private final List<Artifact> collectedArtifacts;
    private final List<Mineral> collectedMinerals;

    /** Creates a ship at (0,0) with full fuel and 3 drones. */
    public Ship() {
        this.currentFuel = GameConfig.SHIP_MAX_FUEL;
        this.posX = 0;
        this.posY = 0;
        this.drones = new ArrayList<>();
        this.collectedArtifacts = new ArrayList<>();
        this.collectedMinerals = new ArrayList<>();

        for (int i = 0; i < GameConfig.MAX_DRONES; i++) {
            drones.add(new Drone());
        }
    }

    /** Uses fuel (ignores values <= 0). */
    public void consumeFuel(int amount) {
        if (amount <= 0) return;
        currentFuel -= amount;
        if (currentFuel < 0) currentFuel = 0;
    }

    /** Adds an artifact to the collection. */
    public void addArtifact(Artifact artifact) {
        if (artifact != null) collectedArtifacts.add(artifact);
    }

    /** Adds a mineral to the collection. */
    public void addMineral(Mineral mineral) {
        if (mineral != null) collectedMinerals.add(mineral);
    }

    /** Sets ship position. */
    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    /** Checks if fuel is empty. */
    public boolean isOutOfFuel() {
        return currentFuel <= 0;
    }

    /** Checks if all drones are destroyed. */
    public boolean allDronesLost() {
        return drones.stream().allMatch(Drone::isDestroyed);
    }

    /** Gets a drone by index (null if invalid). */
    public Drone getDrone(int index) {
        if (index < 0 || index >= drones.size()) return null;
        return drones.get(index);
    }

    /** Gets current fuel. */
    public int getCurrentFuel() {
        return currentFuel;
    }

    /** Gets max fuel. */
    public int getMaxFuel() {
        return GameConfig.SHIP_MAX_FUEL;
    }

    /** Gets X position. */
    public int getPosX() {
        return posX;
    }

    /** Gets Y position. */
    public int getPosY() {
        return posY;
    }

    /** Gets all drones. */
    public List<Drone> getDrones() {
        return drones;
    }

    /** Gets collected artifacts. */
    public List<Artifact> getCollectedArtifacts() {
        return collectedArtifacts;
    }

    /** Gets collected minerals. */
    public List<Mineral> getCollectedMinerals() {
        return collectedMinerals;
    }

    @Override
    public String toString() {
        return "Ship{fuel=" + currentFuel + "/" + GameConfig.SHIP_MAX_FUEL +
                ", pos=(" + posX + "," + posY + ")" +
                ", drones=" + drones.size() + "}";
    }
}