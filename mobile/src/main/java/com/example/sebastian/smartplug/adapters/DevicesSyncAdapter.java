package com.example.sebastian.smartplug.adapters;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;

import com.example.sebastian.smartplug.database.DeviceDatabaseContract;
import com.example.sebastian.smartplug.models.SmartPlug;
import com.example.sebastian.smartplug.services.RestApiServiceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sebastian on 5/27/2017.
 */
//para actualizar periodicamente la base de datos si es q los datos obtenidos en la nube son mas recientes q los del movil
public class DevicesSyncAdapter extends AbstractThreadedSyncAdapter {
    ContentResolver mContentResolver;

    public DevicesSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        //mAccountManager = AccountManager.get(context);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d("Ejecutando: ", "AsyncTask");
        Log.d("Ejecutando: ", "Exito!");
        try {
            // Get the auth token for the current account
            //String authToken = mAccountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
            RestApiServiceHelper restApiServiceHelper = new RestApiServiceHelper();//TODO clase q voy a crear para implementar la RESTAPI

            // Get devices from the remote server
            List<SmartPlug> remoteDevices = restApiServiceHelper.GetDevices("devices");//authToken

            // Get devices from the local storage
            HashMap<String, Integer> _idDeviceMap = new HashMap<String, Integer>();
            ArrayList<SmartPlug> local_Devices = new ArrayList<SmartPlug>();
            Cursor curDevices = provider.query(DeviceDatabaseContract.DeviceEntry.CONTENT_URI, null, null, null, null);
            if (curDevices != null) {
                while (curDevices.moveToNext()) {
                    local_Devices.add(SmartPlug.fromCursor(curDevices));
                    _idDeviceMap.put(curDevices.getString(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry._ID)),curDevices.getInt(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry._ID)));
                }
                curDevices.close();
            }
            // TODO See what Local shows are missing on Remote
            //esto lo tengo q hacer asincronicamente, porque cuando yop hago un cambio desde el movil es para q el dispositivo tome una accion y quiero q la haga inmediatamente
            //uso directamente una asynctask, thread o handler para realizarlo

            // TODO See what Remote shows are missing on Local
            //if lastupdate on the server is more recent than the one on the database, then I update the database

            ContentValues updatedValues = new ContentValues();
            int rowsUpdated;
            for(SmartPlug remoteDevice : remoteDevices){
                if(!local_Devices.contains(remoteDevice)){

                    updatedValues = remoteDevice.getContentValues();
                    rowsUpdated = provider.update(DeviceDatabaseContract.DeviceEntry.CONTENT_URI, updatedValues, "DevId=\"" + remoteDevice.getId() + "\"", null);
                    Log.d("Rows Updated: ", Integer.toString(rowsUpdated));
                    if(rowsUpdated == 0){//si no actualizo ninguna fila es porque el dispositivo no existia y se trata de uno nuevo
                        provider.insert(DeviceDatabaseContract.DeviceEntry.CONTENT_URI, updatedValues);
                    }


                }
            }

            //TODO TENGO Q ACTUALIZAR LOS DATOS CADA 1 HORA


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
