package co.edu.eafit.mrblock.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        db.insert(Contract.ContactInContract.TABLE_NAME, null, values);
        db.close();
    }

    public DateTime getDate(int number){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.DateContract.TABLE_NAME,new String[]{
                Contract.DateContract.COLUMN_NUMBER,
                Contract.DateContract.COLUMN_YEAR,
                Contract.DateContract.COLUMN_MONTH,
                Contract.DateContract.COLUMN_DAY,
                Contract.DateContract.COLUMN_HOUR,
                Contract.DateContract.COLUMN_MINUTE,
                Contract.DateContract.COLUMN_SECOND
        }, Contract.DateContract.COLUMN_NUMBER + "=?", new String[]{String.valueOf(number)}
        ,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        DateTime dateTime = new DateTime(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),
                cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6));
        return dateTime;
    }
}
