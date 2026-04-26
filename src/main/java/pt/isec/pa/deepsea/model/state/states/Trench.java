package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

public class Trench extends GameStateAdapter {
    private static final long serialVersionUID = 1L;

    public Trench(GameContext context, Game game) {
        super(context,game);
    }

    @Override
    public GameState getState(){
        return GameState.TRENCH;
    }

    @Override
    public boolean move(String command) {

        if (!game.moveDroneInTrench(command))
            return false;

        if (game.isActiveDroneDestroyed()) {
            game.returnDroneToShip();
            changeState(GameState.SURFACE.createState(context,game));
            return true;
        }

        if(game.hasDroneReachedTop()){
            game.enterSurface();
            changeState(GameState.SURFACE.createState(context,game));
        }

        if (game.hasDroneReachedBottom() && !game.getIsLastStateBotom()){
            game.enterSeaFloor();
            changeState(GameState.BOTTOM.createState(context,game));
            return true;
        }
        return true;
    }
}