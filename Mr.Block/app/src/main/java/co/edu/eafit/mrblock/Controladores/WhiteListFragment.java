package co.edu.eafit.mrblock.Controladores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Entidades.Complete;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.DateTime;
import co.edu.eafit.mrblock.Entidades.Type;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.TypeHelper;
import co.edu.eafit.mrblock.R;

/**
 * Created by juan on 27/10/15.
 */
public class WhiteListFragment extends Fragment {
    private ListView listWhiteList;
    private Context context;
    private ArrayAdapter<String> adapter;
    private ArrayList<Contact> whiteArrayContacts = new ArrayList<>();
    private ArrayList<String> whiteArrayString = new ArrayList<>();
    private ContactInHelper contactInHelper;
    private TypeHelper typeHelper;
    public WhiteListFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_white_list, container, false);
        context = container.getContext();
        contactInHelper = new ContactInHelper(context);
        typeHelper = new TypeHelper(context);
        whiteArrayContacts = contactInHelper.getAllContact();
        for(int i=0; i < whiteArrayContacts.size();i++){
            if(whiteArrayContacts.get(i).getType().equals("white contact")
                    && !whiteArrayString.contains(whiteArrayContacts.get(i).getContact())){
                whiteArrayString.add(whiteArrayContacts.get(i).getContact());
                contactInHelper.addContact(whiteArrayContacts.get(i));
                Type type = new Type(whiteArrayContacts.get(i).getNumber(),whiteArrayContacts.get(i).getType());
                typeHelper.addType(type);
            }
        }
        listWhiteList = (ListView) view.findViewById(R.id.listWhiteList);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, whiteArrayString);
        listWhiteList.setAdapter(adapter);
        listWhiteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                /*
                Contact contact = contacts.get(position);

                long row = contactInHelper.delete(contact);
                if(row>0){
                    contacts.remove(position);
                    Blocks.remove(position);
                }
                Toast.makeText(getApplicationContext(),"Contacto eliminado: \n" + contact.getContact(),Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();*/
                openDetailsBlock(position);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public Contact addContactToFragment(String number, String name, String type1, Context context){
        this.context = context;
        Contact contact= new Contact(number,name,type1);
        ContactInHelper contactInHelper = new ContactInHelper(context);
        TypeHelper typeHelper =new TypeHelper(context);

        //adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, typesBlockString);
        if (!whiteArrayString.contains(contact.getContact())) {
            contactInHelper.addContact(contact);
            //contactDbHelper.addContact(contact);

            whiteArrayContacts.add(contact);
            whiteArrayString.add(contact.getContact());
            Type type = new Type(contact.getNumber(), contact.getType());
            typeHelper.addType(type);

            //adapter.notifyDataSetChanged();
            Toast.makeText(context, "Contact added: \n" + contactInHelper.getContact(contact.getNumber()).getContact(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Contact already exists", Toast.LENGTH_LONG).show();
        }
        return contact;

    }


    private void openDetailsBlock(final int position){
        final Contact contact = whiteArrayContacts.get(position);
        final AlertDialog.Builder alertName = new AlertDialog.Builder(context);
        alertName.setTitle("Details");
            alertName.setMessage("type: " + contact.getType() + "\n" +
                    "name: " + contact.getName() + "\n" + "number: " + contact.getNumber());
        alertName.setCancelable(false);
        alertName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertName.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                whiteArrayContacts.remove(position);
                whiteArrayString.remove(position);
                contactInHelper.delete(contact);
                typeHelper.delete(contact.getNumber());
                adapter.notifyDataSetChanged();
                Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
            }
        });
        alertName.show();
    }
}
