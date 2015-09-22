package co.edu.eafit.mrblock;

import java.util.ArrayList;

/**
 * Created by juan on 19/09/15.
 */
public class SingletonContact {
    public ArrayList<Contact> contacts;
    private static SingletonContact ourInstance = new SingletonContact();

    public static SingletonContact getInstance() {
        return ourInstance;
    }

    private SingletonContact() {
        contacts = new ArrayList<Contact>();
    }

    public Contact getAtIndex(int i) {
        return contacts.get(i);
    }

    public void addContact(Contact c) {
        contacts.add(c);
    }

    public void deleteContact(int position){contacts.remove(position);}
}
