package pt.isec.pa.deepsea.model;

import pt.isec.pa.deepsea.model.data.drone.DroneState;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;

import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Facade for accessing the entire "Deep Sea Mining" game logic.
 *
 * <p>This class is the single entry point for the UI (graphical or text).
 * It completely hides the internal complexity of the data model and the
 * state machine ({@link GameContext}).</p>
 *
 * <p>No method returns references to internal model objects.
 * All information is returned as primitive types, enumerations,
 * immutable objects or immutable collections.</p>
 *
 * @author Beja 2024134442
 * @version v1.1.0 (Stage 2)
 */
public class DeepSeaManager {

    private GameContext context;
    private final DeepSeaLog log;
    public static final String PROP_GAME_STATE = "_state_";
    public static final String PROP_GRID       = "_grid_";
    public static final String PROP_SHIP       = "_ship_";
    public static final String PROP_DRONES     = "_drones_";
    public static final String PROP_MINIGAME   = "_minigame_";
    public static final String PROP_LOG        = "_log_";

    private PropertyChangeSupport pcs;

    /**
     * Creates the manager and initialises all subsystems.
     * The game starts immediately in the {@link GameState#SURFACE} state.
     */
    public DeepSeaManager() {
        this.context = new GameContext();
        this.log     = DeepSeaLog.getInstance();
        pcs = new PropertyChangeSupport(this);
        log.log("Game started.");
    }

    // -- FSM state --

    /**
     * Returns the current state of the finite state machine.
     *
     * @return current {@link GameState}
     */
    public GameState getState() {
        return context.getState();
    }

    // -- Actions --

    /**
     * Moves the ship (SURFACE state) or the active drone (TRENCH / BOTTOM states)
     * in the given direction.
     *
     * <p>Valid commands: {@code "w"} (up), {@code "s"} (down),
     * {@code "a"} (left), {@code "d"} (right).</p>
     *
     * @param command direction command
     * @return {@code true} if the move was accepted
     */
    public boolean move(String command) {
        boolean ok = context.move(command);
        if (ok) {
            log.log("Move: " + command + " [state=" + getState() + "]");
            fireAll();
        }
        return ok;
    }

    /**
     * Sends the drone with the given ID into the trench below the ship.
     * Transition: SURFACE → TRENCH.
     *
     * @param droneId ID of the drone to deploy
     * @return {@code true} if the drone was successfully deployed
     */
    public boolean descend(int droneId) {
        boolean ok = context.descend(droneId);
        if (ok) {
            log.log("Drone " + droneId + " started descending.");
            fireAll();
        }
        return ok;
    }

    /**
     * Begins the ascent of the active drone from the sea floor back through the trench.
     * Transition: BOTTOM → TRENCH.
     *
     * @return {@code true} if the ascent was initiated
     */
    public boolean ascend() {
        boolean ok = context.ascend();
        if (ok) {
            log.log("Active drone started ascending.");
            fireAll();
        }
        return ok;
    }

    /**
     * Enters the shop.
     * Transition: SURFACE → SHOP.
     *
     * @return {@code true} if the transition was accepted
     */
    public boolean enterShop() {
        boolean ok = context.enterShop();
        if (ok) {
            log.log("Entered shop.");
            fireAll();
        }
        return ok;
    }

    /**
     * Leaves the shop and returns to the surface.
     * Transition: SHOP → SURFACE.
     *
     * @return {@code true} if the transition was accepted
     */
    public boolean leaveShop() {
        boolean ok = context.leaveShop();
        if (ok) {
            log.log("Left shop.");
            fireAll();
        }
        return ok;
    }

    /**
     * Repairs the hull of the given drone.
     * Only valid in the SHOP state.
     *
     * @param droneId ID of the drone to repair
     * @return {@code true} if the repair was performed
     */
    public boolean repair(int droneId) {
        boolean ok = context.repair(droneId);
        if (ok) {
            log.log("Drone " + droneId + " repaired.");
            fireAll();
        }
        return ok;
    }

    /**
     * Refuels the given drone using the ship's fuel reserve.
     * Only valid in the SHOP state.
     *
     * @param droneId ID of the drone to refuel
     * @return {@code true} if the refuel was performed
     */
    public boolean refuel(int droneId) {
        boolean ok = context.refuel(droneId);
        if (ok){
            log.log("Drone " + droneId + " refuelled.");
            fireAll();
        }
        return ok;
    }

