package com.example.androidlabs;

import android.app.Activity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "CST2335";
    //any time you make any changes to the database, change the version number to get rid of any bugs
    private static final int VERSION_NUM = 2;
    public static final String TABLE_NAME = "MyMessages";
    public static final String COL_ID = "_id";
    public static final String COL_TYPE = "type";
    public static final String COL_MESSAGE = "message";

    public MyDatabaseOpenHelper (Activity ctx){
        //The factory parameter should be null, unless you know a lot about
        // Database Memory management
        super( ctx, DATABASE_NAME,null, VERSION_NUM);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //executes whatever query you pass inside this method as an argument
        //to make sure database is created check adb shell --> /data/data/com.example.androidlabs/databases
        db.execSQL("CREATE TABLE " + TABLE_NAME +" ( "
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TYPE + " INTEGER, " + COL_MESSAGE + " TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i("Database upgrade", "Old version:" + oldVersion + "New version:" + newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i("Database upgrade", "Old version:" + oldVersion + "New version:" + newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    public static int getVersionNum() {
        return VERSION_NUM;
    }

}




