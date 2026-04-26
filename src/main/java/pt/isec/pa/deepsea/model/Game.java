package pt.isec.pa.deepsea.model;

import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.data.Position;
import pt.isec.pa.deepsea.model.data.cell.UnderwaterCell;
import pt.isec.pa.deepsea.model.data.drone.Drone;
import pt.isec.pa.deepsea.model.data.drone.DroneState;
import pt.isec.pa.deepsea.model.data.entity.*;
import pt.isec.pa.deepsea.model.data.minigame.MiniGame;
import pt.isec.pa.deepsea.model.data.seafloor.SeaFloor;
import pt.isec.pa.deepsea.model.data.ship.Ship;
import pt.isec.pa.deepsea.model.data.surface.SeaSurface;
import pt.isec.pa.deepsea.model.data.surface.SurfaceCell;
import pt.isec.pa.deepsea.model.data.trench.SeaTrench;
import pt.isec.pa.deepsea.model.state.GameState;


import java.io.Serializable;
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

public class Game implements GameConfig, Serializable {
    private static final long serialVersionUID = 1L;
    private SeaSurface seaSurface;
    private Ship ship;

    private Drone activeDrone;
    private SurfaceCell activeSurfaceCell;
    private MiniGame miniGame;
    private boolean lastStateBotom;


    private void initialize (){
        /* Initialize sea surface and ship */
        ship = new Ship();
        seaSurface = new SeaSurface(GameConfig.DEFAULT_SEASURFACE_ROWS, GameConfig.DEFAULT_SEASURFACE_COLS, ship);
        ship.setPosition(DEFAULT_SEASURFACE_ROWS / 2, DEFAULT_SEASURFACE_COLS / 2);
        lastStateBotom = false;
        activeDrone = null;
        activeSurfaceCell = null;
        miniGame = null;
    }

    /** Starts the game. */
    public void start() { 
        initialize(); 
    }

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

    /** gets lastState of drone to see if its ascending. */
    public boolean getIsLastStateBotom() {
        return lastStateBotom;
    }

    public boolean moveShip(String command) {
        if (ship == null || seaSurface == null || ship.isOutOfFuel() || !ship.hasEnoughFuelForMovement() )
            return false;

        Position currentPosition = ship.getPosition();

        int newRow = currentPosition.getRow();
        int newCol = currentPosition.getCol();

        switch (command) {
            case "w" -> newRow--; // up
            case "s" -> newRow++; // down
            case "a" -> newCol--; // left
            case "d" -> newCol++; // right
            default -> {
                return false;
            }
        }

        if (!seaSurface.isValidPosition(newRow, newCol)){
            return false;
        }

        ship.setPosition(newRow, newCol);
        ship.consumeFuel(FUEL_COST_SHIP_MOVE);
        return true;
    }

    public boolean descend(int droneId) {
        if (ship == null || seaSurface == null)
            return false;

        Drone drone = ship.getDroneById(droneId);
        if (drone == null)
            return false;

        if (drone.isDestroyed() || drone.isOutOfFuel())
            return false;

        SurfaceCell currentCell = seaSurface.getCell(ship.getPosition());
        if (currentCell == null)
            return false;

        SeaTrench trench = currentCell.getTrench();
        if (trench == null)
            return false;

        activeDrone = drone;
        activeSurfaceCell = currentCell;

        activeDrone.resetPrimeSequence();
        activeDrone.placeOnTrench(1,trench.getCols());
        seaSurface.markVisited(ship.getPosition());
        return true;
    }

    public boolean ascend() {

        if (activeDrone == null)
            return false;

        if (activeSurfaceCell == null)
            return false;

        SeaTrench trench = activeSurfaceCell.getTrench();
        activeDrone.placeOnTrench(trench.getRows()-1,trench.getCols());
        lastStateBotom = true;
        return true;
    }

    public boolean moveDroneInTrench(String command) {
        if (activeDrone == null || activeSurfaceCell == null || activeDrone.isOutOfFuel() || !activeDrone.hasEnoughFuelForMovement())
            return false;

        SeaTrench trench = activeSurfaceCell.getTrench();
        if (trench == null)
            return false;

        Position current = activeDrone.getPosition();
        if (current == null)
            return false;

        Position next;

        switch (command.toLowerCase()) {
            case "w" -> next = current.translate(-1, 0);
            case "s" -> next = current.translate(1, 0);
            case "a" -> next = current.translate(0, -1);
            case "d" -> next = current.translate(0, 1);
            default -> {
                return false;
            }
        }

        if(!next.validatePosition(trench.getRows(), trench.getCols())){
            return false;
        }

        UnderwaterCell nextCell = trench.getCell(next);

        if (nextCell == null)
            return false;

        Entity entity = nextCell.getEntity();

        if (entity instanceof Rock || entity instanceof Animal || entity instanceof Current) {
            activeDrone.takeDamage();
            activeDrone.consumeFuel(FUEL_COST_DRONE_MOVE);
            return true;
        }

        activeDrone.setPosition(next);
        activeDrone.consumeFuel(FUEL_COST_DRONE_MOVE);

        return true;
    }