    /**
     * Collects the mineral at the drone's current position.
     * Only valid in the BOTTOM state.
     *
     * @return {@code true} if a mineral was collected
     */
    public boolean mining() {
        boolean ok = context.mining();
        if (ok) {
            log.log("Mineral collected.");
            fireAll();
        }
        return ok;
    }

    /**
     * Attempts to retrieve the artifact at the drone's current position.
     * On success, starts the minigame.
     * Transition: BOTTOM → MINIGAME.
     *
     * @return {@code true} if the artifact was found and the minigame started
     */
    public boolean retrieveArtifact() {
        boolean ok = context.retrieveArtifact();
        if (ok) {
            log.log("Artifact found — minigame started.");
            fireAll();
        }
        return ok;
    }

    /**
     * Moves the puzzle piece at (row, col) into the adjacent empty space.
     * Only valid in the MINIGAME state.
     *
     * @param row row of the piece to move
     * @param col column of the piece to move
     * @return {@code true} if the move was valid
     */
    public boolean movePiece(int row, int col) {
        boolean ok = context.movePiece(row, col);
        if (ok) {
            log.log("Puzzle piece moved: (row:" + row + ", col:" + col + ")");
            fireAll();
        }
        return ok;
    }

    /**
     * Starts a new game, resetting all state and clearing the log.
     * Transition to initial SURFACE state.
     */
    public void newGame() {
        context = new GameContext();
        log.clear();
        log.log("Game started.");
        fireAll();
    }

    // -- Ship --

    /** @return current row of the ship on the surface grid */
    public int getShipRow() {
        return context.getGame().getShipY();
    }

    /** @return current column of the ship on the surface grid */
    public int getShipCol() {
        return context.getGame().getShipX();
    }

    /** @return ship's current fuel level */
    public int getShipFuel() {
        return context.getGame().getShipFuel();
    }

    /** @return ship's maximum fuel capacity */
    public int getShipMaxFuel() {
        return context.getGame().getShipMaxFuel();
    }

    /** @return ship's mineral currency available for repairs and upgrades in the shop */
    public int getShipMinerals() {
        return context.getGame().getShipMinerals();
    }

    /** @return number of artifacts collected by the ship */
    public int getCollectedArtifactsCount() {
        return context.getGame().getCollectedArtifacts().size();
    }

    /** @return {@code true} if the given surface cell has any artifact in its sea floor */
    public boolean surfaceCellHasArtifacts(int row, int col) {
        return context.getGame().surfaceCellHasArtifacts(row, col);
    }

    /** @return number of minerals collected by the ship */
    public int getCollectedMineralsCount() {
        return context.getGame().getCollectedMinerals().size();
    }

    // -- Drones --

    /**
     * Returns the IDs of all drones on the ship.
     *
     * @return immutable list of drone IDs
     */
    public List<Integer> getDroneIds() {
        return context.getGame().getShipDrones()
                .stream()
                .map(d -> d.getId())
                .toList();
    }

    /**
     * @param droneId drone ID
     * @return drone's current fuel, or -1 if not found
     */
    public int getDroneFuel(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        return drone == null ? -1 : drone.getCurrentFuel();
    }

    /**
     * @param droneId drone ID
     * @return drone's maximum fuel capacity, or -1 if not found
     */
    public int getDroneMaxFuel(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        return drone == null ? -1 : drone.getMaxFuel();
    }

    /**
     * @param droneId drone ID
     * @return drone's current hull integrity (0–100), or -1 if not found
     */
    public int getDroneHullIntegrity(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        return drone == null ? -1 : drone.getHullIntegrity();
    }

    /**
     * @param droneId drone ID
     * @return drone's maximum hull integrity, or -1 if not found
     */
    public int getDroneMaxHullIntegrity(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        return drone == null ? -1 : drone.getMaxHullIntegrity();
    }

    /**
     * @param droneId drone ID
     * @return drone's current state ({@link DroneState}), or {@code null} if not found
     */
    public DroneState getDroneState(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        return drone == null ? null : drone.getState();
    }

    /**
     * @param droneId drone ID
     * @return drone's current row in the active grid, or -1 if not positioned
     */
    public int getDroneRow(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        if (drone == null || drone.getPosition() == null) return -1;
        return drone.getPosition().getRow();
    }

