package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

public class Surface extends GameStateAdapter {
    private static final long serialVersionUID = 1L;

    public Surface(GameContext context, Game game) {
        super(context,game);
    }

    @Override
    public GameState getState(){
        return GameState.SURFACE;
    }

    @Override
    public boolean move(String command){
        return game.moveShip(command);
    }

    @Override
    public boolean enterShop() {
        changeState(GameState.SHOP.createState(context,game));
        return true;
    }

    @Override
    public boolean descend(int droneId) {
        if (!game.descend(droneId))
            return false;

        changeState(GameState.TRENCH.createState(context,game));
        return true;
    }

}
