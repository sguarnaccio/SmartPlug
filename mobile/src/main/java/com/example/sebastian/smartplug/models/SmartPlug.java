package com.example.sebastian.smartplug.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.sebastian.smartplug.database.DeviceDatabaseContract;
import com.example.sebastian.smartplug.database.DeviceDatabaseDBHelper;

import java.io.Serializable;

/**
 * Created by sebastian on 5/20/2017.
 */

public class SmartPlug implements Serializable{//implements serializable me permite enviar los datos de este objeto a traves de un intent hacia otras activities

    String deviceId;//id del dispositivo
    String ssidNetwork;//ssid de la red hogareña
    String passwordNetwork;//password de la red hogareña
    String ssidLocalServer;//nombre del AP del dispositivo
    String ipLocalServer;//ip del servidor local del dispositivo
    float voltage;
    float current;
    float power;
    double cosphi;
    float powerThreshold;
    boolean switchStatus;//On or Off
    String houseLocation;//Living, Kitchen, Room, etc
    String deviceName;//TV, Fridge, Air conditioneer, Lights, etc
    boolean deviceStatus;//connected or disconnected
    boolean sendNotifications;
    int hourToOpen;
    int minToOpen;
    int hourToClose;
    int minToClose;
    int lastUpdateMonth;
    int lastUpdateYear;
    int lastUpdateDay;
    int lastUpdateHour;
    int lastUpdatemin;


    public SmartPlug() {
        this.deviceId = "";
        this.ssidNetwork = "";
        this.passwordNetwork = "";
        this.ssidLocalServer = "";
        this.ipLocalServer = "";
        this.houseLocation = "";
        this.deviceName = "";
        this.deviceStatus = false;
        this.voltage = 0;
        this.current = 0;
        this.power = 0;
        this.cosphi = 0;
        this.switchStatus = false;
        this.powerThreshold = 0;
        this.sendNotifications = false;
        this.hourToOpen = -1;
        this.minToOpen = -1;
        this.hourToClose = -1;
        this.minToClose = -1;
        //this.lastUpdateMonth = -1;
        //this.lastUpdateYear = -1;
        //this.lastUpdateDay = -1;
        //this.lastUpdateHour = -1;
        //this.lastUpdatemin = -1;
    }

    public SmartPlug(String id, String ssidNetwork, String passwordNetwork, String ssidLocalServer, String ipLocalServer, float voltage, float current, float power, double cosphi, boolean switchStatus, String houseLocation, String deviceName, boolean deviceStatus, float powerThreshold, boolean sendNotifications, int hourToOpen, int minToOpen, int hourToClose, int minToClose) {
        this.deviceId = id;
        this.ssidNetwork = ssidNetwork;
        this.passwordNetwork = passwordNetwork;
        this.ssidLocalServer = ssidLocalServer;
        this.ipLocalServer = ipLocalServer;
        this.voltage = voltage;
        this.current = current;
        this.power = power;
        this.cosphi = cosphi;
        this.switchStatus = switchStatus;
        this.houseLocation = houseLocation;
        this.deviceName = deviceName;
        this.deviceStatus = deviceStatus;
        this.powerThreshold = powerThreshold;
        this.sendNotifications = sendNotifications;
        this.hourToOpen = hourToOpen;
        this.minToOpen = minToOpen;
        this.hourToClose = hourToClose;
        this.minToClose = minToClose;
        //this.lastUpdateMonth = lastUpdateMonth;
        //this.lastUpdateYear = lastUpdateYear;
        //this.lastUpdateDay = lastUpdateDay;
        //this.lastUpdateHour = lastUpdateHour;
        //this.lastUpdatemin = lastUpdatemin;
    }

    public SmartPlug(String id, String ssidNetwork, String passwordNetwork, String ssidLocalServer, String ipLocalServer, boolean deviceStatus) {
        this.deviceId = id;
        this.ssidNetwork = ssidNetwork;
        this.passwordNetwork = passwordNetwork;
        this.ssidLocalServer = ssidLocalServer;
        this.ipLocalServer = ipLocalServer;
        this.deviceStatus = deviceStatus;
        this.voltage = 0;
        this.current = 0;
        this.power = 0;
        this.cosphi = 0;
        this.switchStatus = false;
        this.houseLocation = "";
        this.deviceName = "";
        this.powerThreshold = 0;
        this.sendNotifications = false;
        this.hourToOpen = -1;
        this.minToOpen = -1;
        this.hourToClose = -1;
        this.minToClose = -1;
        //this.lastUpdateMonth = -1;
        //this.lastUpdateYear = -1;
        //this.lastUpdateDay = -1;
        //this.lastUpdateHour = -1;
        //this.lastUpdatemin = -1;
    }

    public SmartPlug(String id, String ssidNetwork, String passwordNetwork, String ssidLocalServer, String ipLocalServer, String houseLocation, String deviceName, boolean deviceStatus) {
        this.deviceId = id;
        this.ssidNetwork = ssidNetwork;
        this.passwordNetwork = passwordNetwork;
        this.ssidLocalServer = ssidLocalServer;
        this.ipLocalServer = ipLocalServer;
        this.houseLocation = houseLocation;
        this.deviceName = deviceName;
        this.deviceStatus = deviceStatus;
        this.voltage = 0;
        this.current = 0;
        this.power = 0;
        this.cosphi = 0;
        this.switchStatus = false;
        this.powerThreshold = 0;
        this.sendNotifications = false;
        this.hourToOpen = -1;
        this.minToOpen = -1;
        this.hourToClose = -1;
        this.minToClose = -1;
        //this.lastUpdateMonth = -1;
        //this.lastUpdateYear = -1;
        //this.lastUpdateDay = -1;
        //this.lastUpdateHour = -1;
        //this.lastUpdatemin = -1;
    }

