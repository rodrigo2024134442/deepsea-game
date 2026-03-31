package pt.isec.pa.deepsea.model.data.trench;

public class TrenchCell {
    private TrenchCellType type;

    public TrenchCell(TrenchCellType type) {
        this.type = type;
    }

    public TrenchCellType getType() {
        return type;
    }

    public void setType(TrenchCellType type) {
        if(type != null)
            this.type = type;
    }
}