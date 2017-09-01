package iotmaster.com.internetofthings.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iotmaster.com.internetofthings.data.AlamContract.AlamEntry;

/**
 * Created by Abhishek on 02/08/2017.
 */

public class AlarmHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "alarm.db";
    public static final int DATABASE_VERSION = 1;

    public AlarmHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String STATEMENT = "CREATE TABLE " + AlamEntry.TABLE_NAME + "( "
                + AlamEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                + AlamEntry.ACTIVE + " TEXT, "
                + AlamEntry.ACTION + " TEXT, "
                + AlamEntry.REPEAT + " TEXT, "
                + AlamEntry.TIME + " TEXT, "
                + AlamEntry.STATUS + " TEXT, "
                + AlamEntry.MONDAY + " TEXT, "
                + AlamEntry.TUESDAY + " TEXT, "
                + AlamEntry.WEDNESDAY + " TEXT, "
                + AlamEntry.THURSDAY + " TEXT, "
                + AlamEntry.FRIDAY + " TEXT, "
                + AlamEntry.SATURDAY + " TEXT, "
                + AlamEntry.SUNDAY + " TEXT "+


        ");";

        db.execSQL(STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
