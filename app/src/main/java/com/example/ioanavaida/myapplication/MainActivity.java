package com.example.ioanavaida.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ioanavaida.myapplication.data.ActiPulsDbHelper;
import com.example.ioanavaida.myapplication.data.DoctorsOrdersContract;

public class MainActivity extends AppCompatActivity {

    private ActivityListAdapter mAdapter;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ArduinoActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView activitiesRecyclerView;

        // Set local attributes to corresponding views
        activitiesRecyclerView = (RecyclerView) this.findViewById(R.id.all_activities_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Create a DB helper (this will create the DB if run for the first time)
        ActiPulsDbHelper dbHelper = new ActiPulsDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        //Fill the database with fake data
        TestUtil.insertFakeData(mDb);
        // Run the getAllActivities function and store the result in a Cursor variable
        Cursor cursor = getAllActivities();
        // Create an adapter for that cursor to display the data
        mAdapter = new ActivityListAdapter(this, cursor);

        // Link the adapter to the RecyclerView
        activitiesRecyclerView.setAdapter(mAdapter);
    }

    private Cursor getAllActivities() {
        return mDb.query(
                DoctorsOrdersContract.DoctorsOrdersEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DATE
        );
    }
}