    public String getId() {
        return deviceId;
    }

    public void setId(String id) {
        this.deviceId = id;
    }

    public String getSsidNetwork() {
        return ssidNetwork;
    }

    public void setSsidNetwork(String ssidNetwork) {
        this.ssidNetwork = ssidNetwork;
    }

    public String getPasswordNetwork() {
        return passwordNetwork;
    }

    public void setPasswordNetwork(String passwordNetwork) {
        this.passwordNetwork = passwordNetwork;
    }

    public String getSsidLocalServer() {
        return ssidLocalServer;
    }

    public void setSsidLocalServer(String ssidLocalServer) {
        this.ssidLocalServer = ssidLocalServer;
    }

    public String getIpLocalServer() {
        return ipLocalServer;
    }

    public void setIpLocalServer(String ipLocalServer) {
        this.ipLocalServer = ipLocalServer;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public double getCosphi() {
        return cosphi;
    }

    public void setCosphi(double cosphi) {
        this.cosphi = cosphi;
    }

    public boolean getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(boolean switchStatus) {
        this.switchStatus = switchStatus;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public boolean getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(boolean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public float getPowerThreshold() {
        return powerThreshold;
    }

    public void setPowerThreshold(float powerThreshold) {
        this.powerThreshold = powerThreshold;
    }

    public boolean isSendNotifications() {
        return sendNotifications;
    }

    public void setSendNotifications(boolean sendNotifications) {
        this.sendNotifications = sendNotifications;
    }

    public int getHourToOpen() {
        return hourToOpen;
    }

    public void setHourToOpen(int hourToOpen) {
        this.hourToOpen = hourToOpen;
    }

    public int getMinToOpen() {
        return minToOpen;
    }

    public void setMinToOpen(int minToOpen) {
        this.minToOpen = minToOpen;
    }

    public int getHourToClose() {
        return hourToClose;
    }

    public void setHourToClose(int hourToClose) {
        this.hourToClose = hourToClose;
    }

    public int getMinToClose() {
        return minToClose;
    }

    public void setMinToClose(int minToClose) {
        this.minToClose = minToClose;
    }

    // Create a SmartPlug object from a cursor
    public static SmartPlug fromCursor(Cursor curDevices) {
        String id = curDevices.getString(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICEID));
        String ssidNetwork = curDevices.getString(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_SSIDNETWORK));
        String passwordNetwork = curDevices.getString(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_PASSWDNETWORK));
        String ssidLocalServer = curDevices.getString(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_SSIDLOCAL));
        String ipLocalServer = curDevices.getString(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_IPLOCAL));
        float voltage = curDevices.getFloat(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_VOLTAGE));
        float current = curDevices.getFloat(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_CURRENT));
        float power = curDevices.getFloat(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_POWER));
        float cosphi = curDevices.getFloat(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_COSPHI));
        int switchStatusValue = curDevices.getInt(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_SWITCHSTATUS));
        boolean switchStatus;
        String houseLocation = curDevices.getString(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_HOUSELOCATION));
        String deviceName = curDevices.getString(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICENAME));
        int deviceStatusValue = curDevices.getInt(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICESTATUS));
        boolean deviceStatus;
        float powerThreshold = curDevices.getFloat(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_PWRTHRES));
        int sendNotificationsValue = curDevices.getInt(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_SENDNOTIFICATIONS));
        boolean sendNotifications;
        int hourToOpen = curDevices.getInt(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_HOURTOOPEN));;
        int minToOpen = curDevices.getInt(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_MINTOOPEN));;
        int hourToClose = curDevices.getInt(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_HOURTOCLOSE));;
        int minToClose = curDevices.getInt(curDevices.getColumnIndex(DeviceDatabaseContract.DeviceEntry.COLUMN_MINTOCLOSE));
        //lastupdate


        if(switchStatusValue == 1)
            switchStatus = true;
        else
            switchStatus = false;

        if(deviceStatusValue == 1)
            deviceStatus = true;
        else
            deviceStatus = false;

        if(sendNotificationsValue == 1)
            sendNotifications = true;
        else
            sendNotifications = false;
        //TODO ADD lastupdate

        return new SmartPlug(id, ssidNetwork, passwordNetwork, ssidLocalServer, ipLocalServer, voltage, current, power, cosphi, switchStatus, houseLocation, deviceName, deviceStatus, powerThreshold, sendNotifications, hourToOpen, minToOpen, hourToClose, minToClose);
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICEID, deviceId);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_SSIDNETWORK, ssidNetwork);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_PASSWDNETWORK, passwordNetwork);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_SSIDLOCAL, ssidLocalServer);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_IPLOCAL, ipLocalServer);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_VOLTAGE, voltage);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_CURRENT, current);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_POWER, power);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_COSPHI, cosphi);
        if(switchStatus)
            values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_SWITCHSTATUS, 1);
        else
            values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_SWITCHSTATUS, 0);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_HOUSELOCATION, houseLocation);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICENAME, deviceName);
        if(deviceStatus)
            values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICESTATUS, 1);
        else
            values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_DEVICESTATUS, 0);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_PWRTHRES, powerThreshold);
        if(sendNotifications)
            values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_SENDNOTIFICATIONS, 1);
        else
            values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_SENDNOTIFICATIONS, 0);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_HOURTOOPEN, hourToOpen);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_MINTOOPEN, minToOpen);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_HOURTOCLOSE, hourToClose);
        values.put(DeviceDatabaseContract.DeviceEntry.COLUMN_MINTOCLOSE, minToClose);
        //TODO ADD lastupdate

        return values;
    }

}