    /**
     * @param droneId drone ID
     * @return drone's current column in the active grid, or -1 if not positioned
     */
    public int getDroneCol(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        if (drone == null || drone.getPosition() == null) return -1;
        return drone.getPosition().getCol();
    }

    /**
     * @param droneId drone ID
     * @return amount of minerals carried by the drone
     */
    public int getDroneMinerals(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        return drone == null ? 0 : drone.getMinerals();
    }

    /**
     * @param droneId drone ID
     * @return number of artifacts currently carried by the drone
     */
    public int getDroneArtifactsCount(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        return drone == null ? 0 : drone.getCollectedArtifacts().size();
    }

    /**
     * @param droneId drone ID
     * @return number of minerals collected by the drone
     */
    public int getDroneMineralsCount(int droneId) {
        var drone = context.getGame().getShipDroneById(droneId);
        return drone == null ? 0 : drone.getCollectedMinerals().size();
    }

    /** @return {@code true} if there is an active drone currently in expedition */
    public boolean hasActiveDrone() {
        return context.getGame().hasActiveDrone();
    }

    // -- Game over --

    /**
     * Checks whether the game has ended for any reason.
     *
     * @return {@code true} if the game is over
     */
    public boolean isGameOver() {
        return context.getGame().areAllDronesLost() || context.getGame().isShipOutOfFuel();
    }

    /**
     * Returns the reason the game ended, or an empty string if still running.
     *
     * @return game-over message
     */
    public String getGameOverReason() {
        if (context.getGame().areAllDronesLost()) return "All drones have been destroyed.";
        if (context.getGame().isShipOutOfFuel())  return "The ship has run out of fuel.";
        return "";
    }

    // -- Grid queries (JavaFX UI) --

    /** @return number of rows of the surface grid */
    public int getSurfaceRows() {
        return context.getGame().getSurfaceRows();
    }

    /** @return number of columns of the surface grid */
    public int getSurfaceCols() {
        return context.getGame().getSurfaceCols();
    }

    /**
     * @param row cell row
     * @param col cell column
     * @return {@code true} if the surface cell has already been visited by the ship
     */
    public boolean isSurfaceCellVisited(int row, int col) {
        return context.getGame().isSurfaceCellVisited(row, col);
    }

    /** @return number of rows in the active trench, or 0 if none */
    public int getTrenchRows() {
        return context.getGame().getTrenchRows();
    }

    /** @return number of columns in the active trench, or 0 if none */
    public int getTrenchCols() {
        return context.getGame().getTrenchCols();
    }

    /**
     * Returns the type of entity in a trench cell, as a string the UI can switch on.
     * Possible values: {@code "ROCK"}, {@code "ANIMAL"}, {@code "CURRENT"}, {@code "EMPTY"}.
     *
     * @param row cell row
     * @param col cell column
     * @return entity type string
     */
    public String getTrenchCellEntity(int row, int col) {
        return context.getGame().getTrenchCellEntity(row, col);
    }

    /** @return number of rows in the active sea floor, or 0 if none */
    public int getBottomRows() {
        return context.getGame().getBottomRows();
    }

    /** @return number of columns in the active sea floor, or 0 if none */
    public int getBottomCols() {
        return context.getGame().getBottomCols();
    }

    /**
     * @param row cell row
     * @param col cell column
     * @return {@code true} if the sea floor cell has been revealed by the drone
     */
    public boolean isBottomCellRevealed(int row, int col) {
        return context.getGame().isBottomCellRevealed(row, col);
    }

    /**
     * Returns the type of entity in a revealed sea floor cell.
     * Possible values: {@code "ARTIFACT"}, {@code "MINERAL"}, {@code "MONSTER"}, {@code "EMPTY"}.
     * Returns {@code "HIDDEN"} if the cell has not been revealed yet.
     *
     * @param row cell row
     * @param col cell column
     * @return entity type string
     */
    public String getBottomCellEntity(int row, int col) {
        return context.getGame().getBottomCellEntity(row, col);
    }

    // -- Active drone position (for drawing on grids) --

    /**
     * @return row of the active drone in the current grid, or -1 if none
     */
    public int getActiveDroneRow() {
        return context.getGame().getActiveDroneRow();
    }

