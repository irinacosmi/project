package com.example.ioanavaida.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioanavaida.myapplication.data.DoctorsOrdersContract;

import java.text.SimpleDateFormat;
import java.util.Date;

class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Constructor using the context and the db cursor
     */
    public ActivityListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_list_item, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {

        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return;
        String name = mCursor.getString(mCursor.getColumnIndex(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_ACTIVITY));
        int time = mCursor.getInt(mCursor.getColumnIndex(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DURATION));
        Date date = new Date(mCursor.getLong(mCursor.getColumnIndex(DoctorsOrdersContract.DoctorsOrdersEntry.COLUMN_DATE)));
        // Display the activity name
        holder.nameTextView.setText(name);
        // Display the activity duration
        holder.timeTextView.setText(String.valueOf(time));
        // Display the activity date
        // TODO format the date
        holder.dateTextView.setText(String.valueOf(dFormat.format(date)));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class ActivityViewHolder extends RecyclerView.ViewHolder {

        // Will display the activity name
        TextView nameTextView;
        // Will display the time for the activity
        TextView timeTextView;
        // Will display the date for the activity
        TextView dateTextView;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.activity_name);
            timeTextView = (TextView) itemView.findViewById(R.id.activity_time);
            dateTextView = (TextView) itemView.findViewById(R.id.activity_date);
        }

    }
}
