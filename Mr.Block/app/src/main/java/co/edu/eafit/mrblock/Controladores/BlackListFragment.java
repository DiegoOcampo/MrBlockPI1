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
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import co.edu.eafit.mrblock.Entidades.Complete;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.DateTime;
import co.edu.eafit.mrblock.Entidades.TransitionBlock;
import co.edu.eafit.mrblock.Entidades.Type;
import co.edu.eafit.mrblock.Entidades.Ubicacion;
import co.edu.eafit.mrblock.Helper.CompleteHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.DateHelper;
import co.edu.eafit.mrblock.Helper.TransitionInHelper;
import co.edu.eafit.mrblock.Helper.TypeHelper;
import co.edu.eafit.mrblock.Helper.UbicationHelper;
import co.edu.eafit.mrblock.R;

/**
 * Created by juan on 24/10/15.
 */
public class BlackListFragment extends Fragment{
    private ListView listBlackList;
    private View textEmpty;
    private CompleteHelper completeHelper;
    private ContactInHelper contactInHelper;
    private DateHelper dateHelper;
    private TypeHelper typeHelper;
    private UbicationHelper ubicationHelper;
    private TransitionInHelper transitionInHelper;
    private Context context;
    private boolean delete;
    private ArrayList<Integer> imageId;
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ArrayList<Complete> completes = new ArrayList<Complete>();
    private ArrayList<DateTime> dateTimes = new ArrayList<DateTime>();
    private ArrayList<String> Blocks = new ArrayList<String>();
    private LinkedList<Type> typesBlock = new LinkedList<>();
    private LinkedList<String> typesBlockString = new LinkedList<>();
    private  LinkedList<Type> typesBlockShow = new LinkedList<>();

    public static ArrayAdapter<String> adapter;
    private CustomList adapter1;

