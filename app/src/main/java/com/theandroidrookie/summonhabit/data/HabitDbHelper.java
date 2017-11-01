package com.theandroidrookie.summonhabit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jeff on 11/1/2017.
 */

public class HabitDbHelper extends SQLiteOpenHelper {
    private static final String TAG = HabitDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "tracker.db";
    private static final int DATABSE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + "(" +
                HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, " +
                HabitContract.HabitEntry.COLUMN_HABIT_COMMENT + " TEXT NOT NULL, " +
                HabitContract.HabitEntry.COLUMN_HABIT_DURATION + " INTEGER NOT NULL DEFAULT 10, " +
                HabitContract.HabitEntry.COLUMN_HABIT_REPEAT + " INTEGER NOT NULL DEFAULT 0, " +
                HabitContract.HabitEntry.COLUMN_HABIT_TIME + " TEXT );";
        db.execSQL(SQL_CREATE_HABIT_TABLE);
        Log.d("CHECK", SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Method to insert a record of habit in tracker database
     *
     * @param name     Short name
     * @param comment  description
     * @param duration duration of habit
     * @param repeat   no. of time to repeat in week
     * @param time     at what to remind
     */
    public void insertHabit(String name, String comment, int duration, int repeat, String time) {

        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, name);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_COMMENT, comment);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DURATION, duration);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_REPEAT, repeat);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TIME, time);

        SQLiteDatabase writableDatabase = getWritableDatabase();

        long result = writableDatabase.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
        if (result != -1) {
            Log.d(TAG, "Insert row succesful ID = " + result);
        } else {
            Log.d(TAG, "Insert row unsuccesful");
        }
    }

    /**
     * Method to read and print(in Logs) records present in database.
     *
     * @return Cursor object
     */
    public Cursor readHabit() {
        SQLiteDatabase readableDatabase = getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_COMMENT,
                HabitContract.HabitEntry.COLUMN_HABIT_DURATION,
                HabitContract.HabitEntry.COLUMN_HABIT_REPEAT,
                HabitContract.HabitEntry.COLUMN_HABIT_TIME
        };

        Cursor cursor = readableDatabase.query(HabitContract.HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }
}