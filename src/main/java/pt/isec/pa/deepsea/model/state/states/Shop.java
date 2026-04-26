package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

public class Shop extends GameStateAdapter {
    public Shop(GameContext context, Game game) {
        super(context,game);
    }

    @Override
    public GameState getState(){
        return GameState.SHOP;
    }

    @Override
    public boolean leaveShop() {
        changeState(GameState.SURFACE.createState(context,game));
        return true;
    }

    @Override
    public boolean repair(int droneId) {
        return game.repair(droneId);
    }

    @Override
    public boolean refuel(int droneId) {
        return game.refuel(droneId);
    }

}