    public BlackListFragment(){


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<String >(getActivity(),android.R.layout.simple_list_item_1, typesBlockString);
        adapter1 = new CustomList(getActivity(), typesBlockString, imageId);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();


        imageId= new ArrayList<>();

        delete = false;
        View view = inflater.inflate(R.layout.fragment_black_list, container, false);
        listBlackList = (ListView)view.findViewById(R.id.listBlackList);
        textEmpty = (View)view.findViewById(R.id.textEmptyBlack);

        contactInHelper = new ContactInHelper(context);
        completeHelper = new CompleteHelper(context);
        dateHelper = new DateHelper(context);
        typeHelper =new TypeHelper(context);
        ubicationHelper = new UbicationHelper(context);
        transitionInHelper = new TransitionInHelper(context);

        contacts = contactInHelper.getAllContact();
        completes = completeHelper.getAllComplete();
        dateTimes = dateHelper.getAllDate();

        typesBlock = typeHelper.getAllTypes();
        if(typesBlockString.size()==0) {
            for (int i = 0; i < typesBlock.size(); i++) {
                if(!typesBlock.get(i).getType().equals("white contact")) {
                    typesBlockString.add(typesBlock.get(i).getType() + ": " + typesBlock.get(i).getId());
                    typesBlockShow.add(typesBlock.get(i));
                    /*if(typesBlock.get(i).getType().equals("contact")){
                        imageId.add(R.mipmap.ic_contact);
                    }else if(typesBlock.get(i).getType().equals("Complete block")){
                        imageId.add(R.mipmap.ic_all);
                    }else if(typesBlock.get(i).getType().equals("location")){
                        imageId.add(R.mipmap.ic_location);
                    }else{
                        imageId.add(R.mipmap.ic_date);
                    }*/
                }
            }
        }

        for(int i = 0; i < typesBlock.size(); i++){
            if(!typesBlock.get(i).getType().equals("white contact")) {
                if (typesBlock.get(i).getType().equals("contact")) {
                    imageId.add(R.mipmap.ic_contact);
                } else if (typesBlock.get(i).getType().equals("Complete block")) {
                    imageId.add(R.mipmap.ic_all);
                } else if (typesBlock.get(i).getType().equals("location")) {
                    imageId.add(R.mipmap.ic_location);
                } else {
                    imageId.add(R.mipmap.ic_date);
                }
            }
        }
     /*   if(typesBlockString.size() == 0){
            textEmpty.setText("The list is empty");
        }
*/

        adapter1 = new CustomList(getActivity(), typesBlockString, imageId);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, typesBlockString);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        listBlackList.setAdapter(adapter1);
        listBlackList.setEmptyView(textEmpty);
        listBlackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                    typeHelper.addType(type);
                    completeHelper.addComplete(complete);
                    Toast.makeText(context, R.string.black_every_contact_was_blocked, Toast.LENGTH_LONG).show();
                }catch (Exception e){}
                //}else{
                //    Toast.makeText(getApplicationContext(), "Los contactos ya fueron bloqueados anteriormente", Toast.LENGTH_LONG).show();
                //}
    }

    public Contact addContactToFragment(String number, String name, String type1, Context context){
        this.context = context;
        if(number.charAt(0) == '+'){
            number = number.substring(3);
        }
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
                typeHelper.addType(type);

                //adapter.notifyDataSetChanged();
                Toast.makeText(context, context.getString(R.string.black_contact_added) + contactInHelper.getContact(contact.getNumber()).getContact(), Toast.LENGTH_LONG).show();
            } else if(complete!=null){
                Toast.makeText(context, R.string.black_every_contact_blocked, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, R.string.black_contact_already, Toast.LENGTH_LONG).show();
        }
        return contact;

    }



    private void openDetailsBlock(final int position) {
        //Collections.reverse(typesBlock);
        //try {
        final Type type = typesBlockShow.get(position);
        final String id = type.getId();
        final String blocktype = type.getType();
                final AlertDialog.Builder alertName = new AlertDialog.Builder(context);
                alertName.setTitle(R.string.black_details);
                if (blocktype.equals("contact")) {
                    Contact con = contactInHelper.getContact(id);
                    alertName.setMessage(context.getString(R.string.black_type) + con.getType() + "\n" +
                            context.getString(R.string.black_name) + con.getName() + "\n" +
                            context.getString(R.string.black_number) + con.getNumber());
                } else if (blocktype.equals("Complete block")) {
                    Complete comp = completeHelper.getComplete(id);
                    alertName.setMessage(context.getString(R.string.black_type) + comp.getType() + "\n" +
                            context.getString(R.string.black_name) + comp.getBlockName());
                } else if(blocktype.equals("location")) {
                    Ubicacion ubicacion = ubicationHelper.getUbication(id);
                    alertName.setMessage(context.getString(R.string.black_name) + ubicacion.getName() + "\n" +
                            context.getString(R.string.black_radius) + ubicacion.getRadio());
                }else{
                    DateTime date = dateHelper.getDate(id);
                    alertName.setMessage(context.getString(R.string.black_type) + date.getType() + "\n" + context.getString(R.string.black_name) + date.getDateName());
                }
                alertName.setCancelable(false);
                alertName.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alertName.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (type.getType().equals("contact")) {
                            Contact contact = contactInHelper.getContact(id);
                            contactInHelper.delete(contact);
                        } else if (type.getType().equals("Complete block")) {
                            completeHelper.delete(id);
                        } else if (type.getType().equals("location")) {
                            Ubicacion ubicacion = ubicationHelper.getUbication(id);
                            ubicationHelper.delete(ubicacion);
                            transitionInHelper.deleteAll();
                            TransitionBlock transition = new TransitionBlock("location", 0);
                            transitionInHelper.addTransition(transition);
                        } else {
                            DateTime dateTime = dateHelper.getDate(id);
                            dateHelper.delete(id);
                        }

                        imageId.remove(position);
                        typesBlock.remove(position);
                        typesBlockShow.remove(position);
                        typesBlockString.remove(position);
                        typeHelper.delete(type.getId());

                        adapter1.notifyDataSetChanged();

                        Toast.makeText(context, R.string.deleted, Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(getContext(), MainFragmentActivity.class);
                        //startActivity(intent);
                    }
                });
                alertName.show();

       // }catch (Exception e){
        //    Toast.makeText(context,"error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        //}

    }


}
