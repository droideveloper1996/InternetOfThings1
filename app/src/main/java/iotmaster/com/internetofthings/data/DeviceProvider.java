package iotmaster.com.internetofthings.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import iotmaster.com.internetofthings.data.DeviceContract.DeviceEntry;


/**
 * Created by Abhishek on 13/07/2017.
 */

public class DeviceProvider extends ContentProvider {
    public static final int MATCH_WITHOUT_ID = 200;
    public static final int MATCH_WITH_ID = 201;
    public static final UriMatcher sUriMatcher = getMatch();
    DeviceHelper mDeviceHelper;

    private static UriMatcher getMatch() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DeviceContract.AUTHORITY, DeviceContract.PATH, MATCH_WITHOUT_ID);
        uriMatcher.addURI(DeviceContract.AUTHORITY, DeviceContract.PATH + "/#", MATCH_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mDeviceHelper = new DeviceHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase = new DeviceHelper(getContext()).getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        switch (match) {
            case MATCH_WITH_ID:

                cursor = sqLiteDatabase.query(DeviceEntry.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
                break;
            case MATCH_WITHOUT_ID:

                cursor = sqLiteDatabase.query(DeviceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        if (cursor != null && cursor.getCount() != 0) {
            return cursor;
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDeviceHelper.getWritableDatabase();

        //Sanity Checks.....
        Log.i("ABCDEF", "Reached Here");
        Log.i("ABCDEF", uri.toString());


        int match = sUriMatcher.match(uri);
        Uri appendenUri = null;
        long row;
        switch (match) {

            case MATCH_WITHOUT_ID:
                row = db.insert(DeviceEntry.TABLE_NAME, null, values);
                Log.i("Device Provider", Long.toString(row));
                if (row > 0) {
                    appendenUri = ContentUris.withAppendedId(uri, row);
                }
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return appendenUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = mDeviceHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int row = 0;
        switch (match) {
            case MATCH_WITH_ID:
                String id=uri.getPathSegments().get(1);
                String where="_id=?";
                String args[]=new String[]{id};
                row = sqLiteDatabase.delete(DeviceEntry.TABLE_NAME, where, args);

                break;
            case MATCH_WITHOUT_ID:

                row = sqLiteDatabase.delete(DeviceEntry.TABLE_NAME, null, null);

                break;

        }
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDeviceHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int row = 0;
        switch (match) {
            case MATCH_WITH_ID:
                row = database.update(DeviceEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        if (row > 0) {
            Toast.makeText(getContext(), "Update rows " + row, Toast.LENGTH_LONG).show();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return row;
    }


}
