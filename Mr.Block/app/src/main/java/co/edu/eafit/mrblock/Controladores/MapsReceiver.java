package co.edu.eafit.mrblock.Controladores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.TransitionBlock;
import co.edu.eafit.mrblock.Helper.TransitionInHelper;
import co.edu.eafit.mrblock.R;

/**
 * Created by juan on 12/11/15.
 */
public class MapsReceiver extends BroadcastReceiver {
    protected static final String TAG = "MapsReceiver";
    private boolean blocked = false;
    TransitionInHelper transitionInHelper;
    @Override
    public void onReceive(Context context, Intent intent) {

        transitionInHelper = new TransitionInHelper(context);
        if(intent.getAction().equals(GeofenceTransitionsIntentService.Transition_Entered)){
            Log.wtf(TAG,"Into a Geofence");
            int block = 1;
            transitionInHelper.deleteAll();
            TransitionBlock Transition = new TransitionBlock(context.getString(R.string.location_type_location),block);
            transitionInHelper.addTransition(Transition);
            blocked = true;
        }else if (intent.getAction().equals(GeofenceTransitionsIntentService.Transition_Exited)){
            Log.wtf(TAG,"Out of a Geofence");
            int block = 0;
            transitionInHelper.deleteAll();
            TransitionBlock Transition = new TransitionBlock(context.getString(R.string.location_type_location), block);
            transitionInHelper.addTransition(Transition);
            blocked = false;
        }
        try {
        String typeblock = context.getString(R.string.location_type_location);
        TransitionBlock Transitionblock = transitionInHelper.getTransitionBlocked(typeblock);
        if(Transitionblock!=null && Transitionblock.getBlock() == 1) {

                if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    System.out.println("--------in state-----");
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
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

        }
        } catch (Exception ex) { // Many things can go wrong with reflection calls
            ex.printStackTrace();
        }
    }
}
