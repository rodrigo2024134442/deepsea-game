package pt.isec.pa.deepsea.model.data.entity;

public abstract class LivingEntity extends Entity {
    private int health;

    public LivingEntity(String name, int health) {
        super(name);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public abstract LivingEntity clone();
}