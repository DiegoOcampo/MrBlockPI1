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
import co.edu.eafit.mrblock.Helper.CompleteHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.DateHelper;
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
    private ArrayList<String> typesBlock = new ArrayList<String>();

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterItems;


    private CompleteHelper completeHelper;
    private ContactInHelper contactInHelper;
    private DateHelper dateHelper;
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


        contacts = contactInHelper.getAllContact();
        completes = completeHelper.getAllComplete();
        dateTimes = dateHelper.getAllDate();


        for(int i = 0;i < contacts.size();i++){
            Blocks.add(contacts.get(i).getContact());
            typesBlock.add(contacts.get(i).getContact());
        }
        for(int i = 0;i < completes.size();i++){
            typesBlock.add(completes.get(i).getBlockName());
        }
        for(int i = 0;i < dateTimes.size();i++){
            typesBlock.add(dateTimes.get(i).getDateName());
        }


        adapterItems = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, typesBlock);

        listView.setAdapter(adapter);
        listDrawer.setAdapter(adapterItems);

        listDrawer.setOnItemClickListener(new DrawerItemClickListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Contact contact = contacts.get(position);

                long row = contactInHelper.delete(contact);
                if(row>0){
                    contacts.remove(position);
                    Blocks.remove(position);
                }
                Toast.makeText(getApplicationContext(),"Contacto eliminado: \n" + contact.getContact(),Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
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
                        Contact contact = new Contact(number,name);

                        if(!Blocks.contains(contact.getContact())){
                            contactInHelper.addContact(contact);
                            //contactDbHelper.addContact(contact);
                            contacts.add(contact);
                            Blocks.add(contact.getContact());
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
        if (id == R.id.action_settings) {
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
                completeHelper.delete("Complete block");
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


       /* if(items[position].equals(items[0])){
            // user BoD suggests using Intent.ACTION_PICK instead of .ACTION_GET_CONTENT to avoid the chooser
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            startActivityForResult(intent, 1);
        }else if(items[position].equals(items[2])){

        }else if(items[position].equals(items[3])){
            openAlertBlock();*/
            //check=true;
                    /*
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                            while (phones.moveToNext()) {

                                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).replace(" ","");
                                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ","");
                                Contact contact = new Contact(phoneNumber, name);
                                if (!Blocks.contains(contact.getContact())) {
                                    contactInHelper.addContact(contact);
                                    contacts.add(contact);
                                    Blocks.add(contact.getContact());
                                }

                                adapter.notifyDataSetChanged();
                            }
                            phones.close();*/
/*

        }else if(items[position].equals(items[4])){

        }else if(items[position].equals(items[5])){
            completeHelper.delete("Complete block");
            Toast.makeText(getApplicationContext(),"Todos los contactos han sido desbloqueados",Toast.LENGTH_LONG).show();
            /*check=false;
            contactInHelper.deleteAll();
            Blocks.clear();
            contacts.clear();
            adapter.notifyDataSetChanged();
            */
            /*
            int length = contacts.size();
            try {
                for (int index = length - 1; index >= 0; index--) {
                    Contact contact = contacts.get(index);
                    long row = contactInHelper.delete(contact);
                    if (row > 0) {
                        contacts.remove(index);
                        Blocks.remove(index);
                        adapter.notifyDataSetChanged();
                    }

                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }else if(items[position].equals(items[6])){
            Intent i = new Intent(getBaseContext(), Alarm.class);
            startActivity(i);
        }else if(items[position].equals(items[7])){
            Intent intent = new Intent(getApplicationContext(), CallsInListActivity.class);
            startActivity(intent);
        }else if(items[position].equals(items[8])){
            Intent i = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(i);
        }else if(items[position].equals(items[9])){

        }*/
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
                Complete complete = new Complete("Complete block",1,0,0,0,"Complete block");
                completeHelper.addComplete(complete);
                Toast.makeText(getApplicationContext(),"Todos los contactos han sido bloqueados",Toast.LENGTH_LONG).show();
            }
        });

        alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(getApplicationContext(),"Bloqueo cancelado",Toast.LENGTH_LONG).show();
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





