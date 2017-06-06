package com.example.sebastian.smartplug.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sebastian.smartplug.adapters.DevicesSyncAdapter;

/**
 * Created by sebastian on 5/28/2017.
 */

public class DevicesSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static DevicesSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new DevicesSyncAdapter(getApplicationContext(), true);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ejecutando ", "SyncTaskService");
        return sSyncAdapter.getSyncAdapterBinder();

    }


}
