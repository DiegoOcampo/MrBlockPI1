package co.edu.eafit.mrblock.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
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
        values.put(Contract.ContactInContract.COLUMN_TYPE, contact.getType());
        db.insert(Contract.ContactInContract.TABLE_NAME, null, values);
        db.close();

    }

    public Contact getContact(String number){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.ContactInContract.TABLE_NAME,new String[]{
                Contract.ContactInContract.COLUMN_NUMBER,
                Contract.ContactInContract.COLUMN_NAME,
                Contract.ContactInContract.COLUMN_TYPE},
                Contract.ContactInContract.COLUMN_NUMBER + "='" + number+"'",null,
                null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Contact contact=new Contact(cursor.getString(0),cursor.getString(1),cursor.getString(2));
        return contact;
    }

    public ArrayList<Contact> getWhiteContacts(){
        String type = "white list";
        ArrayList<Contact> block= new ArrayList<Contact>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.ContactInContract.TABLE_NAME,new String[]{
                        Contract.ContactInContract.COLUMN_NUMBER,
                        Contract.ContactInContract.COLUMN_NAME,
                        Contract.ContactInContract.COLUMN_TYPE},
                Contract.ContactInContract.COLUMN_TYPE + "='" + type +"'",null,
                null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setNumber(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setType(cursor.getString(2));
                block.add(contact);
            }while (cursor.moveToNext());
        }
        return block;
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
                contact.setType(cursor.getString(2));
                block.add(contact);
            }while (cursor.moveToNext());
        }
        return block;
    }



    public long delete(Contact contact){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
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

    public long deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.delete(Contract.ContactInContract.TABLE_NAME, null,null);
            //db.close();
        }catch (Exception e){
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
            return -1;
        }
    }
}