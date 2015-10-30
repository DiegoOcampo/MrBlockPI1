package co.edu.eafit.mrblock.Controladores;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import co.edu.eafit.mrblock.R;

/**
 * Created by Usuario on 28/10/2015.
 */
public class GeofenceTransitionsIntentService extends IntentService {

    public GeofenceTransitionsIntentService() {
        super(GeofenceTransitionsIntentService.class.getSimpleName());
    }

    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        /**
         if (geofencingEvent.hasError()) {
         String errorMessage = GeofenceErrorMessages.getErrorString(this,
         geofencingEvent.getErrorCode());
         Log.e(TAG, errorMessage);
         return;
         }
         */

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            /**
             *
             String geofenceTransitionDetails = getGeofenceTransitionDetails(
             this,
             geofenceTransition,
             triggeringGeofences
             );
             */

            //Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            //Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
            //      geofenceTransition));
        }
    }
}
