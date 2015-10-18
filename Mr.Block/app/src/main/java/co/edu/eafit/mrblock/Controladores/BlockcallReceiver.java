package co.edu.eafit.mrblock.Controladores;

/**
 * Created by juan on 13/09/15.
 */
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import co.edu.eafit.mrblock.Entidades.Call;
import co.edu.eafit.mrblock.Entidades.Complete;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.DateTime;
import co.edu.eafit.mrblock.Helper.CallInHelper;
import co.edu.eafit.mrblock.Helper.CompleteHelper;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.DateHelper;

public class BlockcallReceiver extends BroadcastReceiver {
    ContactInHelper contactInHelper;
    CallInHelper callInHelper;
    ArrayList<Contact> con = new ArrayList<Contact>();
    DateHelper dateHelper;
    Date date1,date2;
    DateTime dateTime;
    CompleteHelper completeHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        completeHelper = new CompleteHelper(context);
        dateHelper = new DateHelper(context);
        date1 = new Date();
        date2 = new Date();
        contactInHelper = new ContactInHelper(context);
        callInHelper = new CallInHelper(context);
        con = contactInHelper.getAllContact();
        Bundle myBundle = intent.getExtras();
        try {
            String incoming = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            dateTime = dateHelper.getDate(incoming.replaceAll(" ",""));
            date1.setYear(dateTime.getYear1() - 1900);
            date1.setMonth(dateTime.getMonth1());
            date1.setDate(dateTime.getDay1());
            date1.setHours(dateTime.getHour1());
            date1.setMinutes(dateTime.getMinute1());
            date1.setSeconds(dateTime.getSecond1());
            date2.setYear(dateTime.getYear2() - 1900);
            date2.setMonth(dateTime.getMonth2());
            date2.setDate(dateTime.getDay2());
            date2.setHours(dateTime.getHour2());
            date2.setMinutes(dateTime.getMinute2());
            date2.setSeconds(dateTime.getSecond2());
            Date date = new Date();
            //TODO Auto-generated method stub
            if (date1.before(date) && date.before(date2)) {
                if (myBundle != null) {

                    try {
                        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {

                            Toast.makeText(context,"3",Toast.LENGTH_LONG).show();
                            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                            System.out.println("--------in state-----");
                            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                                // Incoming call
                                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                                System.out.println("--------------my number---------" + incomingNumber);

                                // this is main section of the code,. could also be use for particular number.
                                // Get the boring old TelephonyManager.
                                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                // Get the getITelephony() method
                                Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
                                Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");

                                // Ignore that the method is supposed to be private
                                methodGetITelephony.setAccessible(true);

                                // Invoke getITelephony() to get the ITelephony interface
                                Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

                                // Get the endCall method from ITelephony
                                Class<?> telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
                                Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

                                // Invoke endCall()
                                methodEndCall.invoke(telephonyInterface);


                            }
                        }
                    } catch (Exception ex) { // Many things can go wrong with reflection calls
                        ex.printStackTrace();
                    }
                }
            }
        }catch (Exception e){

        }
        if (myBundle != null ){
            System.out.println("--------Not null-----");
            try{
                if (intent.getAction().equals("android.intent.action.PHONE_STATE")){
                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    System.out.println("--------in state-----");
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        // Incoming call
                        Complete complete = completeHelper.getComplete("Complete block");
                        if(complete.getInCalls()==1){
                            Block(context);
                        }
                        String incomingNumber =intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        System.out.println("--------------my number---------" + incomingNumber);
                        Contact contact = contactInHelper.getContact(incomingNumber.replaceAll(" ", ""));
                        Call call = new Call(contact.getNumber(),contact.getName());
                        callInHelper.addCall(call);
                        Block(context);
                      // for (Contact cc : con) {
                            //if(contactInHelper.getContact(incomingNumber.replaceAll(" ","")).equals(cc.)){
                        //    if (cc.getNumber().equalsIgnoreCase(incomingNumber.replaceAll(" ", ""))) {
                          //      Block(cc,context);
                           /*     Call call = new Call(cc.getNumber(),cc.getName());
                                callInHelper.addCall(call);
                                // this is main section of the code,. could also be use for particular number.
                                // Get the boring old TelephonyManager.
                                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                // Get the getITelephony() method
                                Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
                                Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");

                                // Ignore that the method is supposed to be private
                                methodGetITelephony.setAccessible(true);

                                // Invoke getITelephony() to get the ITelephony interface
                                Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

                                // Get the endCall method from ITelephony
                                Class<?> telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
                                Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

                                // Invoke endCall()
                                methodEndCall.invoke(telephonyInterface);
                            *///}
                        //}



                    }

                }
            }
            catch (Exception ex)
            { // Many things can go wrong with reflection calls
                ex.printStackTrace();
            }
        }else{
            System.out.println("null bundle");
        }
    }

    public void Block(Context context) throws Exception{

        // this is main section of the code,. could also be use for particular number.
        // Get the boring old TelephonyManager.
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // Get the getITelephony() method
        Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
        Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");

        // Ignore that the method is supposed to be private
        methodGetITelephony.setAccessible(true);

        // Invoke getITelephony() to get the ITelephony interface
        Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

        // Get the endCall method from ITelephony
        Class<?> telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
        Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

        // Invoke endCall()
        methodEndCall.invoke(telephonyInterface);
    }
}
