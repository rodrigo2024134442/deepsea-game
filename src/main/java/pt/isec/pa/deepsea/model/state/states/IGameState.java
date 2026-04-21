package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.state.GameState;

public interface IGameState {
    GameState getState();

    boolean move(String command);
    boolean ascend();
    boolean descend();
    boolean foundObstacle();

    boolean enterShop();
    boolean leaveShop();
    boolean repair();
    boolean refuel();

    boolean mining();
    boolean retrieveArtifact();

    boolean winMini();
    boolean loseMini();

}
