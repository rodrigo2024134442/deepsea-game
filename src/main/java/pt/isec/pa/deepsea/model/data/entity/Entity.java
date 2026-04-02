package pt.isec.pa.deepsea.model.data.entity;

public abstract class Entity {
    private final int id;
    private final String name;

    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Entity clone();

    public int getId() {
        return id;
    }
}