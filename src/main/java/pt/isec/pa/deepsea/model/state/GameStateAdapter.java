package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.states.IGameState;

public abstract class GameStateAdapter implements IGameState {
    protected GameContext context;
    protected Game game;

    protected GameStateAdapter(GameContext context, Game game){
        this.context = context;
        this.game = game;
    }

    protected void changeState(IGameState state){
        context.changeState(state);
    }

    @Override
    public boolean move(String command) {return false;}

    @Override
    public boolean ascend(int droneId) {
        return false;
    }

    @Override
    public boolean descend(int droneId) {return false;}

    @Override
    public boolean foundObstacle(){return false;}

    @Override
    public boolean enterShop() {
        return false;
    }

    @Override
    public boolean leaveShop() {
        return false;
    }

    @Override
    public boolean repair(int droneId) {
        return false;
    }

    @Override
    public boolean refuel(int droneId) {
        return false;
    }

    @Override
    public boolean mining() {
        return false;
    }

    @Override
    public boolean retrieveArtifact() {
        return false;
    }

    @Override
    public boolean winMini() {
        return false;
    }

    @Override
    public boolean loseMini() {
        return false;
    }

}
