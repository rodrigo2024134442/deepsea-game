package pt.isec.pa.deepsea.model.state;


import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.states.*;

public enum GameState {
    SURFACE, SHOP, TRENCH, BOTTOM, MINIGAME;

    public IGameState createState (GameContext context, Game game) {
        return switch (this) {
            case SURFACE ->  new Surface(context,game);
            case TRENCH ->  new Trench(context,game);
            case BOTTOM ->  new Bottom(context,game);
            case MINIGAME ->  new PuzzleGame(context,game);
            case SHOP -> new Shop(context,game);
        };
    }
}