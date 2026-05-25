package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.data.GameConfig;
import pt.isec.pa.deepsea.model.state.states.IGameState;

import java.io.*;

/**
 * Represents the context of the game Finite State Machine.
 *
 * <p>This class stores the current state of the FSM and redirects all game
 * actions to that active state. It also owns the main {@link Game} model
 * instance used by the states to execute game logic.</p>
 *
 * <p>The class also supports binary persistence, allowing the current game
 * context to be saved and later restored.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
public class GameContext implements Serializable, GameConfig {
    private static final long serialVersionUID = 1L;

    private IGameState state;
    private Game game;

    /**
     * Creates a new game context, initializes the game model,
     * and starts the FSM in the surface state.
     */
    public GameContext() {
        this.game = new Game();
        state = GameState.SURFACE.createState(this, game);
        game.start();
    }

    /**
     * Gets the current FSM state.
     *
     * @return current game state
     */
    public GameState getState() {
        return state.getState();
    }

    /** Opens game instance for facade access. */
    public Game getGame() { return game; }


    /**
     * Redirects a movement command to the current state.
     *
     * @param command movement command
     * @return true if the action was executed, false otherwise
     */
    public boolean move(String command) {
        return state.move(command);
    }

    /**
     * Redirects the ascend action to the current state.
     *
     * @return true if the action was executed, false otherwise
     */
    public boolean ascend() {
        return state.ascend();
    }

    /**
     * Redirects the descend action to the current state.
     *
     * @param droneId identifier of the drone to launch
     * @return true if the drone started descending, false otherwise
     */
    public boolean descend(int droneId) {
        return state.descend(droneId);
    }

    /**
     * Redirects obstacle handling to the current state.
     *
     * @return true if the obstacle action was handled, false otherwise
     */
    public boolean foundObstacle() {
        return state.foundObstacle();
    }

    /**
     * Redirects the enter shop action to the current state.
     *
     * @return true if the FSM entered the shop state, false otherwise
     */
    public boolean enterShop() {
        return state.enterShop();
    }

    /**
     * Redirects the leave shop action to the current state.
     *
     * @return true if the FSM left the shop state, false otherwise
     */
    public boolean leaveShop() {
        return state.leaveShop();
    }

    /** Buys ship fuel using minerals. Only allowed in SHOP state. */
    public boolean buyShipFuel(int amount) {
        if (state.getState() != GameState.SHOP) return false;
        return game.buyShipFuel(amount);
    }

    /** Buys a new drone using minerals. Only allowed in SHOP state. */
    public boolean buyDrone() {
        if (state.getState() != GameState.SHOP) return false;
        return game.buyDrone();
    }

    /** Upgrade ship max fuel. Only allowed in SHOP state. */
    public boolean upgradeShipMaxFuel(int units) {
        if (state.getState() != GameState.SHOP) return false;
        return game.upgradeShipMaxFuel(units);
    }

    /** Upgrade drones max fuel. Only allowed in SHOP state. */
    public boolean upgradeDronesMaxFuel(int units) {
        if (state.getState() != GameState.SHOP) return false;
        return game.upgradeDronesMaxFuel(units);
    }

    /** Upgrade drones max hull. Only allowed in SHOP state. */
    public boolean upgradeDronesMaxHull(int points) {
        if (state.getState() != GameState.SHOP) return false;
        return game.upgradeDronesMaxHull(points);
    }

    /**
     * Redirects a drone repair action to the current state.
     *
     * @param droneId identifier of the drone to repair
     * @return true if the drone was repaired, false otherwise
     */
    public boolean repair(int droneId) {
        return state.repair(droneId);
    }

    /**
     * Redirects a drone refuel action to the current state.
     *
     * @param droneId identifier of the drone to refuel
     * @return true if the drone was refueled, false otherwise
     */
    public boolean refuel(int droneId) {
        return state.refuel(droneId);
    }

    /**
     * Redirects a mining action to the current state.
     *
     * @return true if a mineral was mined, false otherwise
     */
    public boolean mining() {
        return state.mining();
    }

    /**
     * Redirects an artifact retrieval action to the current state.
     *
     * @return true if an artifact was retrieved, false otherwise
     */
    public boolean retrieveArtifact() {
        return state.retrieveArtifact();
    }

    /**
     * Redirects a puzzle piece movement to the current state.
     *
     * @param row row of the selected piece
     * @param col column of the selected piece
     * @return true if the piece was moved, false otherwise
     */
    public boolean movePiece(int row, int col) {
        return state.movePiece(row, col);
    }

    /**
     * Changes the current FSM state.
     *
     * @param newState new concrete state
     */
    void changeState(IGameState newState) {
        state = newState;
    }

    /**
     * Gets a textual representation of the current active grid.
     *
     * @return current grid as text
     */
    public String getCurrentGridAsString() {
        return game.getCurrentGridAsString(getState());
    }

    /**
     * Gets textual information about all drones.
     *
     * @return drone information as text
     */
    public String getDronesInfoAsString() {
        return game.getDronesInfoAsString();
    }

    /**
     * Gets textual information about the ship.
     *
     * @return ship information as text
     */
    public String getShipInfoAsString() {
        return game.getShipInfoAsString();
    }

    /**
     * Gets textual information about the available commands
     * for the current state.
     *
     * @return command information as text
     */
    public String getCommandInfoAsString() {
        return game.getCommandInfoAsString(state.getState());
    }

    /**
     * Gets a textual representation of the active minigame.
     *
     * @return minigame board as text, or a message if no minigame exists
     */
    public String getMiniGameAsString() {
        return game.getMiniGameAsString();
    }

    /**
     * Saves the current game context to a binary file.
     *
     * @return true if the game was saved successfully, false otherwise
     */
    public boolean saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BIN_FILE_NAME))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Loads a game context from a binary file.
     *
     * @return loaded game context, or null if loading failed
     */
    public static GameContext loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BIN_FILE_NAME))) {
            return (GameContext) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}