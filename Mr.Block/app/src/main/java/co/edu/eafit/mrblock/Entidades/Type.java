package co.edu.eafit.mrblock.Entidades;

/**
 * Created by juan on 20/10/15.
 */
public class Type {
    private String id, type;

    public Type(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
