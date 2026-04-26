package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.Game;
import pt.isec.pa.deepsea.model.state.states.IGameState;

public class GameContext {
    private IGameState state;
    private Game game;

    public GameContext(){
        this.game=new Game();
        state = GameState.SURFACE.createState(this,game);
        game.start();
    }

    public GameState getState() {
        return state.getState();
    }

    //SHIP & DRONE & MINIGAME
    public boolean move(String command) {
        return state.move(command);
    }
    public boolean ascend(){return state.ascend();}
    public boolean descend(int droneId){return state.descend(droneId);};
    public boolean foundObstacle(){return state.foundObstacle();}

    //SHOP
    public boolean enterShop() {
        return state.enterShop();
    }
    public boolean leaveShop() {
        return state.leaveShop();
    }
    public boolean repair(int droneId) {return state.repair(droneId);}
    public boolean refuel(int droneId) {return state.refuel(droneId);}

    //BOTTOMFLOOR
    public boolean mining() {return state.mining();}
    public boolean retrieveArtifact(){return state.retrieveArtifact();}

    //MINIGAME
    public boolean movePiece(int row, int col) {return state.movePiece(row,col);}
    public boolean winMini() {return state.winMini();}
    public boolean loseMini() {return state.loseMini();}

    void changeState(IGameState newState){
        state = newState;
    }

    public String getCurrentGridAsString() {
        return game.getCurrentGridAsString(getState());
    }

    public String getDronesInfoAsString() {
        return game.getDronesInfoAsString();
    }

    public String getShipInfoAsString() {
        return game.getShipInfoAsString();
    }

    public String getCommandInfoAsString() {
        return game.getCommandInfoAsString(state.getState());
    }

    public String getMiniGameAsString() {
        return game.getMiniGameAsString();
    }
}
