package pt.isec.pa.deepsea.ui.commandbar;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;



public class CommandBar extends VBox {
    private DeepSeaManager manager;
    private static final int TOGGLE_SIZE = 40;

    ToggleButton btnUp, btnDown, btnLeft, btnRight;


    public CommandBar(DeepSeaManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers(){

    }

    private void createViews(){
        btnLeft = new ToggleButton("A");
        btnDown = new ToggleButton("S");
        btnRight = new ToggleButton("D");
        btnUp = new ToggleButton("W");

        btnLeft.setPrefSize(TOGGLE_SIZE,TOGGLE_SIZE);
        btnDown.setPrefSize(TOGGLE_SIZE,TOGGLE_SIZE);
        btnRight.setPrefSize(TOGGLE_SIZE,TOGGLE_SIZE);
        btnUp.setPrefSize(TOGGLE_SIZE,TOGGLE_SIZE);




    }

    private void update(){

    }
}
