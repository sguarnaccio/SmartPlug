package com.example.sebastian.smartplug.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;


/**
 * Created by sebastian on 5/26/2017.
 */

public class DeviceDatabaseDBHelper extends SQLiteOpenHelper {

    //If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "devicesdb.db";

    public DeviceDatabaseDBHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DEVICES_TABLE = "CREATE TABLE " + DeviceDatabaseContract.DeviceEntry.TABLE_NAME + " (" +
                DeviceDatabaseContract.DeviceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICEID + " TEXT NOT NULL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICENAME + " TEXT NOT NULL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_HOUSELOCATION + " TEXT NOT NULL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_SSIDNETWORK + " TEXT NOT NULL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_PASSWDNETWORK + " TEXT NOT NULL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_SSIDLOCAL + " TEXT NOT NULL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_IPLOCAL + " TEXT NOT NULL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_VOLTAGE + " REAL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_CURRENT + " REAL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_POWER + " REAL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_COSPHI + " REAL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_PWRTHRES + " REAL, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_SWITCHSTATUS + " INTEGER, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICESTATUS + " INTEGER, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_SENDNOTIFICATIONS + " INTEGER, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_HOURTOOPEN + " INTEGER, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_MINTOOPEN + " INTEGER, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_HOURTOCLOSE + " INTEGER, " +
                DeviceDatabaseContract.DeviceEntry.COLUMN_MINTOCLOSE + " INTEGER, " +
                "UNIQUE (" + DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICEID  + ") ON " +
                "CONFLICT IGNORE" + " );";
        Log.d("por","crear la base de datos");
        //final String SQL_CREATE_DEVICES_TABLE = "CREATE TABLE deviceslist (_id INTEGER PRIMARY KEY AUTOINCREMENT, devid TEXT NOT NULL, devname TEXT NOT NULL, UNIQUE (devid) ON CONFLICT IGNORE);";

        try{
            db.execSQL(SQL_CREATE_DEVICES_TABLE);
            Log.d("Creacion de ", "LA BASE DE DATOS");
            Log.d("Comando:  ", SQL_CREATE_DEVICES_TABLE);
        }
        catch(Exception e){
            Log.d("Error al crear ", "LA BASE DE DATOS");
            Log.d("Comando:  ", SQL_CREATE_DEVICES_TABLE);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + DeviceDatabaseContract.DeviceEntry.TABLE_NAME);
        onCreate(db);

    }
}
