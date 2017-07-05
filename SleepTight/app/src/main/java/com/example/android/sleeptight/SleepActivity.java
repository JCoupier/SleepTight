package com.example.android.sleeptight;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sleeptight.data.SleepContract.SleepEntry;
import com.example.android.sleeptight.data.SleepDbHelper;

/**
 * Sleep Tight created by JCoupier on 04/07/2017.
 */
public class SleepActivity extends AppCompatActivity {

    /** The Database */
    private SQLiteDatabase mDb;

    /** Database helper that will provide us access to the database */
    private SleepDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new SleepDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Helper method to display information in the onscreen TextView about the state of
     * the sleep habit database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        mDb = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {SleepEntry._ID, SleepEntry.COLUMN_ASLEEP_TYPE,
                SleepEntry.COLUMN_ASLEEP_DATE, SleepEntry.COLUMN_ASLEEP_DURATION,
                SleepEntry.COLUMN_ASLEEP_FEELING, SleepEntry.COLUMN_ASLEEP_COMMENT };

        // Perform a query on the asleep table
        Cursor cursor = mDb.query(SleepEntry.TABLE_NAME, projection, null, null, null, null, null);

        // Find the TextView to update with the content of the Database
        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            // Create a header in the Text View that looks like this:
            // The asleep table contains <number of rows in Cursor> entries.
            // _id - name - breed - gender - weight
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The asleep table contains " + cursor.getCount() + " enties.\n\n");
            displayView.append(SleepEntry._ID + " - " + SleepEntry.COLUMN_ASLEEP_TYPE + " - " +
                    SleepEntry.COLUMN_ASLEEP_DATE + " - " + SleepEntry.COLUMN_ASLEEP_DURATION + " - " +
                    SleepEntry.COLUMN_ASLEEP_FEELING + " - " + SleepEntry.COLUMN_ASLEEP_COMMENT + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(SleepEntry._ID);
            int typeColumnIndex = cursor.getColumnIndex(SleepEntry.COLUMN_ASLEEP_TYPE);
            int dateColumnIndex = cursor.getColumnIndex(SleepEntry.COLUMN_ASLEEP_DATE);
            int durationColumnIndex = cursor.getColumnIndex(SleepEntry.COLUMN_ASLEEP_DURATION);
            int feelingColumnIndex = cursor.getColumnIndex(SleepEntry.COLUMN_ASLEEP_FEELING);
            int commentColumnIndex = cursor.getColumnIndex(SleepEntry.COLUMN_ASLEEP_COMMENT);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentType = cursor.getString(typeColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                int currentFeeling = cursor.getInt(feelingColumnIndex);
                String currentComment = cursor.getString(commentColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append("\n" + currentID + " - " + currentType + " - " + currentDate + " - " +
                        currentDuration + " - " + currentFeeling + " - " + currentComment);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded sleep data into the database. For debugging purposes only.
     */
    private void insertSleep() {
        // Gets the database in write mode
        mDb = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and a dummy Sandman's sleep attributes are the values.
        ContentValues values = new ContentValues();
        values.put(SleepEntry.COLUMN_ASLEEP_TYPE, "Night");
        values.put(SleepEntry.COLUMN_ASLEEP_DATE, "04/07/17");
        values.put(SleepEntry.COLUMN_ASLEEP_DURATION, 480);
        values.put(SleepEntry.COLUMN_ASLEEP_FEELING, SleepEntry.FEELING_WELL);
        values.put(SleepEntry.COLUMN_ASLEEP_COMMENT, "Nothing to report");

        // Insert the Dummy data with the ContentValues into the database
        mDb.insert(SleepEntry.TABLE_NAME, null, values);
        // Close the instance of the Database
        mDb.close();
    }

    private Cursor readSleep(){
        // Open the Database to read from it
        mDb = mDbHelper.getReadableDatabase();

        // Perform a query on the asleep table (select all)
        Cursor cursor = mDb.query(SleepEntry.TABLE_NAME, null, null, null, null, null, null);
        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.
        cursor.close();

        // Close the instance of the Database
        mDb.close();

        // Return the cursor
        return cursor;
    }


    private void deleteAllSleep() {
        // Gets the database in write mode
        mDb = mDbHelper.getWritableDatabase();
        // Delete all entries
        mDb.delete(SleepEntry.TABLE_NAME, null, null);
        // Close the instance of the Database
        mDb.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_sleep.xml.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_sleep, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertSleep();
                displayDatabaseInfo();

                // Simple usage of the readSleep method
                Cursor cursor = readSleep();
                Toast.makeText(this, cursor.toString(), Toast.LENGTH_SHORT).show();

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllSleep();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
