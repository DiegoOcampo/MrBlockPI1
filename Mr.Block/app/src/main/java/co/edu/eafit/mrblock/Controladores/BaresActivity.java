package co.edu.eafit.mrblock.Controladores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import co.edu.eafit.mrblock.Entidades.Bar;
import co.edu.eafit.mrblock.R;

/**
0 * Created by yeison on 13/11/2015.
 */
public class BaresActivity extends AppCompatActivity {
    HttpURLConnection con;
    ListView lista;
    ArrayAdapter adapter;
    ArrayList<String> bares = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bares);
        Bar bar1 = new Bar(123,"bar1","cr 45","3426578","las mejores micheladas");
        bares.add(bar1.getNombre());
        Bar bar2 = new Bar(124,"bar2","cr 46","3789578","Solo aguardiente antioqueño");
        bares.add(bar2.getNombre());
        Bar bar3 = new Bar(125,"bar3","cr 46","3789578","Solo aguardiente antioqueño");
        bares.add(bar3.getNombre());
        lista= (ListView) findViewById(R.id.listaBares);
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, bares);
        lista.setAdapter(adapter);
       /* lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDetailsBlock(position);
            }
        });*/



        /*try{
            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                new JsonTask().
                        execute(
                                new URL("http://mrblock.herokuapp.com/api/bares.json"));
                Toast.makeText(this, "I'm running in heroku", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }*/
    }
/*
    public class JsonTask extends AsyncTask<URL, Void, List<Bar>> {
        List bares = new ArrayList<>();

        @Override
        protected List doInBackground(URL... urls){
            //Bar bars = new Bar(1,"a","b","c","4");
            //bares.add(bars);


            try {
                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                int statusCode = con.getResponseCode();
                if (statusCode!=200){
                    bares = new ArrayList<>();
                    Bar ba = new Bar(Integer.parseInt(null),"Error",null,null,null);
                    bares.add(ba);
                }else{
                    InputStream in = new BufferedInputStream(con.getInputStream());

                    JsonBarParser parser = new JsonBarParser();
                    bares = parser.readJsonStream(in);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                con.disconnect();
            }
            return bares;
        }

        @Override
        protected void onPostExecute(List<Bar> bares){
            if (bares != null){
                adaptador = new AdaptadorBares(getBaseContext(),bares);
                lista.setAdapter(adaptador);
            }else{
                Toast.makeText(getBaseContext(), "Ocurrió un error de parsing Json", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    /*private void openDetailsBlock(final int position) {
        //Collections.reverse(typesBlock);
        //try {
        final Bar bar = bares.get(position);
        //final String id = type.getId();
        //final String blocktype = type.getType();
        final AlertDialog.Builder alertName = new AlertDialog.Builder(getApplicationContext());
        alertName.setTitle("Details");
        alertName.setMessage("name: " + bar.getNombre() + "\n" + "id: " + bar.getId());

        alertName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertName.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alertName.show();


    }*/
}



