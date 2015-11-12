package co.edu.eafit.mrblock.Controladores;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Entidades.SimpleGeofence;
import co.edu.eafit.mrblock.Entidades.Ubicacion;
import co.edu.eafit.mrblock.Helper.UbicationHelper;
import co.edu.eafit.mrblock.R;

/**
 * Created by Usuario on 12/10/2015.
 */
public class LockBlockActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>{

    private EditText name;
    private Button confirmed;
    private String nombre;
    private UbicationHelper ubicationHelper;
    private ArrayList<Ubicacion> array = new ArrayList<Ubicacion>();
    private ArrayList<String> array2 = new ArrayList<String>();
    private boolean exist;
    private double radius = 250;
    private RadioGroup radioGroup;
    protected static final String TAG = "GeoFenceController";
    protected GoogleApiClient mGoogleApiClient;
    protected ArrayList<Geofence> mGeofenceList;
    private boolean mGeofencesAdded;
    private PendingIntent mGeofencePendingIntent;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_locblock);
        toolbar = (Toolbar) findViewById(R.id.toolbarmaps);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGeofencePendingIntent = null;
        buildGoogleApiClient();

        confirmed = (Button) findViewById(R.id.btn_Confirmed);
        name = (EditText) findViewById(R.id.edit_Name);
        radioGroup = (RadioGroup)findViewById(R.id.Radius);

        ubicationHelper = new UbicationHelper(getApplicationContext());
        array = ubicationHelper.getAllUbication();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                CheckRadioButtonForRadius(checkedId);
            }
        });

        exist = false;
        if(!array.isEmpty()){
            for(int i = 0 ; i < array.size();i++ ){
                Ubicacion ubic = new Ubicacion();
                ubic=array.get(i);
                array2.add(i, ubic.getName());
                Toast.makeText(getApplicationContext(), ubic.getName(),Toast.LENGTH_SHORT).show();
            }
        }else if(array.isEmpty()){
            Toast.makeText(getApplicationContext(),"No hay ubiaciones bloqueadas",Toast.LENGTH_LONG).show();
        }
        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckNameForDB();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        boolean conectado = mGoogleApiClient.isConnected();
        if(conectado){
            Toast.makeText(getApplicationContext(),"Conectado",Toast.LENGTH_LONG).show();
        }else if(!conectado){
            Toast.makeText(getApplicationContext(),"No Conectado",Toast.LENGTH_LONG).show();
        }
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {
            // Update state and save in shared preferences.
            mGeofencesAdded = !mGeofencesAdded;
            Toast.makeText(this,
                    getString(mGeofencesAdded ? R.string.geofences_added :
                            R.string.geofences_removed),
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    status.getStatusCode());
            Log.e(TAG, errorMessage);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }



    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
        Toast.makeText(getApplicationContext(),"Conectado",Toast.LENGTH_LONG).show();
    }


    public void addGeofencesHandler(ArrayList<Geofence> listGeofence) {
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(getApplicationContext(), "No estoy conectado" , Toast.LENGTH_SHORT).show();
            return;
        }else if(mGoogleApiClient.isConnected()){
            Toast.makeText(getApplicationContext(),"Estoy Conectado",Toast.LENGTH_LONG).show();
            try {
                LocationServices.GeofencingApi.addGeofences(
                        mGoogleApiClient,
                        listGeofence,
                        getGeofencePendingIntent()
                ).setResultCallback(this);
            } catch (SecurityException securityException) {
                logSecurityException(securityException);
            }
        }
    }

    public void removeGeofencesHandler(ArrayList<String> list) {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // Remove geofences.
            LocationServices.GeofencingApi.removeGeofences(
                    mGoogleApiClient,
                    list
            ).setResultCallback(this);
        } catch (SecurityException securityException) {
            logSecurityException(securityException);
        }
    }

    private void logSecurityException(SecurityException securityException) {
        Log.e(TAG, "Invalid location permission. " +
                "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void CheckNameForDB(){
        exist = false;
        nombre = name.getText().toString();
        if (nombre.equals("")) {
            Toast.makeText(getApplicationContext(), " Porfavor Ingrese un Nombre para la Ubicacion ", Toast.LENGTH_SHORT).show();
        }else if (!array.isEmpty()) {
            for (int i = 0; i < array2.size(); i++) {
                Toast.makeText(getApplicationContext(), "Comparando " + i, Toast.LENGTH_LONG).show();
                String s = array2.get(i);
                if (nombre.equals(s)) {
                    exist = true;
                }
            }
        }if (exist) {
            Toast.makeText(getApplicationContext(), "Please choose another name", Toast.LENGTH_SHORT).show();
        }else if (!exist && !nombre.equals("")) {
            nombre = name.getText().toString();
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setName(nombre);
            LatLng ubiclatlng = MapsActivity.storeubic;
            ubicacion.setLatlng(ubiclatlng);
            ubicacion.setRadio(radius);
            ubicationHelper.addUbication(ubicacion);
            Toast.makeText(getApplicationContext(), "The Geofence :" + nombre + " has been created", Toast.LENGTH_LONG).show();
            finish();
            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(i);
        }
    }

    public void CheckRadioButtonForRadius(int checkedId){
        if(checkedId == R.id.RB_50m){
            radius = 50;
        }else if(checkedId == R.id.RB_100m){
            radius = 100;
        }else if(checkedId == R.id.RB_150m){
            radius = 150;
        }else if(checkedId == R.id.RB_200m){
            radius = 200;
        }else if(checkedId == R.id.RB_250m){
            radius = 250;
        }
    }

    public void onRadioButtonClicked(View view){
        boolean check = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case (R.id.RB_100m):
                radius = 100;
                break;
            case (R.id.RB_150m):
                radius = 150;
                break;
            case (R.id.RB_200m):
                radius = 200;
                break;
            case (R.id.RB_250m):
                radius = 250;
                break;
            case(R.id.RB_50m):
                radius = 50;
                break;
        }
    }
}

