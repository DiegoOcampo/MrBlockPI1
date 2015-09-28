package co.edu.eafit.mrblock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Entidades.Contact;

/**
 * Created by juan on 25/09/15.
 */
public class ContactDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Bloqueados";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE = " CREATE TABLE " + BlockContract.BlockEntry.TABLE_NAME
            + "(" + BlockContract.BlockEntry.COLUMN_NUMBER + " TEXT PRIMARY KEY, "
            + BlockContract.BlockEntry.COLUMN_NAME + " TEXT)";
    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + BlockContract.BlockEntry.TABLE_NAME;

    public ContactDbHelper(Context context) {
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

    public void addContact(Contact contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BlockContract.BlockEntry.COLUMN_NUMBER, contact.getNumber());
        values.put(BlockContract.BlockEntry.COLUMN_NAME, contact.getName());
        db.insert(BlockContract.BlockEntry.TABLE_NAME, null, values);
        db.close();

    }

    public Contact getContact(int number){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BlockContract.BlockEntry.TABLE_NAME,new String[]{
                BlockContract.BlockEntry.COLUMN_NUMBER, BlockContract.BlockEntry.COLUMN_NAME},
                BlockContract.BlockEntry.COLUMN_NUMBER + "=?", new String[]{String.valueOf(number)},
                null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Contact contact=new Contact(cursor.getString(0),cursor.getString(1));
        return contact;
    }

    public ArrayList<String> getAllContact(){
        ArrayList<String> block= new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + BlockContract.BlockEntry.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setNumber(cursor.getString(0));
                contact.setName(cursor.getString(1));
                block.add(contact.getContact());
            }while (cursor.moveToNext());
        }
        return block;
    }

    public void delete(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        /*db.execSQL("DELETE FROM " + BlockContract.BlockEntry.TABLE_NAME +
        "WHERE " + BlockContract.BlockEntry.COLUMN_NUMBER + "='"+
        contact.getNumber() + "'");
        */
        db.delete(BlockContract.BlockEntry.TABLE_NAME, BlockContract.BlockEntry.COLUMN_NUMBER + "=?",
                new String [] {contact.getNumber()});
        db.close();
    }
}




/*
*
*
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PersonDbHelper {
    class Row extends Object {
        public long _Id;
        public String code;
        public String name;
        public String gender;
    }

    private static final String DATABASE_CREATE =
        "create table BIODATA(_id integer primary key autoincrement, "
            + "code text not null,"
            + "name text not null"
            +");";

    private static final String DATABASE_NAME = "PERSONALDB";

    private static final String DATABASE_TABLE = "BIODATA";

    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public PersonDbHelper(Context ctx) {
        try {
            db = ctx.openDatabase(DATABASE_NAME, null);
        } catch (FileNotFoundException e) {
            try {
                db =
                    ctx.createDatabase(DATABASE_NAME, DATABASE_VERSION, 0,
                        null);
                db.execSQL(DATABASE_CREATE);
            } catch (FileNotFoundException e1) {
                db = null;
            }
        }
    }

    public void close() {
        db.close();
    }

    public void createRow(String code, String name) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("code", code);
        initialValues.put("name", name);
        db.insert(DATABASE_TABLE, null, initialValues);
    }

    public void deleteRow(long rowId) {
        db.delete(DATABASE_TABLE, "_id=" + rowId, null);
    }

    public List<Row> fetchAllRows() {
        ArrayList<Row> ret = new ArrayList<Row>();
        try {
            Cursor c =
                db.query(DATABASE_TABLE, new String[] {
                    "_id", "code", "name"}, null, null, null, null, null);
            int numRows = c.count();
            c.first();
            for (int i = 0; i < numRows; ++i) {
                Row row = new Row();
                row._Id = c.getLong(0);
                row.code = c.getString(1);
                row.name = c.getString(2);
                ret.add(row);
                c.next();
            }
        } catch (SQLException e) {
            Log.e("Exception on query", e.toString());
        }
        return ret;
    }

    public Row fetchRow(long rowId) {
        Row row = new Row();
        Cursor c =
            db.query(true, DATABASE_TABLE, new String[] {
                "_id", "code", "name"}, "_id=" + rowId, null, null,
                null, null);
        if (c.count() > 0) {
            c.first();
            row._Id = c.getLong(0);
            row.code = c.getString(1);
            row.name = c.getString(2);
            return row;
        } else {
            row.rowId = -1;
            row.code = row.name= null;
        }
        return row;
    }

    public void updateRow(long rowId, String code, String name) {
        ContentValues args = new ContentValues();
        args.put("code", code);
        args.put("name", name);
        db.update(DATABASE_TABLE, args, "_id=" + rowId, null);
    }
    public Cursor GetAllRows() {
        try {
            return db.query(DATABASE_TABLE, new String[] {
                    "_id", "code", "name"}, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e("Exception on query", e.toString());
            return null;
        }
    }
}
*
* */