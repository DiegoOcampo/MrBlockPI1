package co.edu.eafit.mrblock.Controladores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Entidades.Complete;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.DateTime;
import co.edu.eafit.mrblock.Entidades.Type;
import co.edu.eafit.mrblock.Helper.CompleteHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.DateHelper;
import co.edu.eafit.mrblock.Helper.TypeHelper;
import co.edu.eafit.mrblock.R;
//parse
//


public class MainActivity extends AppCompatActivity {
    private ListView listView, listDrawer;
    private DrawerLayout mDrawerLayout;
    private String [] items = {"Bloquear contacto", "Bloquear todo", "Desbloquear todo", "Bloquear fecha", "Llamadas", "Bloquear posicion","Lista blanca", "Bloquear app"};

    //private String [] items = { 0"Bloqueados S", 1"Bloquear contacto",2"Bloquear TS", 3"Bloquear tdo", 4"Desbloquear TS", 5"Desbloquear tdo", 6"Bloquear fecha", 7"Llamadas", 8"Bloquear posicion", 9"Bloquear app"};


    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ArrayList<Complete> completes = new ArrayList<Complete>();
    private ArrayList<DateTime> dateTimes = new ArrayList<DateTime>();
    private ArrayList<String> Blocks = new ArrayList<String>();
    public ArrayList<Type> typesBlock = new ArrayList<Type>();
    public ArrayList<String> typesBlockString = new ArrayList<String>();


    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterItems;


    private CompleteHelper completeHelper;
    private ContactInHelper contactInHelper;
    private DateHelper dateHelper;
    private TypeHelper typeHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listDrawer = (ListView) findViewById(R.id.left_drawer);
        listView = (ListView) findViewById(R.id.listView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        contactInHelper = new ContactInHelper(getApplicationContext());
        completeHelper = new CompleteHelper(getApplicationContext());
        dateHelper = new DateHelper(getApplicationContext());
        typeHelper =new TypeHelper(getApplicationContext());

        contacts = contactInHelper.getAllContact();
        completes = completeHelper.getAllComplete();
        dateTimes = dateHelper.getAllDate();
        typesBlock = typeHelper.getAllTypes();

        for(int i = 0;i < contacts.size();i++){
            Blocks.add(contacts.get(i).getContact());
            Type type1 = typeHelper.getType(contacts.get(i).getNumber());
                Type type = new Type(contacts.get(i).getNumber(), contacts.get(i).getType());
                typeHelper.addType(type);
                typesBlock.add(type);
                typesBlockString.add(type.getType() + ": " + contacts.get(i).getName());

        }
        for(int i = 0;i < completes.size();i++){
            Type type = new Type(completes.get(i).getBlockName(),completes.get(i).getType());
            typeHelper.addType(type);
            typesBlock.add(type);
            typesBlockString.add(type.getType());

        }
        for(int i = 0;i < dateTimes.size();i++){
            Type type = new Type(dateTimes.get(i).getDateName(),dateTimes.get(i).getType());
            try {
                typeHelper.addType(type);
                typesBlock.add(type);
                typesBlockString.add(type.getType() + ": " + dateTimes.get(i).getDateName());
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }


        adapterItems = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, typesBlockString);

        listView.setAdapter(adapter);
        listDrawer.setAdapter(adapterItems);

        listDrawer.setOnItemClickListener(new DrawerItemClickListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,},
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String name = c.getString(0);
                        String number = c.getString(1).replaceAll(" ", "");
                        Contact contact = new Contact(number,name,"contact");

                        if(!Blocks.contains(contact.getContact())){
                            contactInHelper.addContact(contact);
                            //contactDbHelper.addContact(contact);
                            contacts.add(contact);
                            Blocks.add(contact.getContact());
                            Type type = new Type(contact.getNumber(),contact.getType());
                            typesBlock.add(type);
                            typesBlockString.add(type.getType() + ": " + contact.getName());
                            typeHelper.addType(type);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"Contacto agregado: \n"+contactInHelper.getContact(contact.getNumber()).getContact(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"El contacto ya existe.",Toast.LENGTH_LONG).show();
                        }
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_contact_block) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItem(int position) {
        listDrawer.setItemChecked(position, true);
        switch (position){
            case 0:
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent1, 1);
                break;
            case 1:
                openAlertBlock();
                break;
            case 2:
                //Complete complete = completeHelper.getComplete("Complete block");
                Type type = typeHelper.getType("Complete block");
                completeHelper.delete("Complete block");
                typesBlock.remove(type);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Todos los contactos han sido desbloqueados",Toast.LENGTH_LONG).show();
                break;
            case 3:
                Intent intent2 = new Intent(getBaseContext(), Alarm.class);
                startActivity(intent2);
                break;
            case 4:
                Intent intent3 = new Intent(getApplicationContext(), CallsInListActivity.class);
                startActivity(intent3);
                break;
            case 5:
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(i);
                break;



        }


        mDrawerLayout.closeDrawer(listDrawer);
    }

    private void openAlertBlock(){

        final AlertDialog.Builder alertName = new AlertDialog.Builder(MainActivity.this);
        alertName.setTitle("Advertencia");
        alertName.setMessage("Esta seguro de bloquear todos los contactos?");
        //final EditText dateName = new EditText(MainActivity.this);
        //alertName.setView(dateName);
        alertName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //String editTextName = dateName.getText().toString();
                //Type type1 = typeHelper.getType("Complete block");
                //if(type1==null) {
                try {
                    Complete complete = new Complete("Complete block", 1, 0, 0, 0, "Complete block");
                    Type type = new Type("Complete block", "Complete block");
                    typesBlock.add(type);
                    typesBlockString.add(type.getType());
                    typeHelper.addType(type);
                    adapter.notifyDataSetChanged();
                    completeHelper.addComplete(complete);
                    Toast.makeText(getApplicationContext(), "Todos los contactos han sido bloqueados", Toast.LENGTH_LONG).show();
                }catch (Exception e){}
                //}else{
                //    Toast.makeText(getApplicationContext(), "Los contactos ya fueron bloqueados anteriormente", Toast.LENGTH_LONG).show();
                //}
            }
        });

        alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(getApplicationContext(),"Bloqueo cancelado",Toast.LENGTH_LONG).show();
            }
        });
        alertName.show();

    }

    private void openDetailsBlock(final int position){
        final Type type = typesBlock.get(position);
        final String id = type.getId();
        final String blocktype = type.getType();
        final AlertDialog.Builder alertName = new AlertDialog.Builder(MainActivity.this);
        alertName.setTitle("Detalles");
        if(blocktype.equals("contact")){
            Contact con = contactInHelper.getContact(id);
            alertName.setMessage("type: " + blocktype + "\n" +
            "name: " + con.getName() + "\n" + "number: " + con.getNumber());
        }else if(blocktype.equals("Complete block")){
            Complete comp = completeHelper.getComplete(id);
            alertName.setMessage("type: " + blocktype + "\n" +
                    "name: " + comp.getBlockName());
        }else{
            DateTime date = dateHelper.getDate(id);
            alertName.setMessage("type: " + blocktype + "\n" + "name: " + date.getDateName());
        }
        alertName.setCancelable(false);
        alertName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertName.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
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
                Toast.makeText(getApplicationContext(), "Elimindado", Toast.LENGTH_LONG).show();
            }
        });


        alertName.show();

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }

    }


}





