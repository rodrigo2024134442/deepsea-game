package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.GameContext;
import pt.isec.pa.deepsea.model.state.GameState;
import pt.isec.pa.deepsea.model.state.GameStateAdapter;

public class PuzzleGame extends GameStateAdapter {
    private static final long serialVersionUID = 1L;

    public PuzzleGame(GameContext context, Game game) {
        super(context,game);
    }

    @Override
    public GameState getState(){
        return GameState.MINIGAME;
    }

    @Override
    public boolean movePiece(int row, int col){
        if(!game.movePiece(row,col)){
            return false;
        }

        if (game.isMiniGameSolved()) {
            game.clearMiniGame();
            changeState(GameState.BOTTOM.createState(context, game));
            return true;
        }

        if (game.isMiniGameOutOfMoves()) {
            game.clearMiniGame();
            game.loseResources();
            game.returnDroneToShip();
            changeState(GameState.SURFACE.createState(context, game));
            return true;
        }

        return true;
    }

    @Override
    public boolean winMini() {
        changeState(GameState.BOTTOM.createState(context,game));
        return true;
    }

    @Override
    public boolean loseMini() {
        game.returnDroneToShip();
        changeState(GameState.SURFACE.createState(context,game));
        return true;
    }
}

