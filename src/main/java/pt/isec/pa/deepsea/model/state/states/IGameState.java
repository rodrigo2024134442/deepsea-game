package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.state.GameState;

public interface IGameState {
    GameState getState();

    boolean move(String command);
    boolean ascend(int droneId);
    boolean descend(int droneId);
    boolean foundObstacle();

    boolean enterShop();
    boolean leaveShop();
    boolean repair(int droneId);
    boolean refuel(int droneId);

    boolean mining();
    boolean retrieveArtifact();

    boolean winMini();
    boolean loseMini();

}
