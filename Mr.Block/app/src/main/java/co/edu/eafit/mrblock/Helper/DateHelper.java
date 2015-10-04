package co.edu.eafit.mrblock.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import co.edu.eafit.mrblock.Contracts.Contract;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.DateTime;

/**
 * Created by juan on 1/10/15.
 */
public class DateHelper {
    DBHelper dbHelper;
    public DateHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    public void addDate(DateTime dateTime){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.DateContract.COLUMN_NUMBER,dateTime.getNumber());
        values.put(Contract.DateContract.COLUMN_YEAR,dateTime.getYear());
        values.put(Contract.DateContract.COLUMN_MONTH,dateTime.getMonth());
        values.put(Contract.DateContract.COLUMN_DAY,dateTime.getDay());
        values.put(Contract.DateContract.COLUMN_HOUR,dateTime.getHour());
        values.put(Contract.DateContract.COLUMN_MINUTE,dateTime.getMinute());
        values.put(Contract.DateContract.COLUMN_SECOND,dateTime.getSecond());
        db.insert(Contract.DateContract.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<DateTime> getAllDate(){
        ArrayList<DateTime> block= new ArrayList<DateTime>();
        String selectQuery = "SELECT  * FROM " + Contract.DateContract.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                DateTime dateTime = new DateTime();
                dateTime.setNumber(cursor.getString(0));
                dateTime.setYear(cursor.getInt(1));
                dateTime.setMonth(cursor.getInt(2));
                dateTime.setDay(cursor.getInt(3));
                dateTime.setHour(cursor.getInt(4));
                dateTime.setMinute(cursor.getInt(5));
                dateTime.setSecond(cursor.getInt(6));
                block.add(dateTime);
            }while (cursor.moveToNext());
        }
        return block;
    }

    public DateTime getDate(String number){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.DateContract.TABLE_NAME, new String[]{
                Contract.DateContract.COLUMN_NUMBER,
                Contract.DateContract.COLUMN_YEAR,
                Contract.DateContract.COLUMN_MONTH,
                Contract.DateContract.COLUMN_DAY,
                Contract.DateContract.COLUMN_HOUR,
                Contract.DateContract.COLUMN_MINUTE,
                Contract.DateContract.COLUMN_SECOND
        }, Contract.DateContract.COLUMN_NUMBER + "= '" + number + "'", null
                , null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        DateTime dateTime = new DateTime(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),
                cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6));
        return dateTime;
    }

    public long delete(DateTime dateTime){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        /*db.execSQL("DELETE FROM " + Contract.ContactInContract.TABLE_NAME +
        "WHERE " + Contract.ContactInContract.COLUMN_NUMBER + "='"+
        contact.getNumber() + "'");
        */
        try {
            return db.delete(Contract.DateContract.TABLE_NAME, Contract.DateContract.COLUMN_NUMBER + "  =?",
                    new String[]{dateTime.getNumber()});
            //db.close();
        }catch (Exception e){
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
            return -1;
        }
    }
}
