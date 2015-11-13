package co.edu.eafit.mrblock.Controladores;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by yeison on 13/11/2015.
 */
public class BaresActivity extends AppCompatActivity {
    HttpURLConnection con;
    ListView lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bares);

        lista= (ListView) findViewById(R.id.listaBares);

        try{
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
        }
    }

    public class JsonTask extends AsyncTask<URL, Void, List<Bar>> {

        @Override
        protected List doInBackground(URL... urls){
            List bares = null;

            try {
                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                int statusCode = con.getResponseCode();
                Toast.makeText(getBaseContext(), "json", Toast.LENGTH_SHORT).show();
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
    }
}



