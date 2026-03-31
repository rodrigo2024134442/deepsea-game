package pt.isec.pa.deepsea.model.data.drone;

/**
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 1)
 * */
public class Drone {
    // FUEL MANAGEMENT
    private int currentFuel;
    private int maxFuel;

    // HULL INTEGRITY (casco)
    private int hullIntegrity;

    // GRID POSITION
    private int posX;
    private int posY;

    // WHERE IS THE DRONE
    private DroneState state;

    // RESOURCES COLLECTION
    // private List<Content> collectedResources;

    public Drone(){
        maxFuel = 100 /* MAX_FUEL -> Após a criação do ficheiro de configuracao é necessário substituir estes valores */;
        currentFuel = maxFuel;

        this.hullIntegrity = 100 /* MAX_HULL_INTEGRITY -> Após a criação do ficheiro de configuracao é necessário substituir estes valores */;

        this.state = DroneState.ON_SHIP;
        this.posX = -1;
        this.posY = -1;

        //this.collectedResources = new ArrayList<>();
    }

    public void consumeFuel(int amount) {
        if(amount <= 0)
            return;

        currentFuel -= amount;
        if (currentFuel < 0)
            currentFuel = 0;
    }

    public void takeDamage(int damage) {
        if(damage <= 0)
            return;

        hullIntegrity -= damage;
        if (hullIntegrity < 0)
            hullIntegrity = 0;
    }

    public boolean isDestroyed() {
        return hullIntegrity == 0;
    }

    public boolean isOutOfFuel() {
        return currentFuel == 0;
    }

    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public void setState(DroneState state) {
        this.state = state;
    }

    /*
    public void addResource(Content resource) {
        collectedResources.add(resource);
    }
    */

    /* GETTERS */
    public int getCurrentFuel() {
        return currentFuel;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public int getHullIntegrity() {
        return hullIntegrity;
    }

    public DroneState getState() {
        return state;
    }

    /*
    public List<Content> getCollectedResources() {
        return collectedResources;
    }
    */
}
