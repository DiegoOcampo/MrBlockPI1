package co.edu.eafit.mrblock.Entidades;

/**
 * Created by yeison on 13/11/2015.
 */
public class Bar {
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String publicidad;

    public Bar(int id, String nombre, String direccion, String telefono, String publicidad){
        this.id= id;
        this.nombre=nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.publicidad = publicidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPublicidad() {
        return publicidad;
    }

    public void setPublicidad(String publicidad) {
        this.publicidad = publicidad;
    }
}
