package pt.isec.pa.deepsea.model.data.cell;

import pt.isec.pa.deepsea.model.data.entity.Entity;

import java.io.Serializable;

/**
 * Represents a single cell of the sea floor grid.
 * Each underwater cell may contain one entity and stores whether it is revealed or not.
 *
 * @author João Carvalho - 2024126292
 * @version v1.0.0 (Stage 1)
 */
public class UnderwaterCell implements Serializable {
    private static final long serialVersionUID = 1L;
    private Entity ocupant;
    public boolean revealed;

    /**
     * Creates an empty underwater cell.
     * By default, the cell is not revealed.
     */
    public UnderwaterCell() {
        revealed = false ;
    }

    /**
     * Creates an underwater cell with an entity.
     * The cell starts unrevealed.
     *
     * @param something entity to place in the cell
     */
    public UnderwaterCell(Entity something) {
        revealed = false;
        if (something != null) {
            ocupant = something;
        }
    }

    /**
     * Gets the entity stored in the cell.
     * A clone is returned to protect encapsulation.
     *
     * @return cloned entity in the cell, or null if empty
     */
    public Entity getEntity() {
        if (ocupant == null)
            return null;
        return ocupant.clone();
    }

    /**
     * Sets the entity stored in the cell.
     *
     * @param ocupant entity to place in the cell
     */
    public void setEntity(Entity ocupant) {
        this.ocupant = ocupant;
    }

    /**
     * Creates a copy of this underwater cell.
     * If an entity exists, it is also cloned.
     *
     * @return cloned underwater cell
     */
    public UnderwaterCell clone() {
        UnderwaterCell nova = new UnderwaterCell();

        if (this.ocupant != null)
            nova.ocupant = this.ocupant.clone();

        nova.revealed = this.revealed;
        return nova;
    }

    /**
     * Sets whether the cell is revealed.
     *
     * @param veredict true if the cell should be revealed, false otherwise
     */
    public void setRevealed(boolean veredict) {
        revealed = veredict;
    }

    /**
     * Checks whether the cell is revealed.
     *
     * @return true if revealed, false otherwise
     */
    public boolean isRevealed() {
        return revealed;
    }
}