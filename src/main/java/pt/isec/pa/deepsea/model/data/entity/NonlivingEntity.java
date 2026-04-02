package pt.isec.pa.deepsea.model.data.entity;

public abstract class NonlivingEntity extends Entity {

    public NonlivingEntity(int id, String name) {
        super(id, name);
    }

    @Override
    public abstract NonlivingEntity clone();
}
