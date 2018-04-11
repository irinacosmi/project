package com.example.ioanavaida.myapplication;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ioanavaida.myapplication.data.DoctorsOrdersContract;

import java.util.ArrayList;
import java.util.List;

class TestUtil {
    public static void insertFakeData(SQLiteDatabase db) {
        if (db == null) {
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_ACTIVITY, "Jogging");
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DURATION, 15);
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DATE, Long.parseLong("1484457791000"));
        list.add(cv);

        cv = new ContentValues();
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_ACTIVITY, "Walking");
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DURATION, 10);
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DATE, Long.parseLong("1343805819061"));
        list.add(cv);

        cv = new ContentValues();
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_ACTIVITY, "Light Running");
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DURATION, 20);
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DATE, Long.parseLong("1343805819061"));
        list.add(cv);

        cv = new ContentValues();
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_ACTIVITY, "Cycling");
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DURATION, 35);
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DATE, Long.parseLong("1520832191000"));
        list.add(cv);

        cv = new ContentValues();
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_ACTIVITY, "Tenis");
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DURATION, 45);
        cv.put(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DATE, Long.parseLong("1529040191000"));
        list.add(cv);

        //insert all guests in one transaction
        try {
            db.beginTransaction();
            //clear the table first
            db.delete(DoctorsOrdersContract.DoctorsOrdersEntry.TABLE_NAME, null, null);
            //go through the list and add one by one
            for (ContentValues c : list) {
                db.insert(DoctorsOrdersContract.DoctorsOrdersEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //too bad :(
        } finally {
            db.endTransaction();
        }

    }
}

