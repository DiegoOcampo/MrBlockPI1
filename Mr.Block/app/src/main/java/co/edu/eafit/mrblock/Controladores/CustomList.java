package co.edu.eafit.mrblock.Controladores;

/**
 * Created by juan on 6/12/15.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import co.edu.eafit.mrblock.R;

public class CustomList extends ArrayAdapter<String>{


    private final Activity context;
    private final LinkedList<String> web;
    private final ArrayList<Integer> imageId;
    public CustomList(Activity context, LinkedList<String> web, ArrayList<Integer> imageId) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        try {
            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(web.get(position));

            imageView.setImageResource(imageId.get(position));

        }catch (Exception e){}
        return rowView;
    }
}