package co.edu.eafit.mrblock.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.LinkedList;

import co.edu.eafit.mrblock.Contracts.Contract;
import co.edu.eafit.mrblock.Entidades.Type;

/**
 * Created by juan on 20/10/15.
 */
public class TypeHelper {
    DBHelper dbHelper;

    public TypeHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    public void addType(Type type){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.TypeContract.COLUMN_ID, type.getId());
        values.put(Contract.TypeContract.COLUMN_TYPE, type.getType());
        db.insert(Contract.TypeContract.TABLE_NAME, null, values);
        db.close();
    }

    public LinkedList<Type> getAllTypes(){
        LinkedList<Type> block= new LinkedList<Type>();
        String selectQuery = "SELECT  * FROM " + Contract.TypeContract.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Type type = new Type();
                type.setId(cursor.getString(0));
                type.setType(cursor.getString(1));
                block.push(type);
            }while (cursor.moveToNext());
        }
        return block;
    }

    public long delete(String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.delete(Contract.TypeContract.TABLE_NAME, Contract.TypeContract.COLUMN_ID + "  =?",
                    new String[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public Type getType(String id){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.TypeContract.TABLE_NAME,new String[]{
                        Contract.TypeContract.COLUMN_ID,
                        Contract.TypeContract.COLUMN_TYPE},
                Contract.TypeContract.COLUMN_ID + "='" + id +"'",null,
                null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Type type = new Type(cursor.getString(0),cursor.getString(1));
        return type;
    }
}
