package co.edu.eafit.mrblock;

import java.util.ArrayList;

/**
 * Created by juan on 19/09/15.
 */
public class SinContacto {
    public ArrayList<Contacto> contactos;
    private static SinContacto ourInstance = new SinContacto();

    public static SinContacto getInstance() {
        return ourInstance;
    }

    private SinContacto() {
        contactos = new ArrayList<Contacto>();
    }

    public Contacto getAtIndex(int i) {
        return contactos.get(i);
    }

    public void addContact(Contacto c) {
        contactos.add(c);
    }
}