    /**
     * @return column of the active drone in the current grid, or -1 if none
     */
    public int getActiveDroneCol() {
        return context.getGame().getActiveDroneCol();
    }

    // -- Contextual checks for the UI --

    /** @return {@code true} if the active drone was destroyed on the sea floor */
    public boolean isActiveDroneDestroyed() {
        return context.getGame().isActiveDroneDestroyed();
    }

    /**
     * @return {@code true} if there is an artifact at the active drone's current position
     */
    public boolean droneIsOnArtifact() {
        return context.getGame().foundArtifact();
    }

    /**
     * @return {@code true} if there is a mineral at the active drone's current position
     */
    public boolean droneIsOnMineral() {
        return context.getGame().foundMineral();
    }

    // -- Minigame --

    /**
     * @return size of the puzzle grid (NxN), or 0 if no minigame is active
     */
    public int getMiniGameSize() {
        return context.getGame().getMiniGameSize();
    }

    /**
     * Returns the value of the puzzle piece at (row, col).
     * 0 represents the empty slot.
     *
     * @param row piece row
     * @param col piece column
     * @return piece value, or -1 if no minigame is active
     */
    public int getMiniGameCell(int row, int col) {
        return context.getGame().getMiniGameCell(row, col);
    }

    /** @return number of moves made so far in the minigame */
    public int getMiniGameMoveCount() {
        return context.getGame().getMiniGameMoveCount();
    }

    /** @return maximum number of moves allowed in the minigame */
    public int getMiniGameMaxMoves() {
        return context.getGame().getMiniGameMaxMoves();
    }

    /** @return row of the empty slot in the puzzle, or -1 if no minigame is active */
    public int getMiniGameEmptyRow() {
        return context.getGame().getMiniGameEmptyRow();
    }

    /** @return column of the empty slot in the puzzle, or -1 if no minigame is active */
    public int getMiniGameEmptyCol() {
        return context.getGame().getMiniGameEmptyCol();
    }

    /**
     * Returns a full copy of the puzzle board.
     * Each cell contains the piece number, or 0 for the empty slot.
     *
     * @return copy of the board, or null if no minigame is active
     */
    public int[][] getMiniGameBoard() {
        return context.getGame().getMiniGameBoard();
    }

    // -- Persistence --

    /**
     * Saves the current game state to the given file.
     * Used by the JavaFX UI via a FileChooser.
     *
     * @param file destination file
     * @return {@code true} if saved successfully
     */
    public boolean saveGame(File file) {
        if (file == null) return false;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(context);
            log.log("Game saved to " + file.getName());
            pcs.firePropertyChange(PROP_LOG, null, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Loads a previously saved game state from the given file.
     * Used by the JavaFX UI via a FileChooser.
     *
     * @param file source file
     * @return {@code true} if loaded successfully
     */
    public boolean loadGame(File file) {
        if (file == null) return false;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            context = (GameContext) ois.readObject();
            log.log("Game loaded from " + file.getName());
            fireAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // -- Log --

    /**
     * Returns all log messages recorded in this session.
     *
     * @return immutable list of log messages
     */
    public List<String> getLog() {
        return log.getMessages();
    }

    /**
     * Saves the log to the file defined in {@code GameConfig.LOG_FILE_NAME}.
     *
     * @return {@code true} if saved successfully
     */
    public boolean saveLog() {
        return log.saveToFile();
    }

    /** Clears all log messages. */
    public void clearLog() {
        log.clear();
        fireAll();
    }

    // Property change support

    /**
     * Subscribes a listener for a specific topic.
     *
     * @param prop topic name (one of the PROP_* constants)
     * @param listener    listener
     */
    public void addPropertyChangeListener(String prop, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(prop, listener);
    }

    /**
     * Subscribes a listener for all topics.
     *
     * @param listener listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Fires all property change topics at once.
     * Used after any action that may have changed several aspects of the model.
     */
    private void fireAll() {
        pcs.firePropertyChange(PROP_GAME_STATE, null, null);
        pcs.firePropertyChange(PROP_GRID,       null, null);
        pcs.firePropertyChange(PROP_SHIP,       null, null);
        pcs.firePropertyChange(PROP_DRONES,     null, null);
        pcs.firePropertyChange(PROP_MINIGAME,   null, null);
        pcs.firePropertyChange(PROP_LOG,        null, null);
    }
}