    public boolean hasDroneReachedTop() {
        if (activeDrone == null || activeSurfaceCell == null)
            return false;

        SeaTrench trench = activeSurfaceCell.getTrench();
        if (trench == null)
            return false;

        Position pos = activeDrone.getPosition();
        if (pos == null)
            return false;

        return pos.getRow() == 0;
    }

    public boolean hasDroneReachedBottom() {
        if (activeDrone == null || activeSurfaceCell == null)
            return false;

        SeaTrench trench = activeSurfaceCell.getTrench();
        if (trench == null)
            return false;

        Position pos = activeDrone.getPosition();
        if (pos == null)
            return false;

        return pos.getRow() == trench.getRows() - 1;
    }

    public boolean enterSurface() {
        if (activeDrone == null || activeSurfaceCell == null){
            return false;
        }

        activeDrone.clearPosition();

        activeDrone.placeOnShip();
        ship.setMinerals(ship.getMinerals()+activeDrone.getMinerals());
        for (Mineral m : activeDrone.getCollectedMinerals()) {
            ship.addMineral(m);
        }
        for (Artifact a : activeDrone.getCollectedArtifacts()) {
            ship.addArtifact(a);
        }
        activeDrone.unloadArtifacts();
        activeDrone.unloadMinerals();
        activeDrone.setMinerals(0);
        activeSurfaceCell = null;
        lastStateBotom = false;
        return true;
    }

    public boolean enterSeaFloor() {
        if (activeDrone == null || activeSurfaceCell == null)
            return false;

        SeaFloor seaFloor = activeSurfaceCell.getSeaFloor();
        if (seaFloor == null)
            return false;

        activeDrone.placeOnSeabed(seaFloor.getCols(), seaFloor.getRows());
        seaFloor.revealCell(activeDrone.getPosition());
        lastStateBotom = true;
        return true;
    }

    public boolean isActiveDroneDestroyed() {
        return activeDrone != null && activeDrone.isDestroyed();
    }

    public boolean hasActiveDrone() {
        return activeDrone != null;
    }

    public Position getShipPosition() {
        return ship.getPosition();
    }

    public boolean moveDroneInBottom(String command) {
        if (activeDrone == null || activeSurfaceCell == null || activeDrone.isOutOfFuel() || !activeDrone.hasEnoughFuelForMovement())
            return false;

        SeaFloor seaFloor = activeSurfaceCell.getSeaFloor();

        if (seaFloor == null)
            return false;

        Position current = activeDrone.getPosition();
        if (current == null)
            return false;

        Position next;

        switch (command.toLowerCase()) {
            case "w" -> next = current.translate(-1, 0);
            case "s" -> next = current.translate(1, 0);
            case "a" -> next = current.translate(0, -1);
            case "d" -> next = current.translate(0, 1);
            default -> {
                return false;
            }
        }

        if (!seaFloor.isValidPosition(next))
            return false;

        activeDrone.setPosition(next);
        activeDrone.consumeFuel(1);
        seaFloor.revealCell(next);

        if (seaFloor.hasMonster(next)) {
            activeDrone.destroy();
            activeDrone.clearPosition();
            activeDrone = null;
            activeSurfaceCell = null;
        }

        return true;
    }


    //Métodos Auxiliares (nao devem ser precisos)
    public boolean foundArtifact() {
        if (activeDrone == null || activeSurfaceCell == null)
            return false;

        SeaFloor seaFloor = activeSurfaceCell.getSeaFloor();
        if (seaFloor == null)
            return false;

        Position pos = activeDrone.getPosition();
        if (pos == null)
            return false;

        return seaFloor.hasArtifact(pos);
    }

    public boolean foundMineral() {
        if (activeDrone == null || activeSurfaceCell == null)
            return false;

        SeaFloor seaFloor = activeSurfaceCell.getSeaFloor();
        if (seaFloor == null)
            return false;

        Position pos = activeDrone.getPosition();
        if (pos == null)
            return false;

        return seaFloor.hasMineral(pos);
    }

