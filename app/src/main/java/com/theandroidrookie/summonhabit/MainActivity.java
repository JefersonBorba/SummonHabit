package com.theandroidrookie.summonhabit;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.theandroidrookie.summonhabit.data.HabitContract;
import com.theandroidrookie.summonhabit.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HabitDbHelper dbHelper = new HabitDbHelper(this);

        //Insert record 1
        dbHelper.insertHabit("Walk",
                "Wake up early and go for a walk in the park",
                60,
                7,
                "5 AM");

        //Insert record 2
        dbHelper.insertHabit("Reading",
                "Continue reading Computer Science Distilled",
                120,
                2,
                "4 PM");

        //Reading from database and printing them in Logs
        Cursor cursor = dbHelper.readHabit();
        try {
            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME);
            int commentColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_COMMENT);
            int durationColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_DURATION);
            int repeatColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_REPEAT);
            int timeColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_TIME);

            Log.d(TAG, "ID | NAME | COMMENT | DURATION | REPEAT | TIME");
            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String comment = cursor.getString(commentColumnIndex);
                int duration = cursor.getInt(durationColumnIndex);
                int repeat = cursor.getInt(repeatColumnIndex);
                String time = cursor.getString(timeColumnIndex);

                Log.d(TAG, id + " " + name + " " + comment + " " + duration + " " + repeat + " " + time);
            }
        } finally {
            cursor.close();
        }
    }
}
