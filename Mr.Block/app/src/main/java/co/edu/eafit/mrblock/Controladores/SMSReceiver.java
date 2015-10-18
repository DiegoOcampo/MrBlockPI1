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

import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.DateTime;
import co.edu.eafit.mrblock.Helper.CallInHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.DateHelper;

/**
 * Created by juan on 9/10/15.
 */
public class SMSReceiver extends BroadcastReceiver{
    ContactInHelper contactInHelper;
    CallInHelper callInHelper;
    ArrayList<Contact> smsbBloqueos = new ArrayList<Contact>();
    DateHelper dateHelper;
    Date date1,date2;
    DateTime dateTime;
    @Override
    public void onReceive(Context context, Intent intent) {
        /*dateHelper = new DateHelper(context);
        date1 = new Date();
        date2 = new Date();
        contactInHelper = new ContactInHelper(context);
        callInHelper = new CallInHelper(context);
        smsbBloqueos = contactInHelper.getAllContact();
        this.abortBroadcast();
        final SmsManager sms = SmsManager.getDefault();
        Intent smsRecvIntent = new Intent("android.provider.Telephony.SMS_RECEIVED");
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        System.out.println("--------------my number---------" + incomingNumber);
        //String phoneNumber = currentMessage.getDisplayOriginatingAddress();
        Toast.makeText(context,incomingNumber + "mensaje",Toast.LENGTH_LONG).show();*/

        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String incomingNumber = "";

        if (bundle != null)
        {
            if(MainActivity.check3) {

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
}
