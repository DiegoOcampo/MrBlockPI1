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
    ArrayList<DateTime> dateTimes = new ArrayList<DateTime>();
    DateHelper dateHelper;
    Date date1,date2;
    DateTime dateTime;
    CompleteHelper completeHelper;
    boolean isDateBlock;
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

            dateTimes = dateHelper.getAllDate();
            for (int i = 0; i < dateTimes.size(); i++){
                date1.setYear(dateTimes.get(i).getYear1() - 1900);
                date1.setMonth(dateTimes.get(i).getMonth1());
                date1.setDate(dateTimes.get(i).getDay1());
                date1.setHours(dateTimes.get(i).getHour1());
                date1.setMinutes(dateTimes.get(i).getMinute1());
                date1.setSeconds(dateTimes.get(i).getSecond1());
                date2.setYear(dateTimes.get(i).getYear2() - 1900);
                date2.setMonth(dateTimes.get(i).getMonth2());
                date2.setDate(dateTimes.get(i).getDay2());
                date2.setHours(dateTimes.get(i).getHour2());
                date2.setMinutes(dateTimes.get(i).getMinute2());
                date2.setSeconds(dateTimes.get(i).getSecond2());
                Date date = new Date();
                if (date1.before(date) && date.before(date2)){
                    isDateBlock = true;
                    break;
                }else {
                    isDateBlock = false;
                }

            }
            //TODO Auto-generated method stub
            if (isDateBlock) {
                if (myBundle != null) {

                    try {
                        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
                            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                            System.out.println("--------in state-----");
                            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                                Contact contact = null;
                                try {
                                    contact= contactInHelper.getContact(incomingNumber.replaceAll(" ", ""));
                                }catch (Exception e){
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                if( contact==null ) {
                                    Block(context);
                                }

                                //Block(context);
                                // Incoming call



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
                        Complete complete =  null;
                        try {
                            complete = completeHelper.getComplete("Complete block");
                        }catch (Exception e){
                            Toast.makeText(context,"error complete"+ e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER).replaceAll(" ", "");
                        if(complete!=null){

                            Contact contact= null;
                            try {
                                contact = contactInHelper.getContact(incomingNumber);
                            }catch (Exception e){
                            }
                            if (contact!=null && contact.getType().equals("white contact")){

                            }else {
                                Call call = new Call(incomingNumber, "contact", "call");
                                callInHelper.addCall(call);
                                Block(context);
                            }
                        }else {
                            System.out.println("--------------my n  umber---------" + incomingNumber);
                            Contact contact = contactInHelper.getContact(incomingNumber.replaceAll(" ", ""));
                            Toast.makeText(context, "" + !contact.getType().equals("white contact"), Toast.LENGTH_LONG).show();
                            if(!contact.getType().equals("white contact")) {
                                Call call = new Call(contact.getNumber(), contact.getName(), "call");
                                callInHelper.addCall(call);
                                Block(context);
                            }

                        }
                      // for (Contact cc : con) {
                            //if(contactInHelper.getContact(incomingNumber.replaceAll(" ","")).equals(cc.)){
                        //    if (cc.getDateName().equalsIgnoreCase(incomingNumber.replaceAll(" ", ""))) {
                          //      Block(cc,context);
                           /*     Call call = new Call(cc.getDateName(),cc.getName());
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
