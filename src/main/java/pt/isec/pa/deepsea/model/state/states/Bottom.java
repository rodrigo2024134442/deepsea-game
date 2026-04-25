package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

public class Bottom extends GameStateAdapter {

    public Bottom(GameContext context, Game game) {
        super(context,game);
    }

    @Override
    public GameState getState(){
        return GameState.BOTTOM;
    }

    @Override
    public boolean ascend(int droneId){
        changeState(GameState.TRENCH.createState(context,game));
        return true;
    }

    @Override
    public boolean retrieveArtifact(){

        /*
        if(game.retrieveArtifact()){
            changeState(GameState.MINIGAME.createState(context,game));
            return true;
        }
        */
        return false;
    }

    @Override
    public boolean foundObstacle(){

        return true;
    }



}
