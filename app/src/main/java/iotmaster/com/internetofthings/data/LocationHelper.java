package iotmaster.com.internetofthings.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iotmaster.com.internetofthings.data.LocationContract.LocationEntry;

/**
 * Created by Abhishek on 18/07/2017.
 */

public class LocationHelper extends SQLiteOpenHelper {
    // The database name
    private static final String DATABASE_NAME = "shushme.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public LocationHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        final String SQL_CREATE_PLACES_TABLE = "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
                LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LocationEntry.PLACE_ID + " TEXT NOT NULL, " +
                LocationEntry.PLACE_NAME+ " TEXT, "+
                LocationEntry.PLACE_LONGITUTE+" TEXT, "+
                LocationEntry.PLACE_LATITUTE+" TEXT "+
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_PLACES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}