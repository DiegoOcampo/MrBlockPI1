package co.edu.eafit.mrblock.Controladores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import co.edu.eafit.mrblock.ServiceClass;

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