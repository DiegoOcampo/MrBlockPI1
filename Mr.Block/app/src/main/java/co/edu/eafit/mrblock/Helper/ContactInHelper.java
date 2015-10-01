package co.edu.eafit.mrblock.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Contracts.Contract;
import co.edu.eafit.mrblock.Entidades.Contact;

/**
 * Created by juan on 25/09/15.
 */
public class ContactInHelper  {

   DBHelper dbHelper;

    public ContactInHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    public void addContact(Contact contact){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.ContactInContract.COLUMN_NUMBER, contact.getNumber());
        values.put(Contract.ContactInContract.COLUMN_NAME, contact.getName());
        db.insert(Contract.ContactInContract.TABLE_NAME, null, values);
        db.close();

    }

    public Contact getContact(int number){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.ContactInContract.TABLE_NAME,new String[]{
                Contract.ContactInContract.COLUMN_NUMBER, Contract.ContactInContract.COLUMN_NAME},
                Contract.ContactInContract.COLUMN_NUMBER + "=?", new String[]{String.valueOf(number)},
                null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Contact contact=new Contact(cursor.getString(0),cursor.getString(1));
        return contact;
    }

    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> block= new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + Contract.ContactInContract.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setNumber(cursor.getString(0));
                contact.setName(cursor.getString(1));
                block.add(contact);
            }while (cursor.moveToNext());
        }
        return block;
    }

    public long delete(Contact contact){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        /*db.execSQL("DELETE FROM " + Contract.ContactInContract.TABLE_NAME +
        "WHERE " + Contract.ContactInContract.COLUMN_NUMBER + "='"+
        contact.getNumber() + "'");
        */
        try {
            return db.delete(Contract.ContactInContract.TABLE_NAME, Contract.ContactInContract.COLUMN_NUMBER + "  =?",
                    new String[]{contact.getNumber()});
            //db.close();
        }catch (Exception e){
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
            return -1;
        }
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