package com.example.sebastian.smartplug.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


import com.example.sebastian.smartplug.R;
import com.example.sebastian.smartplug.models.SmartPlug;

import org.json.JSONObject;

/**
 * Created by sebastian on 5/27/2017.
 */

public class RestApiService extends IntentService {

    public RestApiService() {
        super("RestApiService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SmartPlug smartPlug;
        RestApiServiceHelper caller = new RestApiServiceHelper();
        //JSONObject object = caller.GET(url);



        //String action = intent.getExtras().getString("action");
        String config = intent.getStringExtra("Config");
        if(config.equals("SendConfig")){
            String deviceId = intent.getStringExtra("DeviceId");
            caller.SendConfig("devices/control/" + deviceId, "{\"SwitchAction\":1}");
        }
        else if(config.equals("GetDeviceData")){
            String deviceId = intent.getStringExtra("DeviceId");
            smartPlug = caller.GetDeviceData("devices/data/" + deviceId);

            /*ProcessorFactory.createProcessor(getApplicationContext(), action)
                    .parse(object);*/

            Intent broadCastIntent = new Intent();
            broadCastIntent.setAction(""/*action*/);
            //envio por un intent los datos actualizados del dispositivo pedido
            broadCastIntent.putExtra("DEVICE", smartPlug);

            this.sendBroadcast(broadCastIntent);
        }

    }
}


