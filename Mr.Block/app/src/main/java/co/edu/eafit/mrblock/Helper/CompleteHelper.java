package co.edu.eafit.mrblock.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.edu.eafit.mrblock.Contracts.Contract;
import co.edu.eafit.mrblock.Entidades.Complete;

/**
 * Created by juan on 17/10/15.
 */
public class CompleteHelper {
    DBHelper dbHelper;
    public CompleteHelper(Context context){
        dbHelper = new DBHelper(context);
    }
    public void addComplete(Complete complete){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.CompleteContract.COLUMN_BLOCKNAME, complete.getBlockName());
        values.put(Contract.CompleteContract.COLUMN_INCALLS, complete.getInCalls());
        values.put(Contract.CompleteContract.COLUMN_OUTCALLS, complete.getOutCalls());
        values.put(Contract.CompleteContract.COlUMN_INSMS,complete.getInSms());
        values.put(Contract.CompleteContract.COLUMN_OUTSMS,complete.getOutSms());
        values.put(Contract.CompleteContract.COLUMN_TYPE,complete.getType());
        db.insert(Contract.CompleteContract.TABLE_NAME, null, values);
        db.close();
    }

    public Complete getComplete(String blockName){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.CompleteContract.TABLE_NAME,new String[]{
                        Contract.CompleteContract.COLUMN_BLOCKNAME,
                        Contract.CompleteContract.COLUMN_INCALLS,
                        Contract.CompleteContract.COLUMN_OUTCALLS,
                        Contract.CompleteContract.COlUMN_INSMS,
                        Contract.CompleteContract.COLUMN_OUTSMS,
                        Contract.CompleteContract.COLUMN_TYPE},
                Contract.CompleteContract.COLUMN_BLOCKNAME + "='" + blockName+"'",null,
                null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Complete complete = new Complete(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5));
        return complete;
    }

    public long delete(String blockName){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.delete(Contract.CompleteContract.TABLE_NAME, Contract.CompleteContract.COLUMN_BLOCKNAME + "  =?",
                    new String[]{blockName});
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /*public long delete(Complete complete){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.delete(Contract.CompleteContract.TABLE_NAME, Contract.CompleteContract.COLUMN_BLOCKNAME + "  =?",
                    new String[]{complete.getBlockName()});
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }*/

    public ArrayList<Complete> getAllComplete(){
        ArrayList<Complete> block= new ArrayList<Complete>();
        String selectQuery = "SELECT  * FROM " + Contract.CompleteContract.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Complete complete = new Complete();
                complete.setBlockName(cursor.getString(0));
                complete.setInCalls(cursor.getInt(1));
                complete.setOutCalls(cursor.getInt(2));
                complete.setInSms(cursor.getInt(3));
                complete.setOutSms(cursor.getInt(4));
                complete.setType(cursor.getString(5));
                block.add(complete);
            }while (cursor.moveToNext());
        }
        return block;
    }



}
