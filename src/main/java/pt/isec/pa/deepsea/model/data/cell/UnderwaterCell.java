package pt.isec.pa.deepsea.model.data.cell;

import pt.isec.pa.deepsea.model.data.entity.Entity;

public class UnderwaterCell {
    private Entity ocupant;
    public boolean revealed;

    public UnderwaterCell(){};

    public UnderwaterCell(Entity something) {
        revealed = false;
        if(something!=null){
            ocupant = something;
        }
    }

    public Entity getEntity() {
        if (ocupant == null)
            return null;
        return ocupant.clone();
    }

    public void setEntity (Entity ocupant) {this.ocupant = ocupant;}

    public UnderwaterCell clone() {
        UnderwaterCell nova = new UnderwaterCell();

        if (this.ocupant != null)
            nova.ocupant = this.ocupant.clone();

        return nova;
    }

    public void setRevealed(boolean veredict) {
        revealed = veredict;
    }

    public boolean isRevealed(){return revealed;}
}
