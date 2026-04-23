package pt.isec.pa.deepsea.model;

import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.Position;
import pt.isec.pa.deepsea.model.data.drone.Drone;
import pt.isec.pa.deepsea.model.data.entity.Artifact;
import pt.isec.pa.deepsea.model.data.entity.Mineral;
import pt.isec.pa.deepsea.model.data.ship.Ship;
import pt.isec.pa.deepsea.model.data.surface.SeaSurface;

import java.util.List;
import java.util.Set;

/**
 * The Game class represents the main entry point of the data model for the
 * "Deep Sea Mining" application.
 *
 * <p>This class is responsible for initializing and aggregating all core
 * components of the game, namely the surface grid and the player's ship.</p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Initialize the game state</li>
 *   <li>Create and generate the surface grid</li>
 *   <li>Create the player's ship</li>
 *   <li>Provide access to core game components</li>
 * </ul>
 *
 * @author Leonardo Matos Ferreira - 2024113113 
 * @author Beja - 2024134442 added SeaSurface and Ship functionality
 * @version v1.0.0 (Stage 1)
 */

public class Game implements GameConfig {
    private SeaSurface seaSurface;
    private Ship ship;


    private void initialize (){
        /* Initialize sea surface and ship */
        ship = new Ship();
        seaSurface = new SeaSurface(GameConfig.DEFAULT_SEASURFACE_ROWS, GameConfig.DEFAULT_SEASURFACE_COLS, ship);

        ship.setPosition(DEFAULT_SEASURFACE_COLS / 2, DEFAULT_SEASURFACE_ROWS / 2);
    }

    /** Starts the game. */
    public void start() { 
        initialize(); 
    }

    /* GETTERS */

//    /** Gets surface grid. */
//    public SeaSurface getSurfaceGrid(){
//        return seaSurface;
//    }
//
//    /** Gets ship. */
//    public Ship getShip(){
//        return ship;
//    }

    /** Gets the ship's current X position. */
    public int getShipX() {
        return ship.getPosition().getCol();
    }

    /** Gets the ship's current Y position. */
    public int getShipY() {
        return ship.getPosition().getRow();
    }

    /** Gets the ship's current fuel level. */
    public int getShipFuel() {
        return ship.getCurrentFuel();
    }

    /** Gets the ship's maximum fuel capacity. */
    public int getShipMaxFuel() {
        return ship.getMaxFuel();
    }

    /** Returns true if the ship has no fuel left. */
    public boolean isShipOutOfFuel() {
        return ship.isOutOfFuel();
    }

    /** Gets all drones managed by the ship. */
    public Set<Drone> getShipDrones() {
        return ship.getDrones();
    }

    /** Returns true if all drones are destroyed. */
    public boolean areAllDronesLost() {
        return ship.allDronesLost();
    }

    /** Gets a specific drone by its ID. */
    public Drone getShipDroneById(int id) {
        return ship.getDroneById(id);
    }

    /** Gets drones sorted by fuel (highest first). */
    public List<Drone> getShipDronesByFuel() {
        return ship.getDronesByFuel();
    }

    /** Gets drones sorted by hull integrity (highest first). */
    public List<Drone> getShipDronesByIntegrity() {
        return ship.getDronesByIntegrity();
    }

    /** Gets all artifacts collected by the ship. */
    public List<Artifact> getCollectedArtifacts() {
        return ship.getCollectedArtifacts();
    }

    /** Gets all minerals collected by the ship. */
    public List<Mineral> getCollectedMinerals() {
        return ship.getCollectedMinerals();
    }

    public boolean moveShip(String command) {
        if (ship == null || seaSurface == null)
            return false;

        if (ship.isOutOfFuel())
            return false;

        Position current = ship.getPosition();
        int newRow = current.getRow();
        int newCol = current.getCol();

        switch (command) {
            case "w" -> newRow--; // up
            case "s" -> newRow++; // down
            case "a" -> newCol--; // left
            case "d" -> newCol++; // right
            default -> {
                return false;
            }
        }
        if (!seaSurface.isValidPosition(newRow, newCol))
            return false;

        ship.setPosition(newRow, newCol);
        ship.consumeFuel(1);
        seaSurface.markVisited(ship.getPosition());

        return true;
    }

    public boolean moveDrone(String command) {
        ship.getDronesByFuel();

        Drone drone = new Drone();
        //drone.move(String command);
        return true;
    }

    public Position getShipPosition() {
        return ship.getPosition();
    }
}
