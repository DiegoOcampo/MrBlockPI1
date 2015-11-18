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
    private static final int DATABASE_VERSION = 13;

    private static final String  TABLE_CONTACTS= " CREATE TABLE " + Contract.ContactInContract.TABLE_NAME
            + "(" + Contract.ContactInContract.COLUMN_NUMBER + " TEXT PRIMARY KEY, "
            + Contract.ContactInContract.COLUMN_NAME + " TEXT,"
            + Contract.ContactInContract.COLUMN_TYPE + " TEXT)";
    public static final String DELETE_CONTACTS = "DROP TABLE IF EXISTS " + Contract.ContactInContract.TABLE_NAME;

    public static final String TABLE_UBICATION = " CREATE TABLE " + Contract.UbicationContract.TABLE_NAME
            + "(" + Contract.UbicationContract.COLUMN_PLACE + " TEXT PRIMARY KEY, "
            + Contract.UbicationContract.COLUMN_LATITUDE + " REAL, "
            + Contract.UbicationContract.COLUMN_LONGITUDE + " REAL,"
            + Contract.UbicationContract.COLUMN_RADIUS + " REAL)";
    public static final String DELETE_UBICATION = " DROP TABLE IF EXISTS " + Contract.UbicationContract.TABLE_NAME;

    public static final String TABLE_TRANSITION = "CREATE TABLE " + Contract.TransitionContract.TABLE_NAME
            + "(" + Contract.TransitionContract.COLUMN_TYPEBLOCK + " TEXT PRIMARY KEY, "
            + Contract.TransitionContract.COLUMN_BLOCK + " INTEGER)";
    public static final String DELETE_TRANSITION = " DROP TABLE IF EXIST " + Contract.TransitionContract.TABLE_NAME;

    private static final String TABLE_CALLS = " CREATE TABLE " + Contract.CallInContract.TABLE_NAME
            + "(" + Contract.CallInContract.COLUMN_NUMBER + " TEXT PRIMARY KEY, "
            + Contract.CallInContract.COLUMN_NAME + " TEXT,"
            + Contract.CallInContract.COLUMN_TYPE + " TEXT)";
    public static final String DELETE_CALLS = "DROP TABLE IF EXISTS " + Contract.CallInContract.TABLE_NAME;


    private static final String TABLE_DATE = " CREATE TABLE " + Contract.DateContract.TABLE_NAME+ "("
            + Contract.DateContract.COLUMN_DATENAME + " TEXT PRIMARY KEY, "
            + Contract.DateContract.COLUMN_YEAR_1 + " INTEGER,"
            + Contract.DateContract.COLUMN_MONTH_1 + " INTEGER,"
            + Contract.DateContract.COLUMN_DAY_1 + " INTEGER,"
            + Contract.DateContract.COLUMN_HOUR_1 + " INTEGER,"
            + Contract.DateContract.COLUMN_MINUTE_1 + " INTEGER,"
            + Contract.DateContract.COLUMN_SECOND_1 + " INTEGER,"
            + Contract.DateContract.COLUMN_YEAR_2 + " INTEGER,"
            + Contract.DateContract.COLUMN_MONTH_2 + " INTEGER,"
            + Contract.DateContract.COLUMN_DAY_2 + " INTEGER,"
            + Contract.DateContract.COLUMN_HOUR_2 + " INTEGER,"
            + Contract.DateContract.COLUMN_MINUTE_2 + " INTEGER,"
            + Contract.DateContract.COLUMN_SECOND_2 + " INTEGER,"
            + Contract.DateContract.COLUMN_TYPE + " TEXT"
            + ")";
    public static final String DELETE_DATE = "DROP TABLE IF EXISTS " + Contract.DateContract.TABLE_NAME;

    private static final String TABLE_COMPLETE = " CREATE TABLE " + Contract.CompleteContract.TABLE_NAME + "("
            + Contract.CompleteContract.COLUMN_BLOCKNAME + " TEXT PRIMARY KEY,"
            + Contract.CompleteContract.COLUMN_INCALLS + " INTEGER,"
            + Contract.CompleteContract.COLUMN_OUTCALLS + " INTEGER,"
            + Contract.CompleteContract.COlUMN_INSMS + " INTEGER,"
            + Contract.CompleteContract.COLUMN_OUTSMS + " INTEGER,"
            + Contract.CompleteContract.COLUMN_TYPE + " TEXT)";

    public static final String DELETE_COMPLETE = "DROP TABLE IF EXISTS " + Contract.CompleteContract.TABLE_NAME;

    public static final String TABLE_TYPE = " CREATE TABLE " +Contract.TypeContract.TABLE_NAME
            + "(" + Contract.TypeContract.COLUMN_ID + " TEXT PRIMARY KEY,"
            + Contract.TypeContract.COLUMN_TYPE + " TEXT)";

    public static final String DELETE_TYPE = "DROP TABLE IF EXISTS " + Contract.TypeContract.TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CONTACTS);
        db.execSQL(TABLE_CALLS);
        db.execSQL(TABLE_DATE);
        db.execSQL(TABLE_UBICATION);
        db.execSQL(TABLE_COMPLETE);
        db.execSQL(TABLE_TYPE);
        db.execSQL(TABLE_TRANSITION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_CONTACTS);
        db.execSQL(DELETE_CALLS);
        db.execSQL(DELETE_DATE);
        db.execSQL(DELETE_UBICATION);
        db.execSQL(DELETE_COMPLETE);
        db.execSQL(DELETE_TYPE);
        db.execSQL(DELETE_TRANSITION);
        onCreate(db);
    }
}