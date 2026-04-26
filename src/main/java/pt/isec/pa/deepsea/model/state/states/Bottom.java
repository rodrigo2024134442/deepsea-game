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
    public boolean move(String command) {
        if (!game.moveDroneInBottom(command))
            return false;

        if (game.isActiveDroneDestroyed()) {
            game.returnDroneToShip();
            changeState(GameState.SURFACE.createState(context,game));
            return true;
        }

        return true;
    }

    @Override
    public boolean ascend(){
        game.ascend();
        changeState(GameState.TRENCH.createState(context,game));
        return true;
    }

    @Override
    public boolean retrieveArtifact() {

        if (!game.retrieveArtifact())
            return false;

        changeState(GameState.MINIGAME.createState(context,game));
        return true;
    }

    @Override
    public boolean mining() {
        return game.mining();
    }
}
