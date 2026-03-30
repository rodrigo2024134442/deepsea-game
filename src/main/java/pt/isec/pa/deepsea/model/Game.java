package pt.isec.pa.deepsea.model;

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
 * @version v1.0.0 (Stage 1)
 */
public class Game {
    // private SeaSurface seaSurface;
    // private Ship ship;

    private void initialize (){
        /* INITIALIZE VARIABLES (Sea surface & ship) */
        // seaSurface = new SeaSurface();
        // seaSurface.generate();

        // ship = new Ship(seaSurface);
    }

    public void start() { initialize(); }

    /* GETTERS */
    // public SeaSurface getSurfaceGrid(){ return seaSurface; }
    // public Ship getShip(){ return ship; }
}
