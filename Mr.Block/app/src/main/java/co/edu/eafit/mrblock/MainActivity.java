package co.edu.eafit.mrblock;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
//parse
//


public class MainActivity extends AppCompatActivity {
    ListView listView;
    public static final int PICK_CONTACT    = 1;
    private Button btnContacts;
    public static ArrayList<String> Bloqueados= new ArrayList<String>();
    ArrayAdapter<String> adapter;
    public static boolean check = false;
    //TextView textDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bloqueados.add("hello");
        Bloqueados.add("world");
        Bloqueados.add("!!!");
        Bloqueados.add("(Y)");
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Bloqueados);
        listView.setAdapter(adapter);
        //Bloqueados.add("3004839486");
        btnContacts = (Button) findViewById(R.id.btn_contacts);
        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // user BoD suggests using Intent.ACTION_PICK instead of .ACTION_GET_CONTENT to avoid the chooser
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
                //Bloqueados.add("3004839486");
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);
            }
        });




              /*Switch onOffSwitch = (Switch)  findViewById(R.id.switch1);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check = isChecked;
                Log.v("Switch State=", ""+isChecked);

            }
        });*/
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
                        String name=c.getString(0);
                        String number = c.getString(1).replaceAll(" ", "");
                         Bloqueados.add(number);
                        Contact contact = new Contact();
                        contact.setName(name);
                        contact.setNumber(number);
                        SingletonContact sincontact = SingletonContact.getInstance();
                        sincontact.addContact(contact);
                        //Bloqueados.add(number);
                        adapter.notifyDataSetChanged();
                        showSelectedNumber(number, name);
                        for (String bloqueo:Bloqueados) {
                            Toast.makeText(this, bloqueo, Toast.LENGTH_LONG).show();
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

    public void showSelectedNumber( String number, String name) {
        Toast.makeText(this,number + "-" + name, Toast.LENGTH_LONG).show();

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
}
