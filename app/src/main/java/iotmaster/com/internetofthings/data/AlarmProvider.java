package iotmaster.com.internetofthings.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import iotmaster.com.internetofthings.data.AlamContract.AlamEntry;

/**
 * Created by Abhishek on 02/08/2017.
 */

public class AlarmProvider extends ContentProvider {

    public static final int MATCH = 400;
    public static final int NO_MATCH = 401;

    public static final UriMatcher sUriMatcher = getMatch();

    AlarmHelper alarmHelper;

    @Override
    public boolean onCreate() {
        alarmHelper = new AlarmHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = alarmHelper.getReadableDatabase();
        Cursor c = null;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case MATCH:
                c = database.query(AlamEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;
            case NO_MATCH:
                c = database.query(AlamEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

                break;
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri appededUri = null;
        long row = -1;
        SQLiteDatabase database = alarmHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MATCH:
                row = database.insert(AlamEntry.TABLE_NAME, null, values);

                break;
            case NO_MATCH:
                row = database.insert(AlamEntry.TABLE_NAME, null, values);
                break;

        }
        if (row > 0) {
            appededUri = ContentUris.withAppendedId(uri, row);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return appededUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = alarmHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted = -1;
        switch (match) {
            case NO_MATCH:
                rowsDeleted = database.delete(AlamEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MATCH:
                rowsDeleted = database.delete(AlamEntry.TABLE_NAME, selection, selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    public static UriMatcher getMatch() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AlamContract.AUTHORITY, AlamContract.PATH, NO_MATCH);
        uriMatcher.addURI(AlamContract.AUTHORITY, AlamContract.PATH + "/#", MATCH);
        return uriMatcher;
    }
}
