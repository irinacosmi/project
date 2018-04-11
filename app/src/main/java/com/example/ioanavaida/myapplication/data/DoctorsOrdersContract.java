package com.example.ioanavaida.myapplication.data;

import android.provider.BaseColumns;

public class DoctorsOrdersContract {

    public static final class DoctorsOrdersEntry implements BaseColumns{

        public static final String TABLE_NAME = "doctorsOrders";
        public static final String COLUMN_ACTIVITY = "activity";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_DATE = "date";


    }
}
