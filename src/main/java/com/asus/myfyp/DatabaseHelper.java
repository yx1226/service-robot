package com.asus.myfyp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Outsider.db";
    public static final String TABLE_NAME = "tbl_outsider";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "Nama";
    public static final String COL_PHONE =  "Nombor Telefon";
    public static final String COL_PHOTO = "Foto";
    public static final String COL_DATE = "Date";
    public static final String COL_POSITION = "Position";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME +"TEXT, " +
                COL_PHONE +"TEXT, " +
                COL_POSITION + "TEXT, "+
                COL_DATE + "DATETIME, " +
                COL_PHOTO + "TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +"" + TABLE_NAME);
        onCreate(db);
    }
}