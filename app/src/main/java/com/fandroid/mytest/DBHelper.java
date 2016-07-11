package com.fandroid.mytest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper{

    public static final int DATA_VERSION=1;
    public static final String DATABASE_NAME="contactDB";
    public static final String TABLE_CONTACTS="contacts";
    public static final String KEY_ID="_id";
    public static final String KEY_NAME="name";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_GENDER="gender";
    public static final String KEY_POSITION="position";
    SQLiteDatabase database;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + " (" + KEY_ID + " integer primary key, " + KEY_NAME + " text, "
                + KEY_PASSWORD + " text, " + KEY_GENDER + " text, " + KEY_POSITION + " text" + " )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
        public long AddUsers(String name,String Password,String Gender,String selectedItem) {
        database=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_PASSWORD, Password);
        cv.put(KEY_GENDER, Gender);
        cv.put(KEY_POSITION, selectedItem);
        return database.insert(TABLE_CONTACTS, null, cv);
        }
    public Cursor getUsers() {
        database=this.getReadableDatabase();
        return  database.query(TABLE_CONTACTS,null, null, null, null, null, null);
    }
}




