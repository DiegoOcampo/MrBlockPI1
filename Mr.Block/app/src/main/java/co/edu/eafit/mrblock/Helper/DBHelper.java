package co.edu.eafit.mrblock.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import co.edu.eafit.mrblock.Contracts.Contract;

/**
 * Created by juan on 30/09/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Block";
    private static final int DATABASE_VERSION = 5;

    private static final String  TABLE_CONTACTS= " CREATE TABLE " + Contract.ContactInContract.TABLE_NAME
            + "(" + Contract.ContactInContract.COLUMN_NUMBER + " TEXT PRIMARY KEY, "
            + Contract.ContactInContract.COLUMN_NAME + " TEXT)";
    public static final String DELETE_CONTACTS = "DROP TABLE IF EXISTS " + Contract.ContactInContract.TABLE_NAME;


    private static final String TABLE_CALLS = " CREATE TABLE " + Contract.CallInContract.TABLE_NAME
            + "(" + Contract.CallInContract.COLUMN_NUMBER + " TEXT PRIMARY KEY, "
            + Contract.CallInContract.COLUMN_NAME + " TEXT)";
    public static final String DELETE_CALLS = "DROP TABLE IF EXISTS " + Contract.CallInContract.TABLE_NAME;


    private static final String TABLE_DATE = " CREATE TABLE " + Contract.DateContract.TABLE_NAME+ "("
            + Contract.DateContract.COLUMN_NUMBER + " TEXT PRIMARY KEY, "
            + Contract.DateContract.COLUMN_YEAR + " INTEGER,"
            + Contract.DateContract.COLUMN_MONTH + " INTEGER,"
            + Contract.DateContract.COLUMN_DAY + " INTEGER,"
            + Contract.DateContract.COLUMN_HOUR + " INTEGER,"
            + Contract.DateContract.COLUMN_MINUTE + " INTEGER,"
            + Contract.DateContract.COLUMN_SECOND + " INTEGER"
            + ")";
    public static final String DELETE_DATE = "DROP TABLE IF EXISTS " + Contract.DateContract.TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CONTACTS);
        db.execSQL(TABLE_CALLS);
        db.execSQL(TABLE_DATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_CONTACTS);
        db.execSQL(DELETE_CALLS);
        db.execSQL(DELETE_DATE);
        onCreate(db);
    }
}