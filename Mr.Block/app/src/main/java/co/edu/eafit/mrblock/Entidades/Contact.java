package co.edu.eafit.mrblock.Entidades;

/**
 * Created by juan on 19/09/15.
 */
public class Contact {

    private String number;
    private String name;

    public Contact(String number, String name){
        this.number = number;
        this.name = name;
    }

    public Contact(){}

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

    public Contact contact(){
        return this;
    }


}
