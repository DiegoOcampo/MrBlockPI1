package co.edu.eafit.mrblock;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

import co.edu.eafit.mrblock.Controladores.MainActivity;

/**
 * Created by juan on 30/09/15.
 */
public class ServiceClass extends Service {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.d("Testing", "Service got created");
        Toast.makeText(this, "ServiceClass.onCreate()", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Bundle myBundle = intent.getExtras();
        System.out.println("I'm fine, thanks");
        if (myBundle != null && MainActivity.check){
            System.out.println("--------Not null-----");
            try{
                if (intent.getAction().equals("android.intent.action.PHONE_STATE")){
                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    System.out.println("--------in state-----");
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        // Incoming call
                        String incomingNumber =intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        System.out.println("--------------my number---------" + incomingNumber);

                        SingletonContact sin = SingletonContact.getInstance();

                        // this is main section of the code,. could also be use for particular number.
                        // Get the boring old TelephonyManager.
                        TelephonyManager telephonyManager = (TelephonyManager)  getSystemService(Context.TELEPHONY_SERVICE);
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
            catch (Exception ex)
            { // Many things can go wrong with reflection calls
                ex.printStackTrace();
            }
        }
        Toast.makeText(this, "ServiceClass.onStart()", Toast.LENGTH_LONG).show();
        Log.d("Testing", "Service got started");
    }
}
