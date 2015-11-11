package co.edu.eafit.mrblock.Controladores;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Entidades.Call;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Helper.CallInHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.R;

/**
 * Created by juan on 24/10/15.
 */
public class IncomingCallsFragment extends Fragment {
    private ListView listIncomingCalls;
    private Context context;

    ArrayList<Call> calls = new ArrayList<Call>();
    ArrayList<String> callsString = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    CallInHelper callInHelper;
    public IncomingCallsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_incoming_calls, container, false);
        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.detach(this).attach(this).commit();
        listIncomingCalls = (ListView) view.findViewById(R.id.listIncomingCalls);


        callInHelper = new CallInHelper(context);
        calls = callInHelper.getAllCall();

        //calls = contactInHelper.getAllContact();
        /*for(int i=0;i<contacts.size();i++){
            adapter1list.add(contacts.get(i).getContact());
        }*/
        for (Call callContact: calls){
            callsString.add(callContact.getContact());
        }
        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, callsString);
        listIncomingCalls.setAdapter(adapter);

        return view;
    }
}
