package co.edu.eafit.mrblock.Controladores;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;

import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.DateHelper;
import co.edu.eafit.mrblock.R;
import co.edu.eafit.mrblock.SingletonContact;
//parse
//


public class MainActivity extends AppCompatActivity {
    private ListView listView, listDrawer;
    private String [] items = {"Bloqueados S", "Bloqueados E","Bloquear TS", "Bloquear TE", "Desbloquear TS", "Desbloquear TE", "Fecha", "Llamadas"};
    private ArrayList<String> Bloqueados= new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterItems;
    public static boolean check = false, check2=false;
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ContactInHelper contactInHelper;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listDrawer = (ListView) findViewById(R.id.left_drawer);
        listView = (ListView) findViewById(R.id.listView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        contactInHelper = new ContactInHelper(getApplicationContext());
        contacts = contactInHelper.getAllContact();

        for(int i = 0;i < contacts.size();i++){
            Bloqueados.add(contacts.get(i).getContact());
        }

        adapterItems = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Bloqueados);

        listView.setAdapter(adapter);
        listDrawer.setAdapter(adapterItems);

        listDrawer.setOnItemClickListener(new DrawerItemClickListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //SingletonContact singletonContact = SingletonContact.getInstance();
                //Contact contact = singletonContact.getAtIndex(position);
                Contact contact = contacts.get(position);

                long row = contactInHelper.delete(contact);
                if(row>0){
                    contacts.remove(position);
                    Bloqueados.remove(position);
                }
                //contactDbHelper.delete(contact);
                //singletonContact.deleteContact(position);
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

                        if(!Bloqueados.contains(contact.getContact())){
                            SingletonContact sincontact = SingletonContact.getInstance();
                            sincontact.addContact(contact);
                            contactInHelper.addContact(contact);
                            //contactDbHelper.addContact(contact);
                            contacts.add(contact);
                            Bloqueados.add(contact.getContact());
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
        if(items[position].equals(items[1])){
            // user BoD suggests using Intent.ACTION_PICK instead of .ACTION_GET_CONTENT to avoid the chooser
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            startActivityForResult(intent, 1);
        }else if(items[position].equals(items[2])){

        }else if(items[position].equals(items[3])){
            check=true;
            try {
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                while (phones.moveToNext()) {

                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contact contact = new Contact(phoneNumber,name);
                    if(!Bloqueados.contains(contact.getContact())) {
                        contactInHelper.addContact(contact);
                        contacts.add(contact);
                        Bloqueados.add(contact.getContact());
                        adapter.notifyDataSetChanged();
                    }

                }
                phones.close();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else if(items[position].equals(items[4])){

        }else if(items[position].equals(items[5])){
            check=false;
            int length = contacts.size();
            try {
                for (int index = length - 1; index >= 0; index--) {
                    Contact contact = new Contact();
                    contact = contacts.get(index);
                    long row = contactInHelper.delete(contact);
                    if (row > 0) {
                        contacts.remove(index);
                        Bloqueados.remove(index);
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
        }
        mDrawerLayout.closeDrawer(listDrawer);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }

    }
}





