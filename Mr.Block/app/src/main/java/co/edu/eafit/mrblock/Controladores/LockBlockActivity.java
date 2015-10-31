package co.edu.eafit.mrblock.Controladores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Entidades.SimpleGeofence;
import co.edu.eafit.mrblock.Entidades.Ubicacion;
import co.edu.eafit.mrblock.Helper.UbicationHelper;
import co.edu.eafit.mrblock.R;

/**
 * Created by Usuario on 12/10/2015.
 */
public class LockBlockActivity extends AppCompatActivity {

    private EditText name;
    private Button confirmed;
    private String nombre;
    private UbicationHelper ubicationHelper;
    private ArrayList<Ubicacion> array = new ArrayList<Ubicacion>();
    private ArrayList<String> array2 = new ArrayList<String>();
    private boolean exist;
    private double radius;
    private RadioGroup radioGroup;
    private RadioButton cinco,cien,ciencinco,docientos,doscinco;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_locblock);
        confirmed = (Button) findViewById(R.id.btn_Confirmed);
        name = (EditText) findViewById(R.id.edit_Name);
        ubicationHelper = new UbicationHelper(getApplicationContext());
        array = ubicationHelper.getAllUbication();
        radioGroup = (RadioGroup)findViewById(R.id.Radius);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.RB_50m){
                    radius = 50;
                    Toast.makeText(getApplicationContext(),"Seleccionado 50 metros",Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.RB_100m){
                    radius = 100;
                    Toast.makeText(getApplicationContext(),"Seleccionado 100 metros",Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.RB_150m){
                    radius = 150;
                    Toast.makeText(getApplicationContext(),"Seleccionado 150 metros",Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.RB_200m){
                    radius = 200;
                    Toast.makeText(getApplicationContext(),"Seleccionado 200 metros",Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.RB_250m){
                    radius = 250;
                    Toast.makeText(getApplicationContext(),"Seleccionado 250 metros",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * Revisar el RadioGroup que define el tamaño del radio de bloqueo
         */
        exist = false;
        if(!array.isEmpty()){
            for(int i = 0 ; i < array.size();i++ ){
                Ubicacion ubic = new Ubicacion();
                ubic=array.get(i);
                array2.add(i, ubic.getName());
                Toast.makeText(getApplicationContext(), ubic.getName(),Toast.LENGTH_SHORT).show();
            }
        }else if(array.isEmpty()){
            Toast.makeText(getApplicationContext(),"esta vacio",Toast.LENGTH_LONG).show();
        }
        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exist = false;
                nombre = name.getText().toString();
                if (!array.isEmpty()) {
                    for (int i = 0; i < array2.size(); i++) {
                        Toast.makeText(getApplicationContext(), "Comparando " + i, Toast.LENGTH_LONG).show();
                        String s = array2.get(i);
                        if (nombre.equals(s)) {
                            exist = true;
                        }
                    }
                }
                if (nombre.equals("")) {
                    Toast.makeText(getApplicationContext(), " Porfavor Ingrese un Nombre para la Ubicacion ", Toast.LENGTH_SHORT).show();
                } else if (exist) {
                    Toast.makeText(getApplicationContext(), "Porfavor Elija otro Nombre ", Toast.LENGTH_SHORT).show();
                } else if (!exist && !nombre.equals("")) {
                    nombre = name.getText().toString();
                    Ubicacion ubicacion = new Ubicacion();
                    ubicacion.setName(nombre);
                    LatLng ubiclatlng = MapsActivity.storeubic;
                    ubicacion.setLatlng(ubiclatlng);
                    ubicacion.setRadio(radius);
                    ubicationHelper.addUbication(ubicacion);
                    Toast.makeText(getApplicationContext(), "El Geofence :" + nombre + " Ha sido guardado", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(i);
                }
            }
        });
    }

     // Pendiente de revision y reestructuracion
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

