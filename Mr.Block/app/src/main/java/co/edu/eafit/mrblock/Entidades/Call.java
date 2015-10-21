package co.edu.eafit.mrblock.Entidades;

/**
 * Created by juan on 30/09/15.
 */
public class Call {
    private String number;
    private String name;
    private String type;

    public Call(String number, String name, String type){
        this.number = number;
        this.name = name;
        this.type = type;
    }

    public Call(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact(){
        return name + "\n" + number ;
    }

    public Call contact(){
        return this;
    }
}
