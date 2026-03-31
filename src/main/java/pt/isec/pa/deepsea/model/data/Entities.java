package pt.isec.pa.deepsea.model.data;

public class Entities {
    int id;

    public Entities (){};

    public Entities(Entities other) {
        this.id = other.id;
    }

    public Entities clone(){
        return new Entities(this);
    }
}
