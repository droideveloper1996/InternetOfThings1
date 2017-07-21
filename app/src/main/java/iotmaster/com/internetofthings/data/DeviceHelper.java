package iotmaster.com.internetofthings.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iotmaster.com.internetofthings.data.DeviceContract.DeviceEntry;

/**
 * Created by Abhishek on 13/07/2017.
 */

public class DeviceHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "devices.db";
    public static final int DATABASE_VERSION = 1;

    public DeviceHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String MY_TABLE = "CREATE TABLE " + DeviceEntry.TABLE_NAME + " ( "
                + DeviceEntry._ID + " INTEGER PRIMARY KEY NOT NULL , "
                + DeviceEntry.DEVICE_NAME + " TEXT NOT NULL, "
                + DeviceEntry.UNIQUE_KEY + " TEXT NOT NULL, "
                + DeviceEntry.RELAY1 + " INTEGER DEFAULT 0, "
                + DeviceEntry.RELAY2 + " INTEGER DEFAULT 0, "
                + DeviceEntry.RELAY3 + " INTEGER DEFAULT 0, "
                + DeviceEntry.RELAY4 + " INTEGER DEFAULT 0, "
                + DeviceEntry.RELAY5 + " INTEGER DEFAULT 0  "
                + " );";
        db.execSQL(MY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
