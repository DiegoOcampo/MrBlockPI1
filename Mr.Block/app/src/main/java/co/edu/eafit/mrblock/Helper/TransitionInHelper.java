package co.edu.eafit.mrblock.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.transition.Transition;
import android.util.Log;

import co.edu.eafit.mrblock.Contracts.Contract;
import co.edu.eafit.mrblock.Entidades.DateTime;
import co.edu.eafit.mrblock.Entidades.TransitionBlock;
import co.edu.eafit.mrblock.Entidades.Ubicacion;

/**
 * Created by Usuario on 17/11/2015.
 */
public class TransitionInHelper {
    DBHelper dbHelper;
    public TransitionInHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    public void addTransition(TransitionBlock transition){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.TransitionContract.COLUMN_TYPEBLOCK, transition.getType());
        values.put(Contract.TransitionContract.COLUMN_BLOCK, transition.getBlock());
        db.insert(Contract.TransitionContract.TABLE_NAME, null, values);
        db.close();
    }

    public long delete(String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.delete(Contract.TransitionContract.TABLE_NAME, Contract.TransitionContract.COLUMN_TYPEBLOCK + "  =?",
                    new String[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public long deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.delete(Contract.TransitionContract.TABLE_NAME, null,null);
            //db.close();
        }catch (Exception e){
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
            return -1;
        }
    }

    public TransitionBlock getTransitionBlocked(String type){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.TransitionContract.TABLE_NAME, new String[]{
                Contract.TransitionContract.COLUMN_TYPEBLOCK,
                Contract.TransitionContract.COLUMN_BLOCK,},
                Contract.TransitionContract.COLUMN_TYPEBLOCK + "= '" + type + "'", null
                , null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        TransitionBlock transitionBlock = new TransitionBlock(cursor.getString(0),cursor.getInt(1));
        return transitionBlock;
    }
}
