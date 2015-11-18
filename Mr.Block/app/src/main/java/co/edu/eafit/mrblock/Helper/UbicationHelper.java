package co.edu.eafit.mrblock.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Contracts.Contract;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.Ubicacion;

/**
 * Created by Usuario on 12/10/2015.
 */
public class UbicationHelper {
    DBHelper dbHelper;
    public UbicationHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    public void addUbication(Ubicacion ubicacion){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.UbicationContract.COLUMN_PLACE, ubicacion.getName());
        values.put(Contract.UbicationContract.COLUMN_LATITUDE, ubicacion.getLatitud());
        values.put(Contract.UbicationContract.COLUMN_LONGITUDE, ubicacion.getLongitud());
        values.put(Contract.UbicationContract.COLUMN_RADIUS, ubicacion.getRadio());
        db.insert(Contract.UbicationContract.TABLE_NAME, null, values);
        db.close();
    }

    public Ubicacion getUbication(String place){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.UbicationContract.TABLE_NAME,new String[]{
                        Contract.UbicationContract.COLUMN_PLACE,
                        Contract.UbicationContract.COLUMN_LATITUDE,
                        Contract.UbicationContract.COLUMN_LONGITUDE,
                        Contract.UbicationContract.COLUMN_RADIUS},
                Contract.UbicationContract.COLUMN_PLACE + "='" + place+"'",null,
                null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        LatLng latlng = new LatLng(cursor.getDouble(1),cursor.getDouble(2));
        Ubicacion ubication=new Ubicacion(latlng,cursor.getString(0),cursor.getDouble(1),cursor.getDouble(2),cursor.getDouble(3));
        return ubication;
    }

    public ArrayList<Ubicacion> getAllUbication(){
        ArrayList<Ubicacion> block= new ArrayList<Ubicacion>();
        String selectQuery = "SELECT  * FROM " + Contract.UbicationContract.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Ubicacion ubic = new Ubicacion();
                ubic.setName(cursor.getString(0));
                ubic.setLatitud(cursor.getDouble(1));
                ubic.setLongitud(cursor.getDouble(2));
                ubic.setRadio(cursor.getDouble(3));
                ubic.setLatlng(new LatLng(cursor.getDouble(1),cursor.getDouble(2)));
                block.add(ubic);
            }while (cursor.moveToNext());
        }
        db.close();
        return block;
    }

    public long delete(Ubicacion ubicacion){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        /*db.execSQL("DELETE FROM " + Contract.ContactInContract.TABLE_NAME +
        "WHERE " + Contract.ContactInContract.COLUMN_DATENAME + "='"+
        contact.getDateName() + "'");
        */
        try {
            return db.delete(Contract.UbicationContract.TABLE_NAME, Contract.UbicationContract.COLUMN_PLACE + "  =?",
                    new String[]{ubicacion.getName()});
            //db.close();
        }catch (Exception e){
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
            return -1;
        }
    }

    public long deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.delete(Contract.UbicationContract.TABLE_NAME, null,null);
            //db.close();
        }catch (Exception e){
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
            return -1;
        }
    }
}