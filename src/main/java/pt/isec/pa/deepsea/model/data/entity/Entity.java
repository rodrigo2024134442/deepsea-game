package pt.isec.pa.deepsea.model.data.entity;

import static pt.isec.pa.deepsea.model.data.GameConfig.ID_START;

public abstract class Entity {
    private static int nextId = ID_START;
    private final int id;
    private final String name;

    public Entity(String name) {
        this.id = nextId++;
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