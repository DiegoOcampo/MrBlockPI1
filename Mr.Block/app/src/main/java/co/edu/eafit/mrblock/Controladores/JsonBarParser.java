package co.edu.eafit.mrblock.Controladores;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import co.edu.eafit.mrblock.Entidades.Bar;

/**
 * Created by yeison on 13/11/2015.
 */
public class JsonBarParser {
    public List<Bar> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTP-8"));
        try {
            return leerArrayBares(reader);
        }finally {
            reader.close();
        }
    }

    public List leerArrayBares(JsonReader reader) throws IOException{
        ArrayList bares = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()){
            bares.add(leerBar(reader));
        }
        reader.endArray();
        return bares;
    }

    public Bar leerBar(JsonReader reader) throws IOException{
        int id = Integer.parseInt(null);
        String nombre = null;
        String direccion = null;
        String telefono = null;
        String publicidad = null;

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            switch (name){
                case "id":
                    id = reader.nextInt();
                    break;
                case "nombre":
                    nombre = reader.nextString();
                    break;
                case "direccion":
                    direccion = reader.nextString();
                    break;
                case "telefono":
                    telefono = reader.nextString();
                    break;
                case "publicidad":
                    publicidad = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Bar(id,nombre,direccion,telefono,publicidad);
    }
}
