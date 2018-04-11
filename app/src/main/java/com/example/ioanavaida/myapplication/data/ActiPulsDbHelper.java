package com.example.ioanavaida.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ioanavaida.myapplication.data.DoctorsOrdersContract.*;

public class ActiPulsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "actipuls.db";

    private static final int DATABASE_VERSION = 1;

    public ActiPulsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold  data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + DoctorsOrdersEntry.TABLE_NAME + " (" +
                DoctorsOrdersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DoctorsOrdersEntry.COLUMN_ACTIVITY + " TEXT NOT NULL, " +
                DoctorsOrdersEntry.COLUMN_DURATION + " INTEGER NOT NULL, " +
                DoctorsOrdersEntry.COLUMN_DATE + " DATE DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DoctorsOrdersEntry.TABLE_NAME);
        onCreate(db);
    }
}
