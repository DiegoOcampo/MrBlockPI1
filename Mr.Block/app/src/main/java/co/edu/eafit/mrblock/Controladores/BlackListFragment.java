package co.edu.eafit.mrblock.Controladores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import co.edu.eafit.mrblock.Entidades.Complete;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.DateTime;
import co.edu.eafit.mrblock.Entidades.Type;
import co.edu.eafit.mrblock.Helper.CompleteHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.DateHelper;
import co.edu.eafit.mrblock.Helper.TypeHelper;
import co.edu.eafit.mrblock.R;

/**
 * Created by juan on 24/10/15.
 */
public class BlackListFragment extends Fragment{
    private ListView listBlackList;
    private CompleteHelper completeHelper;
    private ContactInHelper contactInHelper;
    private DateHelper dateHelper;
    private TypeHelper typeHelper;

    private Context context;

    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ArrayList<Complete> completes = new ArrayList<Complete>();
    private ArrayList<DateTime> dateTimes = new ArrayList<DateTime>();
    private ArrayList<String> Blocks = new ArrayList<String>();
    private LinkedList<Type> typesBlock = new LinkedList<>();
    private LinkedList<String> typesBlockString = new LinkedList<>();



    public static ArrayAdapter<String> adapter;

    public BlackListFragment(){


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, typesBlockString);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_black_list, container, false);
        listBlackList = (ListView)view.findViewById(R.id.listBlackList);

        contactInHelper = new ContactInHelper(context);
        completeHelper = new CompleteHelper(context);
        dateHelper = new DateHelper(context);
        typeHelper =new TypeHelper(context);

        contacts = contactInHelper.getAllContact();
        completes = completeHelper.getAllComplete();
        dateTimes = dateHelper.getAllDate();

        typesBlock = typeHelper.getAllTypes();
        for(int i = 0;i < contacts.size();i++){
            Type type = new Type(contacts.get(i).getNumber(),contacts.get(i).getType());
            if(!typesBlockString.contains(type.getType() + ": " + contacts.get(i).getName())
                    && !type.getType().equals("white contact")) {
                Blocks.add(contacts.get(i).getContact());
                typeHelper.addType(type);
                typesBlock.push(type);
                typesBlockString.push(type.getType() + ": " + contacts.get(i).getName());
            }
        }
        for(int i = 0;i < completes.size();i++){
            Type type = new Type(completes.get(i).getBlockName(),completes.get(i).getType());
            if(!typesBlockString.contains(type.getType())) {
                typeHelper.addType(type);
                typesBlock.push(type);
                typesBlockString.push(type.getType());
            }
        }
        for(int i = 0;i < dateTimes.size();i++){
            Type type = new Type(dateTimes.get(i).getDateName(),dateTimes.get(i).getType());
            if(!typesBlockString.contains(type.getType() + ": " + dateTimes.get(i).getDateName())) {
                typeHelper.addType(type);
                typesBlock.push(type);
                typesBlockString.push(type.getType() + ": " + dateTimes.get(i).getDateName());
            }
        }
        int numberType = typesBlock.size();


        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, typesBlockString);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        listBlackList.setAdapter(adapter);
        listBlackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        return view;


    }

    public ArrayList block(){
        return Blocks;
    }

    public void addCompleteToFragment(Context context){
        typeHelper = new TypeHelper(context);
        completeHelper = new CompleteHelper(context);
         try {
                    Complete complete = new Complete("Complete block", 1, 0, 0, 0, "Complete block");
                    Type type = new Type("Complete block", "Complete block");
                    typesBlock.push(type);
                    typesBlockString.push(type.getType());
                    typeHelper.addType(type);
    //                adapter.notifyDataSetChanged();
                    completeHelper.addComplete(complete);
                    Toast.makeText(context, "Every contact was blocked", Toast.LENGTH_LONG).show();
                }catch (Exception e){}
                //}else{
                //    Toast.makeText(getApplicationContext(), "Los contactos ya fueron bloqueados anteriormente", Toast.LENGTH_LONG).show();
                //}
    }

    public Contact addContactToFragment(String number, String name, String type1, Context context){
        this.context = context;
        Contact contact= new Contact(number,name,type1);
        ContactInHelper contactInHelper = new ContactInHelper(context);
        TypeHelper typeHelper =new TypeHelper(context);
        CompleteHelper completeHelper = new CompleteHelper(context);
        Complete complete = null;
        Contact contact1 = null;
        try{
            contact1 = contactInHelper.getContact(number);
        }catch (Exception e){

        }

        try{
            complete = completeHelper.getComplete("Complete block");
        }catch (Exception e){

        }

        if (contact1 == null && complete==null) {
                contactInHelper.addContact(contact);
                //contactDbHelper.addContact(contact);

                contacts.add(contact);
                Blocks.add(contact.getContact());
                Type type = new Type(contact.getNumber(), contact.getType());
                typesBlock.push(type);
                typesBlockString.push(type.getType() + ": " + contact.getName());
                typeHelper.addType(type);

                //adapter.notifyDataSetChanged();
                Toast.makeText(context, "Contact added: \n" + contactInHelper.getContact(contact.getNumber()).getContact(), Toast.LENGTH_LONG).show();
            } else if(complete!=null){
                Toast.makeText(context, "Every contact is blocked", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, "contact already exists", Toast.LENGTH_LONG).show();
        }
        return contact;

    }

    private void openDetailsBlock(final int position){
        //Collections.reverse(typesBlock);
        final Type type = typesBlock.get(position);
        final String id = type.getId();
        final String blocktype = type.getType();
        final AlertDialog.Builder alertName = new AlertDialog.Builder(context);
        alertName.setTitle("Details");
        if(blocktype.equals("contact")){
            Contact con = contactInHelper.getContact(id);
            alertName.setMessage("type: " + blocktype + "\n" +
                    "name: " + con.getName() + "\n" + "number: " + con.getNumber());
        }else if(blocktype.equals("Complete block")){
            Complete comp = completeHelper.getComplete(id);
            //alertName.setMessage("type: " + blocktype + "\n" +
            //        "name: " + comp.getBlockName());
        }else{
            DateTime date = dateHelper.getDate(id);
            alertName.setMessage("type: " + date.getType() + "\n" + "name: " + date.getDateName());
        }
        alertName.setCancelable(false);
        alertName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertName.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (type.getType().equals("contact")) {
                    Contact contact = contactInHelper.getContact(id);
                    contactInHelper.delete(contact);
                } else if (type.getType().equals("Complete block")) {
                    Complete complete = completeHelper.getComplete(id);
                    completeHelper.delete(id);
                } else {
                    DateTime dateTime = dateHelper.getDate(id);
                    dateHelper.delete(id);
                }
                typesBlock.remove(position);
                typesBlockString.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
            }
        });


        alertName.show();

    }


}
