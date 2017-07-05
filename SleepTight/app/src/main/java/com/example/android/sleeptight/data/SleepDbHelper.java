package com.example.android.sleeptight.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.sleeptight.data.SleepContract.SleepEntry;

/**
 * Sleep Tight created by JCoupier on 04/07/2017.
 *
 * Database helper for Sleep Tight app. Manages database creation and version management.
 */
public class SleepDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    private static final String DATABASE_NAME = "sleep.db";

    /** Database version. If you change the database schema, you must increment the database version. */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link SleepDbHelper}.
     * @param context of the app
     */
    public SleepDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** This is called when the database is created for the first time. */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the asleep table
        String SQL_CREATE_ASLEEP_TABLE =  "CREATE TABLE " + SleepEntry.TABLE_NAME + " ("
                + SleepEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SleepEntry.COLUMN_ASLEEP_TYPE + " TEXT NOT NULL, "
                + SleepEntry.COLUMN_ASLEEP_DATE + " TEXT NOT NULL, "
                + SleepEntry.COLUMN_ASLEEP_DURATION + " INTEGER NOT NULL DEFAULT 0, "
                + SleepEntry.COLUMN_ASLEEP_FEELING + " INTEGER NOT NULL DEFAULT 0, "
                + SleepEntry.COLUMN_ASLEEP_COMMENT + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ASLEEP_TABLE);
    }

    /** This is called when the database needs to be upgraded. */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DELETE TABLE IF EXISTS " + SleepContract.SleepEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
