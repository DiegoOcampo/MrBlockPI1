package co.edu.eafit.mrblock.Controladores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.eafit.mrblock.Entidades.Bar;
import co.edu.eafit.mrblock.R;

/**
 * Created by yeison on 13/11/2015.
 */
public class AdaptadorBares extends ArrayAdapter<Bar> {

    public AdaptadorBares(Context context, List<Bar> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = convertView;

        if ( null == convertView){
            v=inflater.inflate(R.layout.bares_lista, parent, false);
        }

        TextView idBar = (TextView)v.findViewById(R.id.idBar);
        TextView nombreBar = (TextView)v.findViewById(R.id.nombreBar);
        TextView direccionBar = (TextView)v.findViewById(R.id.direccionBar);
        TextView telefonoBar = (TextView)v.findViewById(R.id.telefonoBar);
        TextView publicidadBar = (TextView)v.findViewById(R.id.publicidadBar);

        Bar item = getItem(position);

        idBar.setText(item.getId());
        nombreBar.setText(item.getNombre());
        direccionBar.setText(item.getDireccion());
        telefonoBar.setText(item.getTelefono());
        publicidadBar.setText(item.getPublicidad());
        return v;
    }

}
