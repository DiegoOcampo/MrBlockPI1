package co.edu.eafit.mrblock.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Contracts.Contract;
import co.edu.eafit.mrblock.Entidades.Call;
import co.edu.eafit.mrblock.Entidades.Contact;

/**
 * Created by juan on 29/09/15.
 */
public class CallInHelper{

    DBHelper dbHelper;
    public CallInHelper(Context context){
        dbHelper = new DBHelper(context);
    }
    public void addCall(Call call){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.CallInContract.COLUMN_NUMBER, call.getNumber());
        values.put(Contract.CallInContract.COLUMN_NAME, call.getName());
        values.put(Contract.CallInContract.COLUMN_TYPE, call.getType());
        db.insert(Contract.CallInContract.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Call> getAllCall(){
        ArrayList<Call> block= new ArrayList<Call>();
        String selectQuery = "SELECT  * FROM " + Contract.CallInContract.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Call call = new Call();
                call.setNumber(cursor.getString(0));
                call.setName(cursor.getString(1));
                call.setType(cursor.getString(2));
                block.add(call);
            }while (cursor.moveToNext());
        }
        return block;
    }

}
