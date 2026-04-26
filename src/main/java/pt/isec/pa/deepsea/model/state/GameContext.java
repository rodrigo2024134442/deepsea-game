package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.states.IGameState;

/**
 * Represents the context of the game finite state machine.
 *
 * <p>This class stores the current game state and redirects all actions
 * to the active state. It also owns the main {@link Game} model instance
 * used by the states to execute game logic.</p>
 *
 * <p>The context starts the game in the {@link GameState#SURFACE} state.</p>
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 2)
 */
public class GameContext {
    private IGameState state;
    private Game game;

    /**
     * Creates a new game context, initializes the game model,
     * and sets the initial FSM state to SURFACE.
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

    /**
     * Redirects a movement command to the current state.
     *
     * @param command movement command, usually "w", "a", "s" or "d"
     * @return true if the movement was executed, false otherwise
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
     * @return true if the state changed to shop, false otherwise
     */
    public boolean enterShop() {
        return state.enterShop();
    }

    /**
     * Redirects the leave shop action to the current state.
     *
     * @return true if the state changed to surface, false otherwise
     */
    public boolean leaveShop() {
        return state.leaveShop();
    }

    /**
     * Redirects a drone repair request to the current state.
     *
     * @param droneId identifier of the drone to repair
     * @return true if the drone was repaired, false otherwise
     */
    public boolean repair(int droneId) {
        return state.repair(droneId);
    }

    /**
     * Redirects a drone refuel request to the current state.
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
     * Redirects a minigame piece movement to the current state.
     *
     * @param row row of the piece to move
     * @param col column of the piece to move
     * @return true if the piece was moved, false otherwise
     */
    public boolean movePiece(int row, int col) {
        return state.movePiece(row, col);
    }

    /**
     * Redirects a minigame win action to the current state.
     *
     * @return true if the action was handled, false otherwise
     */
    public boolean winMini() {
        return state.winMini();
    }

    /**
     * Redirects a minigame loss action to the current state.
     *
     * @return true if the action was handled, false otherwise
     */
    public boolean loseMini() {
        return state.loseMini();
    }

    /**
     * Changes the current FSM state.
     *
     * <p>This method has package-private visibility so that only
     * the state package can trigger state changes.</p>
     *
     * @param newState new state object
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
     * Gets the available command information for the current state.
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
}