    public boolean foundMonster() {
        if (activeDrone == null || activeSurfaceCell == null)
            return false;

        SeaFloor seaFloor = activeSurfaceCell.getSeaFloor();
        if (seaFloor == null)
            return false;

        Position pos = activeDrone.getPosition();
        if (pos == null)
            return false;

        return seaFloor.hasMonster(pos);
    }

    public boolean retrieveArtifact() {
        if (activeDrone == null || activeSurfaceCell == null)
            return false;

        SeaFloor seaFloor = activeSurfaceCell.getSeaFloor();
        if (seaFloor == null)
            return false;

        Position pos = activeDrone.getPosition();
        if (pos == null)
            return false;

        if (!seaFloor.hasArtifact(pos))
            return false;

        Entity entity = seaFloor.removeEntity(pos);

        if (!(entity instanceof Artifact artifact))
            return false;

        activeDrone.addArtifact(artifact);

        miniGame = new MiniGame();

        return true;
    }

    public boolean mining() {
        if (activeDrone == null || activeSurfaceCell == null)
            return false;

        SeaFloor seaFloor = activeSurfaceCell.getSeaFloor();
        if (seaFloor == null)
            return false;

        Position pos = activeDrone.getPosition();
        if (pos == null)
            return false;

        Entity entity = seaFloor.removeEntity(pos);

        if (!(entity instanceof Mineral mineral))
            return false;

        activeDrone.addMineral(mineral);

        return true;
    }

    //Shop
    public boolean repair(int droneId) {
        if (ship == null)
            return false;

        Drone drone = ship.getDroneById(droneId);

        if (drone == null)
            return false;

        if (!drone.needsRepair())
            return false;

        drone.repair();

        return true;
    }

    public boolean refuel(int droneId) {
        if (ship == null)
            return false;

        Drone drone = ship.getDroneById(droneId);

        if (drone == null)
            return false;

        if (drone.isFuelFull())
            return false;

        int refuel = drone.getMaxFuel() - drone.getCurrentFuel();

        if(ship.getCurrentFuel()-refuel >=0){
            ship.setCurrentFuel(ship.getCurrentFuel()-refuel);
            drone.refuel(drone.getMaxFuel());
            return true;
        }


        return false;
    }


    //Regressar ao navio

    public boolean returnDroneToShip() {
        if (activeDrone == null)
            return false;

        activeDrone.clearPosition();
        activeDrone.setState(DroneState.ON_SHIP);

        activeDrone = null;
        activeSurfaceCell = null;
        lastStateBotom = false;
        return true;
    }


    //UI TEXTO (Testing)

    public String getCurrentGridAsString(GameState state) {
        return switch (state) {
            case SURFACE, SHOP -> getSurfaceGridAsString();
            case TRENCH -> getTrenchGridAsString();
            case BOTTOM, MINIGAME -> getBottomGridAsString();
        };
    }

