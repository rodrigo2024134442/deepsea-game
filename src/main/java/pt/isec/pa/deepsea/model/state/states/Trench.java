package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

public class Trench extends GameStateAdapter {
    public Trench(GameContext context, Game game) {
        super(context,game);
    }

    @Override
    public GameState getState(){
        return GameState.TRENCH;
    }

    @Override
    public boolean move(String command){
        /*
        if(!game.moveDrone(String command)){
            return false;
        }

        if (game.droneisEndOfGrid) {
            changeState(GameState.BOTTOM.createState(context,game));
        }
         */
        return true;
    }

    @Override
    public boolean ascend (int droneId) {
        changeState(GameState.SURFACE.createState(context,game));
        return true;
    }

    @Override
    public boolean descend(int droneId){
        changeState(GameState.BOTTOM.createState(context,game));
        return true;
    }



}
