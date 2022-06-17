package com.example.suzuki.mensetsu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
    static final String DATABASE_NAME = "sitsumon.db";
    static final int DATABASE_VERSION =1;

    public static final String TABLE_NAME = "mensetsu";
    public static final String COL_ID = "id";
    public static final String COL_SITSUMON = "sitsumon";

    protected final Context context;
    protected DatabaseHelper dbHelper;
    protected SQLiteDatabase db;

    public DBAdapter(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(this.context);
    }

    //SQLiteOpenHelper
    private static class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE" + TABLE_NAME + "("
                    + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_SITSUMON + "TEXT NO NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
            onCreate(db);
        }
    }

    public DBAdapter open(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public boolean deleteAllCompanyname(){
        return db.delete(TABLE_NAME,null,null)>0;
    }

    public  boolean deleteCompanyname(int id){
        return db.delete(TABLE_NAME,COL_ID + "=" +id,null)>0;
    }

    public Cursor getAllcompanyname(){
        return db.query(TABLE_NAME,null,null,null,null,null,null);
    }

    public  void saveCompanyname(String sitsumon){
        ContentValues values = new ContentValues();
        values.put(COL_SITSUMON,sitsumon);
        db.insertOrThrow(TABLE_NAME,null,values);
    }
}


