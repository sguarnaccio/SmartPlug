package com.example.sebastian.smartplug.Processor;

import android.content.Context;
import android.util.Log;

import com.example.sebastian.smartplug.models.SmartPlug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastian on 5/28/2017.
 */

public class RestApiProcessor {
    //private Context context;

    public RestApiProcessor(/*Context context*/){
        //this.context=context;

    }


    /*public <T> T toSmartPlug(JSONObject json){

    }*/

    public SmartPlug toSmartPlug(JSONObject json){
        SmartPlug smartPlug = new SmartPlug();

        try {
                smartPlug.setId(json.getString("DeviceId"));
            //TODO agregar estos 4 elementos al mensaje del esp8266
                //smartPlug.setSsidNetwork(json.getString("SsidNwk"));
                //smartPlug.setPasswordNetwork(json.getString("PasswdNwk"));
                //smartPlug.setSsidLocalServer(json.getString("SsidLocal"));
                //smartPlug.setIpLocalServer(json.getString("IpLocal"));
                smartPlug.setHouseLocation(json.getString("HouseLocation"));
                smartPlug.setDeviceName(json.getString("DeviceName"));
                if(json.getInt("DeviceStatus") == 1){
                    smartPlug.setDeviceStatus(true);
                }
                else{
                    smartPlug.setDeviceStatus(false);
                }

                smartPlug.setVoltage(Float.valueOf(json.getString("Voltage")));
                smartPlug.setCurrent(Float.valueOf(json.getString("Current")));
                smartPlug.setPower(Float.valueOf(json.getString("Power")));
                smartPlug.setCosphi(Float.valueOf(json.getString("CosPhi")));
                if(json.getInt("SwitchStatus") == 1){
                    smartPlug.setSwitchStatus(true);
                }
                else{
                    smartPlug.setSwitchStatus(false);
                }

                smartPlug.setPowerThreshold(Float.valueOf(json.getString("PowerThr")));
                if(json.getInt("SendNotification") == 1){
                    smartPlug.setSendNotifications(true);
                }
                else{
                    smartPlug.setSendNotifications(false);
                }

                smartPlug.setHourToOpen(json.getInt("HourToOpen"));
                smartPlug.setMinToOpen(json.getInt("MinToOpen"));
                smartPlug.setHourToClose(json.getInt("HourToClose"));
                smartPlug.setMinToClose(json.getInt("MinToClose"));
                //smartPlug.setLastUpdateYear(json.getInt("LastUpdateYear"));
                //smartPlug.setLastUpdateMonth(json.getString("LastUpdateMonth"));
                //smartPlug.setLastUpdateMonth(json.getString("LastUpdateDay"));
                //smartPlug.setLastUpdateHour(json.getString("LastUpdateHour"));
                //smartPlug.setLastUpdateMin(json.getString("LastUpdateMin"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return smartPlug;

    }

    public List<SmartPlug> toSmartPlugList(JSONObject json){
        List<SmartPlug> devices = new ArrayList<SmartPlug>();
        SmartPlug smartPlug = new SmartPlug();

        try {
            JSONArray smartPlugJsonarray = json.getJSONArray("item");
            Log.d("Array lenght: ", Integer.toString(smartPlugJsonarray.length()));
            for(int i = 0 ; i < smartPlugJsonarray.length() ; i++){
                smartPlug.setId(smartPlugJsonarray.getJSONObject(i).getString("DeviceId"));
                //TODO agregar estos 4 elementos al mensaje del esp8266
                //smartPlug.setSsidNetwork(smartPlugJsonarray.getJSONObject(i).getString("SsidNwk"));
                //smartPlug.setPasswordNetwork(smartPlugJsonarray.getJSONObject(i).getString("PasswdNwk"));
                //smartPlug.setSsidLocalServer(smartPlugJsonarray.getJSONObject(i).getString("SsidLocal"));
                //smartPlug.setIpLocalServer(smartPlugJsonarray.getJSONObject(i).getString("IpLocal"));
                smartPlug.setHouseLocation(smartPlugJsonarray.getJSONObject(i).getString("HouseLocation"));
                smartPlug.setDeviceName(smartPlugJsonarray.getJSONObject(i).getString("DeviceName"));
                if(smartPlugJsonarray.getJSONObject(i).getInt("DeviceStatus") == 1){
                    smartPlug.setDeviceStatus(true);
                }
                else{
                    smartPlug.setDeviceStatus(false);
                }
                smartPlug.setVoltage(Float.valueOf(smartPlugJsonarray.getJSONObject(i).getString("Voltage")));
                smartPlug.setCurrent(Float.valueOf(smartPlugJsonarray.getJSONObject(i).getString("Current")));
                smartPlug.setPower(Float.valueOf(smartPlugJsonarray.getJSONObject(i).getString("Power")));
                smartPlug.setCosphi(Float.valueOf(smartPlugJsonarray.getJSONObject(i).getString("CosPhi")));
                if(smartPlugJsonarray.getJSONObject(i).getInt("SwitchStatus") == 1){
                    smartPlug.setSwitchStatus(true);
                }
                else{
                    smartPlug.setSwitchStatus(false);
                }
                smartPlug.setPowerThreshold(Float.valueOf(smartPlugJsonarray.getJSONObject(i).getString("PowerThr")));
                if(smartPlugJsonarray.getJSONObject(i).getInt("SendNotification") == 1){
                    smartPlug.setSendNotifications(true);
                }
                else{
                    smartPlug.setSendNotifications(false);
                }

                smartPlug.setHourToOpen(smartPlugJsonarray.getJSONObject(i).getInt("HourToOpen"));
                smartPlug.setMinToOpen(smartPlugJsonarray.getJSONObject(i).getInt("MinToOpen"));
                smartPlug.setHourToClose(smartPlugJsonarray.getJSONObject(i).getInt("HourToClose"));
                smartPlug.setMinToClose(smartPlugJsonarray.getJSONObject(i).getInt("MinToClose"));
                //smartPlug.setLastUpdateYear(smartPlugJsonarray.getJSONObject(i).getInt("LastUpdateYear"));
                //smartPlug.setLastUpdateMonth(smartPlugJsonarray.getJSONObject(i).getString("LastUpdateMonth"));
                //smartPlug.setLastUpdateDay(smartPlugJsonarray.getJSONObject(i).getString("LastUpdateDay"));
                //smartPlug.setLastUpdateHour(smartPlugJsonarray.getJSONObject(i).getString("LastUpdateHour"));
                //smartPlug.setLastUpdateMin(smartPlugJsonarray.getJSONObject(i).getString("LastUpdateMin"));

                Log.d("Device name: ", smartPlugJsonarray.getJSONObject(i).getString("DeviceName"));
                devices.add(smartPlug);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return devices;
    }



}

