package pt.isec.pa.deepsea.model.data.cell;

import pt.isec.pa.deepsea.model.data.entity.NonlivingEntity;

public class UnderwaterCell {
    private NonlivingEntity ocupant;

    public UnderwaterCell() {}

    public NonlivingEntity getEntity() {
        if (ocupant == null)
            return null;
        return ocupant.clone();
    }

    public void setEntity (NonlivingEntity ocupant) {this.ocupant = ocupant;}

    public UnderwaterCell clone() {
        UnderwaterCell nova = new UnderwaterCell();

        if (this.ocupant != null)
            nova.ocupant = this.ocupant.clone();

        return nova;
    }

}
