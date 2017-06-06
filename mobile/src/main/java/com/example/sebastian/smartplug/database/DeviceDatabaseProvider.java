package com.example.sebastian.smartplug.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sebastian on 5/27/2017.
 */

public class DeviceDatabaseProvider extends ContentProvider {



    private static final int DEVICEDATABASE = 100;
    private static final int DEVICEDATABASE_ID = 101;

    //The URI Matcher used by this content provider
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DeviceDatabaseDBHelper deviceDatabaseDBHelper;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DeviceDatabaseContract.CONTENT_AUTHORITY;

        //for each type of URI you want to add, create a corresponding code
        matcher.addURI(authority, DeviceDatabaseContract.PATH_DEVICE, DEVICEDATABASE);
        matcher.addURI(authority, DeviceDatabaseContract.PATH_DEVICE + "/#", DEVICEDATABASE_ID);

        return matcher;

    }






    @Override
    public boolean onCreate() {
        deviceDatabaseDBHelper = new DeviceDatabaseDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //Here's the switch statement that, given a URI, will determine what kind of request it is,
        //and query the database accordingly
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){

            case DEVICEDATABASE: {
                retCursor = deviceDatabaseDBHelper.getReadableDatabase().query(
                        DeviceDatabaseContract.DeviceEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case DEVICEDATABASE_ID: {
                retCursor = deviceDatabaseDBHelper.getReadableDatabase().query(
                        DeviceDatabaseContract.DeviceEntry.TABLE_NAME,
                        projection,
                        DeviceDatabaseContract.DeviceEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unkwon uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DEVICEDATABASE:
                return DeviceDatabaseContract.DeviceEntry.CONTENT_TYPE;
            case DEVICEDATABASE_ID:
                return DeviceDatabaseContract.DeviceEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = deviceDatabaseDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case DEVICEDATABASE: {
                long _id = db.insert(DeviceDatabaseContract.DeviceEntry.TABLE_NAME, null, contentValues);
                if( _id > 0)
                    returnUri = DeviceDatabaseContract.DeviceEntry.CONTENT_URI;
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                    //Log.d("Failed to insert"," row into" + uri);
                    //returnUri = DeviceDatabaseContract.DeviceEntry.CONTENT_URI;
                break;
            }
            default:
                throw new UnsupportedOperationException("Unkown uri: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = deviceDatabaseDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case DEVICEDATABASE: {
                rowsDeleted = db.delete(DeviceDatabaseContract.DeviceEntry.TABLE_NAME,selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unkown uri: " + uri);

        }
        //because a null deletes all rows
        if(selection == null || rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = deviceDatabaseDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case DEVICEDATABASE: {
                rowsUpdated = db.update(DeviceDatabaseContract.DeviceEntry.TABLE_NAME, values,selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unkown uri: " + uri);

        }

        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = deviceDatabaseDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DEVICEDATABASE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DeviceDatabaseContract.DeviceEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }

    }

}
