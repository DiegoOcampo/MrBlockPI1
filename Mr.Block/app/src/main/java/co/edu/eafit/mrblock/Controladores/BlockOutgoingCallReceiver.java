package co.edu.eafit.mrblock.Controladores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Entidades.Complete;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Helper.CompleteHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;

/**
 * Created by juan on 22/09/15.
 */
public class BlockOutgoingCallReceiver extends BroadcastReceiver{
    CompleteHelper completeHelper;
    boolean callBlock;
    ContactInHelper contactInHelper;
    boolean blockedContact;
    @Override
    public void onReceive(Context context, Intent intent) {
        completeHelper = new CompleteHelper(context);
        contactInHelper = new ContactInHelper(context);
        callBlock = isSmsBlock(completeHelper);
        blockedContact = false;
        String phonenumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER).replaceAll(" ", "");
        Contact contact = null;
        try{
            contact = contactInHelper.getContact(phonenumber);
        }catch (Exception e){

        }
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            if (getResultData()!=null && callBlock || contact != null) {
                String number = "123456";
                setResultData(number);
            }
            /*
            if(contact != null){
                String number = "123456";
                setResultData(number);
            }*/
        }
    }

    public boolean isSmsBlock(CompleteHelper completeHelper){
        ArrayList<Complete> completes = completeHelper.getAllComplete();
        for(int i=0;i<completes.size();i++){
            if(completes.get(i).getBlockName().equals("Complete block")){
                return true;
            }
        }
        return false;
    }
}
