package co.edu.eafit.mrblock.Controladores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Entidades.Call;
import co.edu.eafit.mrblock.Helper.DBHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.R;

/**
 * Created by juan on 29/09/15.
 */

public class CallsInListActivity extends AppCompatActivity {
    ListView callInList;
    ArrayList<Call> calls = new ArrayList<Call>();
    ArrayList<String> callsString = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    DBHelper DBHelper;
    ContactInHelper contactInHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_in_list);
        DBHelper = new DBHelper(getApplicationContext());
        contactInHelper = new ContactInHelper(getApplicationContext());
        //calls = contactInHelper.getAllContact();
        calls = DBHelper.getAllCall();
        /*for(int i=0;i<contacts.size();i++){
            adapter1list.add(contacts.get(i).getContact());
        }*/
        for (Call contact: calls){
            callsString.add(contact.getContact());
        }
        callInList = (ListView)findViewById(R.id.callslist);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, callsString);
        callInList.setAdapter(adapter);
    }
}
