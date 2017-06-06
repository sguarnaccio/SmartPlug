package com.example.sebastian.smartplug.fragments;



//import android.content.CursorLoader;
//import android.database.Cursor;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sebastian.smartplug.R;
import com.example.sebastian.smartplug.activities.MainActivity;
import com.example.sebastian.smartplug.adapters.DevicesRecyclerViewAdapter;
import com.example.sebastian.smartplug.database.DeviceDatabaseContract;
import com.example.sebastian.smartplug.models.SmartPlug;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;




/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceListFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rvDevices)
    RecyclerView rvDevices;
    Unbinder unbinder;
    private List<SmartPlug> devices;


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


    public DeviceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_device_list, container, false);

        unbinder = ButterKnife.bind(this, view);

        initializeData();

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rvDevices.setHasFixedSize(true);//todos los items van a tener el mismo tamaño
        rvDevices.setLayoutManager(llm);

        //DevicesRecyclerViewAdapter adapter = new DevicesRecyclerViewAdapter(getContext(), devices);
        //rvDevices.setAdapter(adapter);

        return view;
    }

    private void initializeData() {


        //TODO BORRAR UNA VEZ Q ACRUALICE EL SERVIDOR
        //ContentValues values = new ContentValues();
        //SmartPlug remoteDevice = new SmartPlug("AGS03126", "CASA", "1234", "SmartPlug AP", "192.168.4.9", 220, 3, 660, 0.79, false, "COMEDOR", "TV", true, 700, false, -1, -1, -1, -1);
        //values = remoteDevice.getContentValues();

        //getContext().getContentResolver().insert(DeviceDatabaseContract.DeviceEntry.CONTENT_URI,values);



        //TODO levanto los datos de los dispositivos a travez de la RESTAPI para hacer una query al servidor en la nube
        devices = new ArrayList<>();
        //devices.add(new SmartPlug("AGS03123", "CASA", "1234", "SmartPlug AP", "192.168.4.1", 220, 2, 440, 0.80, true, "Room", "TV", false, 500, false, -1, -1, -1, -1));
        //devices.add(new SmartPlug("AGS03124", "CASA", "1234", "SmartPlug AP", "192.168.4.5", 220, 3, 660, 0.79, false, "Room", "Lights", true, 700, false, -1, -1, -1, -1));

        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        getActivity().getSupportLoaderManager().restartLoader(0,null,this);//si ya estaba iniciado me aseguro de restartearlo


        //TODO IF DATABAE Is EMPTY THEN Call RESTAPI To Fill It
        //TODO SETUP THE PERIODIC SERVICE TO UPDATE THE DEVICE DATABASE FROM THE RESTAPI

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.CursorLoader(getContext(), DeviceDatabaseContract.DeviceEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {




        boolean switchStatus = false, deviceStatus = false, sendNotifications = false;
        while (data.moveToNext()) {
            if(data.getInt(data.getColumnIndex(COLUMN_SWITCHSTATUS)) == 1){
                switchStatus = true;
            }
            else{
                switchStatus = false;
            }

            if(data.getInt(data.getColumnIndex(COLUMN_DEVICESTATUS)) == 1){
                deviceStatus = true;
            }
            else{
                deviceStatus = false;
            }

            if(data.getInt(data.getColumnIndex(COLUMN_SENDNOTIFICATIONS)) == 1){
                sendNotifications = true;
            }
            else{
                sendNotifications = false;
            }
            //TODO falta device status
            devices.add(new SmartPlug(data.getString(data.getColumnIndex(COLUMN_DEVICEID)), data.getString(data.getColumnIndex(COLUMN_SSIDNETWORK)),
                    data.getString(data.getColumnIndex(COLUMN_PASSWDNETWORK)), data.getString(data.getColumnIndex(COLUMN_SSIDLOCAL)), data.getString(data.getColumnIndex(COLUMN_IPLOCAL)),
                    data.getFloat(data.getColumnIndex(COLUMN_VOLTAGE)), data.getFloat(data.getColumnIndex(COLUMN_CURRENT)), data.getFloat(data.getColumnIndex(COLUMN_POWER)),
                    data.getFloat(data.getColumnIndex(COLUMN_COSPHI)), switchStatus,data.getString(data.getColumnIndex(COLUMN_HOUSELOCATION)), data.getString(data.getColumnIndex(COLUMN_DEVICENAME)),
                    deviceStatus, data.getFloat(data.getColumnIndex(COLUMN_PWRTHRES)), sendNotifications, data.getInt(data.getColumnIndex(COLUMN_HOURTOOPEN)),
                    data.getInt(data.getColumnIndex(COLUMN_MINTOOPEN)), data.getInt(data.getColumnIndex(COLUMN_HOURTOCLOSE)), data.getInt(data.getColumnIndex(COLUMN_MINTOCLOSE))));

            Log.d("Device Added: ", data.getString(data.getColumnIndex(COLUMN_DEVICEID)));
            Log.d("Device Location: ", data.getString(data.getColumnIndex(COLUMN_HOUSELOCATION)));
            Log.d("Device Name: ", data.getString(data.getColumnIndex(COLUMN_DEVICENAME)));

            DevicesRecyclerViewAdapter adapter = new DevicesRecyclerViewAdapter(getContext(), devices);
            rvDevices.setAdapter(adapter);
        }


        //data.getString(data.getColumnIndex(COLUMN_DEVICEID));
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {



    }

}