    private String getTrenchGridAsString() {
        if (activeDrone == null || activeSurfaceCell == null)
            return "No active drone.\n";

        SeaTrench trench = activeSurfaceCell.getTrench();
        Position dronePos = activeDrone.getPosition();

        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < trench.getRows(); r++) {
            for (int c = 0; c < trench.getCols(); c++) {
                if (dronePos != null && dronePos.getRow() == r && dronePos.getCol() == c) {
                    sb.append(" D ");
                } else {
                    UnderwaterCell cell = trench.getCell(r, c);
                    Entity ent = cell.getEntity();

                    if (ent == null)
                        sb.append(" . ");
                    else if (ent instanceof Animal)
                        sb.append(" A ");
                    else if (ent instanceof Current)
                        sb.append(" C ");
                    else
                        sb.append(" X ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
    private String getBottomGridAsString() {
        if (activeDrone == null || activeSurfaceCell == null)
            return "No active drone.\n";

        SeaFloor seaFloor = activeSurfaceCell.getSeaFloor();
        Position dronePos = activeDrone.getPosition();

        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < seaFloor.getRows(); r++) {
            for (int c = 0; c < seaFloor.getCols(); c++) {
                if (dronePos != null && dronePos.getRow() == r && dronePos.getCol() == c) {
                    sb.append(" D ");
                } else {
                    var cell = seaFloor.getCell(r, c);

                    if (!cell.isRevealed()) {
                        sb.append(" ? ");
                    } else if (cell.getEntity() instanceof Artifact) {
                        sb.append(" A ");
                    } else if (cell.getEntity() instanceof Mineral){
                        sb.append(" M ");
                    } else
                        sb.append(" . ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
    private String getSurfaceGridAsString() {
        StringBuilder sb = new StringBuilder();

        Position shipPos = ship.getPosition();

        for (int r = 0; r < seaSurface.getRows(); r++) {
            for (int c = 0; c < seaSurface.getCols(); c++) {
                if (shipPos.getRow() == r && shipPos.getCol() == c) {
                    sb.append(" S ");
                } else if (seaSurface.isVisited(new Position(r, c))) {
                    sb.append(" . ");
                } else {
                    sb.append(" ~ ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String getDronesInfoAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Drones:\n");

        for (Drone drone : ship.getDrones()) {
            sb.append("ID: ").append(drone.getId())
                    .append(" | Fuel: ").append(drone.getCurrentFuel())
                    .append("/").append(drone.getMaxFuel())
                    .append(" | Hull: ").append(drone.getHullIntegrity())
                    .append("/").append(drone.getMaxHullIntegrity())
                    .append(" | State: ").append(drone.getState())
                    .append(" | Pos: ").append(drone.getPosition())
                    .append(" | Minerals: ").append(drone.getMinerals())
                    .append("\n")
                    .append("   Artifacts Colection: ").append(drone.getCollectedArtifacts().size())
                    .append(" -> ").append(drone.getCollectedArtifacts())
                    .append("\n")
                    .append("   Minerals Colection: ").append(drone.getCollectedMinerals().size())
                    .append(" -> ").append(drone.getCollectedMinerals())

                    .append("\n");
        }

        return sb.toString();
    }

    public String getShipInfoAsString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ship:\n")
                .append("   Position: ").append(ship.getPosition()).append(" || ")
                .append("   Fuel: ").append(ship.getCurrentFuel())
                .append("/").append(ship.getMaxFuel()).append(" || ")
                .append("   Quantity of Minerals: ").append(ship.getMinerals()).append(" || ")
                .append("   Collected Artifacts: ").append(ship.getCollectedArtifacts().size())
                .append(" -> ").append(ship.getCollectedArtifacts()).append(" || ")
                .append("   Collected Minerals: ").append(ship.getCollectedMinerals().size())
                .append(" -> ").append(ship.getCollectedMinerals()).append("\n");

        return sb.toString();
    }

    public String getCommandInfoAsString(GameState state) {
        StringBuilder sb = new StringBuilder();
        sb.append("============================\n")
                .append("Comandos do Estado: ")
                .append(state)
                .append("\n============================\n");
        switch (state) {
            case SURFACE ->
                    sb.append("1 - Move (w/a/s/d)\n")
                        .append("2 - Enter shop\n")
                        .append("6 - Descend with drone\n")
                        .append("save - Guardar jogo\n")
                        .append("load - Carregar jogo\n")
                        .append("0 - Exit\n");
            case SHOP ->
                    sb.append("3 - Leave shop")
                        .append("5 - Refuel drone")
                        .append("0 - Exit");

            case TRENCH ->
                    sb.append("1 - Move (w/a/s/d)")
                        .append("0 - Exit");
            case BOTTOM->
                    sb.append("1 - Move (w/a/s/d)\n")
                        .append("7 - Mining\n")
                        .append("8 - Retrieve artifact\n")
                        .append("20 - ascend\n")
                        .append("0 - Exit\n");
            case MINIGAME ->
                    sb.append("11 - Move piece (row, col)\n")
                            .append("9 - Force win minigame\n")
                            .append("10 - Force lose minigame\n")
                            .append("0 - Exit\n");
        }
            return sb.toString();
    }

    public boolean movePiece(int row, int col) {
        if (miniGame == null) return false;
        return miniGame.movePiece(row, col);
    }

    public boolean isMiniGameSolved() {
        return miniGame != null && miniGame.isSolved();
    }

    public boolean isMiniGameOutOfMoves() {
        return miniGame != null && miniGame.isOutOfMoves();
    }

    public void loseMiniGame() {
        activeDrone.unloadArtifacts();
    }

    public void clearMiniGame() {
        miniGame = null;
    }

    public String getMiniGameAsString() {
        if (miniGame == null)
            return "No active minigame.\n";

        return miniGame.toString();
    }

    public void lightTrench(){

    }

    public void lightBottom (){

    }

    public void loseResources() {
        activeDrone.unloadMinerals();
        activeDrone.unloadArtifacts();
        activeDrone.setMinerals(0);
    }
}




