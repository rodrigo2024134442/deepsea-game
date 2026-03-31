package pt.isec.pa.deepsea.model;

import pt.isec.pa.deepsea.model.data.ship.Ship;
import pt.isec.pa.deepsea.model.data.surface.SeaSurface;

/**
 * The Game class represents the main entry point of the data model for the
 * "Deep Sea Mining" application.
 *
 * <p>This class is responsible for initializing and aggregating all core
 * components of the game, namely the surface grid and the player's ship.</p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Initialize the game state</li>
 *   <li>Create and generate the surface grid</li>
 *   <li>Create the player's ship</li>
 *   <li>Provide access to core game components</li>
 * </ul>
 *
 * @author Leonardo Matos Ferreira - 2024113113 
 * @author Beja - 2024134442 added SeaSurface and Ship functionality
 * @version v1.0.0 (Stage 1)
 */

public class Game {
    private SeaSurface seaSurface;
    private Ship ship;


    private void initialize (){
        /* Initialize sea surface and ship */
        seaSurface = new SeaSurface();
        //seaSurface.generate(DEFAULT_ARTIFACT_COUNT);
        ship = new Ship();
    }

    /** Starts the game. */
    public void start() { 
        initialize(); 
    }

    /* GETTERS */

    /** Gets surface grid. */
    public SeaSurface getSurfaceGrid(){ 
        return seaSurface; 
    }

    /** Gets ship. */
    public Ship getShip(){ 
        return ship; 
    }
}
