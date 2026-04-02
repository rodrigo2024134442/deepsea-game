package pt.isec.pa.deepsea.model.data.entity.monster;

import pt.isec.pa.deepsea.model.data.entity.LivingEntity;

public class Monster extends LivingEntity {
    private final String speak= "Roar!!! Coff...Coff...";

    public Monster(int id, String name, int health) {
        super(id, name, health);
    }

    public Monster(Monster other) {
        super(other.getId(), other.getName(), other.getHealth());
    }

    @Override
    public Monster clone() {
        return new Monster(this);
    }
}
