package co.edu.eafit.mrblock.Controladores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by juan on 12/11/15.
 */
public class MapsReceiver extends BroadcastReceiver {
    protected static final String TAG = "MapsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(GeofenceTransitionsIntentService.Transition_Entered)){
            Log.wtf(TAG,"Into a Geofence");
        }else if (intent.getAction().equals(GeofenceTransitionsIntentService.Transition_Exited)){
            Log.wtf(TAG,"Out of a Geofence");
        }
    }
}
