package co.edu.eafit.mrblock.Entidades;

/**
 * Created by juan on 30/09/15.
 */
public class Call {
    private String number;
    private String name;

    public Call(String number, String name){
        this.number = number;
        this.name = name;
    }

    public Call(){}

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
