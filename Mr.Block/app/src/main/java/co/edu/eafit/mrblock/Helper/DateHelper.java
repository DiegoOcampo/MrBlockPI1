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
        values.put(Contract.DateContract.COLUMN_YEAR_1,dateTime.getYear1());
        values.put(Contract.DateContract.COLUMN_MONTH_1,dateTime.getMonth1());
        values.put(Contract.DateContract.COLUMN_DAY_1,dateTime.getDay1());
        values.put(Contract.DateContract.COLUMN_HOUR_1,dateTime.getHour1());
        values.put(Contract.DateContract.COLUMN_MINUTE_1,dateTime.getMinute1());
        values.put(Contract.DateContract.COLUMN_SECOND_1,dateTime.getSecond1());
        values.put(Contract.DateContract.COLUMN_YEAR_2,dateTime.getYear2());
        values.put(Contract.DateContract.COLUMN_MONTH_2,dateTime.getMonth2());
        values.put(Contract.DateContract.COLUMN_DAY_2,dateTime.getDay2());
        values.put(Contract.DateContract.COLUMN_HOUR_2,dateTime.getHour2());
        values.put(Contract.DateContract.COLUMN_MINUTE_2,dateTime.getMinute2());
        values.put(Contract.DateContract.COLUMN_SECOND_2,dateTime.getSecond2());
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
                dateTime.setYear1(cursor.getInt(1));
                dateTime.setMonth1(cursor.getInt(2));
                dateTime.setDay1(cursor.getInt(3));
                dateTime.setHour1(cursor.getInt(4));
                dateTime.setMinute1(cursor.getInt(5));
                dateTime.setSecond1(cursor.getInt(6));
                dateTime.setYear2(cursor.getInt(7));
                dateTime.setMonth2(cursor.getInt(8));
                dateTime.setDay2(cursor.getInt(9));
                dateTime.setHour2(cursor.getInt(10));
                dateTime.setMinute2(cursor.getInt(11));
                dateTime.setSecond2(cursor.getInt(12));
                block.add(dateTime);
            }while (cursor.moveToNext());
        }
        return block;
    }

    public DateTime getDate(String number){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.DateContract.TABLE_NAME, new String[]{
                Contract.DateContract.COLUMN_NUMBER,
                Contract.DateContract.COLUMN_YEAR_1,
                Contract.DateContract.COLUMN_MONTH_1,
                Contract.DateContract.COLUMN_DAY_1,
                Contract.DateContract.COLUMN_HOUR_1,
                Contract.DateContract.COLUMN_MINUTE_1,
                Contract.DateContract.COLUMN_SECOND_1,
                Contract.DateContract.COLUMN_YEAR_2,
                Contract.DateContract.COLUMN_MONTH_2,
                Contract.DateContract.COLUMN_DAY_2,
                Contract.DateContract.COLUMN_HOUR_2,
                Contract.DateContract.COLUMN_MINUTE_2,
                Contract.DateContract.COLUMN_SECOND_2
        }, Contract.DateContract.COLUMN_NUMBER + "= '" + number + "'", null
                , null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        DateTime dateTime = new DateTime(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),
                cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7)
                ,cursor.getInt(8),cursor.getInt(9),cursor.getInt(10),cursor.getInt(11),cursor.getInt(12));
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
