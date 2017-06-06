package com.example.sebastian.smartplug.database;


import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by sebastian on 5/26/2017.
 */

public class DeviceDatabaseContract {

    public static final String CONTENT_AUTHORITY = "SebastianGuarnaccio.SmartPlug";//tiene q ser el mismo q el que esta en el manifiesto dentro de providers
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DEVICE = "Devices";

    public static final class DeviceEntry implements BaseColumns{

        public static final String TABLE_NAME = "Devices";
        public static final String COLUMN_DEVICEID = "DevId";
        public static final String COLUMN_SSIDNETWORK = "SSIDNWK";
        public static final String COLUMN_PASSWDNETWORK = "PASSWDNWK";
        public static final String COLUMN_SSIDLOCAL = "SSIDLOCAL";//nombre del AP del dispositivo
        public static final String COLUMN_IPLOCAL = "IPLOCAL";//ip del servidor local del dispositivo
        public static final String COLUMN_VOLTAGE = "Voltage";
        public static final String COLUMN_CURRENT = "Current";
        public static final String COLUMN_POWER = "Power";
        public static final String COLUMN_COSPHI = "Cosphi";
        public static final String COLUMN_PWRTHRES = "PowerThres";
        public static final String COLUMN_SWITCHSTATUS = "SwitchStatus";//On or Off
        public static final String COLUMN_HOUSELOCATION = "HouseLocation";//Living, Kitchen, Room, etc
        public static final String COLUMN_DEVICENAME = "DeviceName";//TV, Fridge, Air conditioneer, Lights, etc
        public static final String COLUMN_DEVICESTATUS = "DeviceStatus";//connected or disconnected
        public static final String COLUMN_SENDNOTIFICATIONS = "SendNotifications";
        public static final String COLUMN_HOURTOOPEN = "HourToOpen";
        public static final String COLUMN_MINTOOPEN = "MinToOpen";
        public static final String COLUMN_HOURTOCLOSE = "HourToClose";
        public static final String COLUMN_MINTOCLOSE = "MinToClose";



        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEVICE).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DEVICE;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DEVICE;


    }

}
