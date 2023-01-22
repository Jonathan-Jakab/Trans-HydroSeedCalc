package com.example.caltranscalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mylist.db";
    private static final String DB_TABLE = "mylist_data";

    //columns
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String COMPOST = "COMPOST";
    private static final String SEED = "SEED";
    private static final String FERTILIZER = "FERTILIZER";


    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NAME + " TEXT " +")";
    //COMPOST + " TEXT, " +
    //SEED + " TEXT, " +
    //FERTILIZER + "TEXT" + ")";




    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    public boolean addData(String name) //String compost, String seed, String fertilizer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        //contentValues.put(COMPOST,compost);
        //contentValues.put(SEED, seed);
        //contentValues.put(FERTILIZER, fertilizer);

        long result = db.insert(DB_TABLE, null, contentValues);

        //if data failed to insert, returns -1
        return result != -1;
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+ DB_TABLE;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }


    public boolean delete(String id) //String compost, String seed, String fertilizer)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(DB_TABLE, NAME + "=?", new String[]{String.valueOf(id)});
        //if data failed to delete, returns -1
        return result != -1;
    }

}
