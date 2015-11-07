package co.edu.eafit.mrblock.Controladores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            if (getResultData()!=null && callBlock) {
                String number = "123456";
                setResultData(number);
            }
            checkDBforblockednumbers(contactInHelper,intent);
            if(!callBlock && blockedContact){
                String number = "123456";
                setResultData(number);
            }
        }
    }

    public boolean checkDBforblockednumbers(ContactInHelper contactInHelper, Intent intent){
        ArrayList<Contact> listblockedContacts = new ArrayList<>();
        listblockedContacts = contactInHelper.getAllContact();
        ArrayList<String> numbers = new ArrayList<>();
        for(int i = 0 ; i<listblockedContacts.size();i++){
            numbers.add(i,listblockedContacts.get(i).getNumber());
        }
        String phonenumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        for(int i = 0;i<numbers.size();i++){
            if(phonenumber == numbers.get(i)){
                blockedContact = true;
                break;
            }
        }
        return blockedContact;
    }

    public String getNumberMarked(Intent intent){
        String phonenumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        return phonenumber;
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
