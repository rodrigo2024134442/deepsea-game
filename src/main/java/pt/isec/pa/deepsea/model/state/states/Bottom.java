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
}
