package co.edu.eafit.mrblock.Controladores;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import co.edu.eafit.mrblock.ContactDbHelper;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.R;
import co.edu.eafit.mrblock.SingletonContact;
//parse
//


public class MainActivity extends AppCompatActivity {
    ListView listView, listDrawer;
    public static final int PICK_CONTACT    = 1;
    private Button btnContacts;
    String [] items = {"Bloqueados S", "Bloqueados E"};
    public static ArrayList<String> Bloqueados= new ArrayList<String>();
    ArrayAdapter<String> adapter, adapterItems;
    public static boolean check = false, check2=false;
    ArrayList<String> contacts = new ArrayList<String>();
    ContactDbHelper contactDbHelper;
    private DrawerLayout mDrawerLayout;


    Button buttonstartSetDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listDrawer = (ListView) findViewById(R.id.left_drawer);
        listView = (ListView) findViewById(R.id.listView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        contactDbHelper = new ContactDbHelper(getApplicationContext());
        contacts = contactDbHelper.getAllContact();

        adapterItems = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Bloqueados);

        listView.setAdapter(adapter);
        listDrawer.setAdapter(adapterItems);

        listDrawer.setOnItemClickListener(new DrawerItemClickListener());
        btnContacts = (Button) findViewById(R.id.btn_contacts);



        buttonstartSetDialog = (Button) findViewById(R.id.startAlaram);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), Alarm.class);
                startActivity(i);
            }
        });



        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // user BoD suggests using Intent.ACTION_PICK instead of .ACTION_GET_CONTENT to avoid the chooser
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bloqueados.remove(position);
                SingletonContact singletonContact = SingletonContact.getInstance();
                Contact contact = singletonContact.getAtIndex(position);

                contacts.remove(position);
                contactDbHelper.delete(contact);
                singletonContact.deleteContact(position);

                adapter.notifyDataSetChanged();
            }
        });
        Switch onOffSwitch = (Switch)  findViewById(R.id.switch1);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check = isChecked;
                Log.v("Switch State=", "" + isChecked);

            }
        });

        Switch onOffSwitchI = (Switch)  findViewById(R.id.switch2);
        onOffSwitchI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check2 = isChecked;
                Log.v("Switch State=", "" + isChecked);

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






                        SingletonContact sincontact = SingletonContact.getInstance();
                        sincontact.addContact(contact);

                        contactDbHelper.addContact(contact);

                        contacts.add(contact.getContact());
                        Bloqueados.add(contact.getContact());
                        adapter.notifyDataSetChanged();

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





