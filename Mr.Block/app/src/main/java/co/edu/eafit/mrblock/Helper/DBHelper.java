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
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Block";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE = " CREATE TABLE " + Contract.CallInContract.TABLE_NAME
            + "(" + Contract.CallInContract.COLUMN_NUMBER + " TEXT PRIMARY KEY, "
            + Contract.CallInContract.COLUMN_NAME + " TEXT)";
    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + Contract.CallInContract.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    public void addCall(Call call){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.CallInContract.COLUMN_NUMBER, call.getNumber());
        values.put(Contract.CallInContract.COLUMN_NAME, call.getName());
        db.insert(Contract.CallInContract.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Call> getAllCall(){
        ArrayList<Call> block= new ArrayList<Call>();
        String selectQuery = "SELECT  * FROM " + Contract.CallInContract.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Call call = new Call();
                call.setNumber(cursor.getString(0));
                call.setName(cursor.getString(1));
                block.add(call);
            }while (cursor.moveToNext());
        }
        return block;
    }

}
