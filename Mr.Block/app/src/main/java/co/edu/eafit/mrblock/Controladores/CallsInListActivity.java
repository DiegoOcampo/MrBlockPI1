package co.edu.eafit.mrblock.Controladores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Entidades.Call;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Helper.CallInHelper;
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
    CallInHelper callInHelper;
    ContactInHelper contactInHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_in_list);
        contactInHelper = new ContactInHelper(getApplicationContext());

        callInHelper = new CallInHelper(getApplicationContext());
        try {
            calls = callInHelper.getAllCall();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"hola" + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        //calls = contactInHelper.getAllContact();
        /*for(int i=0;i<contacts.size();i++){
            adapter1list.add(contacts.get(i).getContact());
        }*/
        for (Call callContact: calls){
            callsString.add(callContact.getContact());
        }
        callInList = (ListView)findViewById(R.id.callslist);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, callsString);
        callInList.setAdapter(adapter);
    }
}
