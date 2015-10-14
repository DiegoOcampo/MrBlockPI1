package co.edu.eafit.mrblock.Entidades;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Usuario on 12/10/2015.
 */
public class Ubicacion {

    private LatLng Ubication;
    private String name;
    private double Longitud;
    private double Latitud;

    public Ubicacion(LatLng latlng, String name, double Longitud, double Latitud){
        this.Ubication = latlng;
        this.name = name;
        this.Latitud = Latitud;
        this.Longitud = Longitud;
    }

    public Ubicacion(){}

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatlng(LatLng latlng) {
        this.Ubication = latlng;
        setLongitud(Ubication.longitude);
        setLatitud(Ubication.latitude);
    }

    public LatLng getLatLng() {
        return Ubication;
    }

    public void setLatitud(double lati){
        this.Latitud = lati;
    }

    public void setLongitud(double longitud) {
        this.Longitud = longitud;
    }

    public double getLongitud() {
        return Longitud;
    }
}
