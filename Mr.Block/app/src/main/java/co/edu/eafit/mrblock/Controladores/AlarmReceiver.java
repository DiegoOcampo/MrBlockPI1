package co.edu.eafit.mrblock.Controladores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Method;

import co.edu.eafit.mrblock.ServiceClass;
import co.edu.eafit.mrblock.SingletonContact;

/**
 * Created by juan on 26/09/15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
            Intent service = new Intent(context, ServiceClass.class);
            context.startService(service);
    }
}