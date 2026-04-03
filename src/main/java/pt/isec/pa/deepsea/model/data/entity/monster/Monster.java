package pt.isec.pa.deepsea.model.data.entity.monster;

import pt.isec.pa.deepsea.model.data.entity.LivingEntity;

public class Monster extends LivingEntity {
    private final String speak= "Roar!!! Coff...Coff...";

    public Monster(String name, int health) {
        super(name, health);
    }

    public Monster(Monster other) {
        super(other.getName(), other.getHealth());
    }

    @Override
    public Monster clone() {
        return new Monster(this);
    }

    public String talk (){return speak;}
}
