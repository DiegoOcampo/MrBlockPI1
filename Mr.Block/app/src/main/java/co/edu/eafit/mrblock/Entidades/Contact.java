package co.edu.eafit.mrblock.Entidades;

/**
 * Created by juan on 19/09/15.
 */
public class Contact{

    private String number;
    private String name;
    private String type;

    public Contact(String number, String name, String type){
        this.number = number;
        this.name = name;
        this.type = type;
    }

    public Contact(){}

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
        return name + " " + number ;
    }

    public Contact contact(){
        return this;
    }


}
