package pt.isec.pa.deepsea.model.data.cell;

import pt.isec.pa.deepsea.model.data.Entities;

public class UnderwaterCell {
    private Entities ocupant;

    public UnderwaterCell() {}

    public Entities getEntity() {
        if (ocupant == null)
            return null;
        return ocupant.clone();
    }

    public void setEntity (Entities ocupant) {this.ocupant = ocupant;}

    public UnderwaterCell clone() {
        UnderwaterCell nova = new UnderwaterCell();

        if (this.ocupant != null)
            nova.ocupant = this.ocupant.clone();

        return nova;
    }

}
