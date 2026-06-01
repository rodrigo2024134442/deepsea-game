package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.state.GameState;

import java.io.Serializable;

/**
 * Contract for game FSM states and the actions they can handle.
 */
public interface IGameState extends Serializable {
    GameState getState();

    boolean move(String command);
    boolean ascend();
    boolean descend(int droneId);
    boolean foundObstacle();

    boolean enterShop();
    boolean leaveShop();
    boolean repair(int droneId);
    boolean refuel(int droneId);

    boolean mining();
    boolean retrieveArtifact();

    boolean movePiece(int row, int col);

}
