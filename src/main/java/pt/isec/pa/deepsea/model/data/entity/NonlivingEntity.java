package pt.isec.pa.deepsea.model.data.entity;

public abstract class NonlivingEntity extends Entity {

    public NonlivingEntity(String name) {
        super(name);
    }

    @Override
    public abstract NonlivingEntity clone();
}
