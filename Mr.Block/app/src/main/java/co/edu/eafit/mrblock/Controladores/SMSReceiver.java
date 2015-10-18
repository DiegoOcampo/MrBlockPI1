package co.edu.eafit.mrblock.Controladores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import co.edu.eafit.mrblock.Entidades.Complete;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.DateTime;
import co.edu.eafit.mrblock.Helper.CallInHelper;
import co.edu.eafit.mrblock.Helper.CompleteHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.DateHelper;

/**
 * Created by juan on 9/10/15.
 */
public class SMSReceiver extends BroadcastReceiver{
    CompleteHelper completeHelper;
    boolean smsBlock;
    @Override
    public void onReceive(Context context, Intent intent) {
        completeHelper = new CompleteHelper(context);
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String incomingNumber = "";
        smsBlock = isSmsBlock(completeHelper);
        if (bundle != null)
        {
            if(smsBlock) {

                this.abortBroadcast();
                //---retrieve the SMS message received---
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    incomingNumber = msgs[i].getOriginatingAddress();
                }
                //---display the new SMS message---
                Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean isSmsBlock(CompleteHelper completeHelper){
        ArrayList<Complete> completes = completeHelper.getAllComplete();
        for(int i = 0; i < completes.size();i++){
            if(completes.get(i).getBlockName().equals("Complete block")){
                return true;
            }
        }
        return false;
    }
}
