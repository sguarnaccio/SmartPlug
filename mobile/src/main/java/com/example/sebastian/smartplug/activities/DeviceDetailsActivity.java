package com.example.sebastian.smartplug.activities;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sebastian.smartplug.R;
import com.example.sebastian.smartplug.models.SmartPlug;
import com.example.sebastian.smartplug.services.RestApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class DeviceDetailsActivity extends AppCompatActivity {

    SmartPlug device;
    EditText ssid;
    EditText password;
    EditText deviceName;
    EditText deviceLocation;
    EditText powerThreshold;
    CheckBox sendNotifications;
    EditText timeToOPen;
    EditText timeToClose;
    CheckBox    timerSetUp;

    int hour_toOpen;
    int min_toOpen;
    int hour_toClose;
    int min_toClose;

    private Pattern pattern;
    private Matcher matcher;

    private static final String TIME24HOURS_PATTERN =
            "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.ibDeviceSettings)
    ImageButton ibDeviceSettings;
    @BindView(R.id.ibSwitch)
    ImageButton ibSwitch;
    @BindView(R.id.ibStatistics)
    ImageView ibStatistics;
    @BindView(R.id.ibTimer)
    ImageView ibTimer;
    @BindView(R.id.statusindicator)
    ImageView statusindicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            device = (SmartPlug) extras.getSerializable("DEVICE");

            //TODO REFRESH DATA WITH THE SERVER AND IMPLEMENT A BROADCAST RECEIVER

            /*Intent intent = new Intent(this, RestApiService.class);
            intent.putExtra("Config","GetDeviceData");
            intent.putExtra("DeviceId", device.getId());
            //startService
            this.startService(intent);*/

            this.setTitle(device.getDeviceName() + " - " + device.getHouseLocation());
            if(device.getSwitchStatus()){
                tvStatus.setText(getString(R.string.status_text, device.getPower(), device.getVoltage(), device.getCurrent(), device.getCosphi(), "On"));
            }
            else{
                tvStatus.setText(getString(R.string.status_text, device.getPower(), device.getVoltage(), device.getCurrent(), device.getCosphi(), "Off"));
            }
        }
        final RelativeLayout relativeLayoutList = (RelativeLayout) findViewById(R.id.rlSettings);

        this.ibDeviceSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cleanLayout(relativeLayoutList);
                addDeviceSettingsTextView(relativeLayoutList);
                addDeviceSettingsButtons(relativeLayoutList);


            }
        });

        this.ibSwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cleanLayout(relativeLayoutList);
                if(device.getSwitchStatus()){
                    device.setSwitchStatus(false);
                }
                else{
                    device.setSwitchStatus(true);
                }
                //TODO LLAMAR A LA RESTAPI PARA MANDAR LA CONFIGURACION (CAMBIAR EL ESTADO DEL SWITCH)
                //TODO hago el llamado a la RESTAPI para setear el HW
                Intent intent = new Intent(getApplicationContext(), RestApiService.class);
                intent.putExtra("Config","SendConfig");
                intent.putExtra("DeviceId", device.getId());
                //startService
                getApplicationContext().startService(intent);


            }
        });

        this.ibStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cleanLayout(relativeLayoutList);
                addStatisticsTextView(relativeLayoutList);
            }
        });

        this.ibTimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cleanLayout(relativeLayoutList);
                addTimerSettingsTextView(relativeLayoutList);
                addTimerSettingsButtons(relativeLayoutList);


            }
        });



        if(device.getDeviceStatus()){
            this.ibDeviceSettings.setEnabled(true);
            this.ibSwitch.setEnabled(true);
            this.ibStatistics.setEnabled(true);
            this.ibTimer.setEnabled(true);
            this.statusindicator.setImageResource(R.mipmap.ic_statusgreen);

        }
        else{
            this.ibSwitch.setEnabled(false);
            this.ibStatistics.setEnabled(false);
            this.ibTimer.setEnabled(false);
            this.statusindicator.setImageResource(R.mipmap.ic_statusred);
        }
    }


    private void cleanLayout(RelativeLayout relativeLayoutList) {
        if(relativeLayoutList.getChildCount() > 0)
            relativeLayoutList.removeAllViews();
    }

    private void addDeviceSettingsTextView(RelativeLayout relativeLayoutList) {
        TextView    textView_ssid = new TextView(this);
        textView_ssid.setText(R.string.device_settings_ssid);
        textView_ssid.setId(R.id.ssid_text);
        EditText editText_ssid = new EditText(this);
        editText_ssid.setId(R.id.ssid_edit);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(60,60,0,0);
        textView_ssid.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.END_OF,R.id.ssid_text);
        params.addRule(RelativeLayout.ALIGN_BASELINE,R.id.ssid_text);

        editText_ssid.setWidth(800);
        editText_ssid.setText(device.getSsidNetwork());
        editText_ssid.setLayoutParams(params);

        editText_ssid.setMaxLines(1);
        editText_ssid.setSingleLine();//necesario para q funcione el ACTION_NEXT y pase directamente al siguiente campo
        editText_ssid.setImeOptions(EditorInfo.IME_ACTION_NEXT);


        TextView    textView_password = new TextView(this);
        textView_password.setText(R.string.device_settings_password);
        textView_password.setId(R.id.password_text);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,R.id.ssid_text);
        params.setMargins(60,60,0,0);
        textView_password.setLayoutParams(params);

        EditText editText_password = new EditText(this);
        editText_password.setId(R.id.password_edit);
        editText_password.setHint("Password");
        editText_password.setWidth(700);
        editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.END_OF,R.id.password_text);
        params.addRule(RelativeLayout.ALIGN_BASELINE,R.id.password_text);
        editText_password.setLayoutParams(params);

        editText_password.setMaxLines(1);
        editText_password.setSingleLine();//necesario para q funcione el ACTION_NEXT y pase directamente al siguiente campo
        editText_password.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        TextView    textView_devicename = new TextView(this);
        textView_devicename.setText(R.string.device_settings_devicename);
        textView_devicename.setId(R.id.deviename_text);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,R.id.password_text);
        params.setMargins(60,60,0,0);
        textView_devicename.setLayoutParams(params);

        EditText editText_devicename = new EditText(this);
        editText_devicename.setId(R.id.devicename_edit);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.END_OF,R.id.deviename_text);
        params.addRule(RelativeLayout.ALIGN_BASELINE,R.id.deviename_text);

        editText_devicename.setWidth(600);
        editText_devicename.setText(device.getDeviceName());
        editText_devicename.setLayoutParams(params);

        editText_devicename.setMaxLines(1);
        editText_devicename.setSingleLine();//necesario para q funcione el ACTION_NEXT y pase directamente al siguiente campo
        editText_devicename.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        TextView    textView_devicelocation = new TextView(this);
        textView_devicelocation.setText(R.string.device_settings_devicehouselocation);
        textView_devicelocation.setId(R.id.devicelocation_text);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,R.id.deviename_text);
        params.setMargins(60,60,0,0);
        textView_devicelocation.setLayoutParams(params);

        EditText editText_devicelocation = new EditText(this);
        editText_devicelocation.setId(R.id.devicelocation_edit);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.END_OF,R.id.devicelocation_text);
        params.addRule(RelativeLayout.ALIGN_BASELINE,R.id.devicelocation_text);

        editText_devicelocation.setWidth(550);
        editText_devicelocation.setText(device.getHouseLocation());
        editText_devicelocation.setLayoutParams(params);

        editText_devicelocation.setMaxLines(1);
        editText_devicelocation.setSingleLine();//necesario para q funcione el ACTION_NEXT y pase directamente al siguiente campo
        editText_devicelocation.setImeOptions(EditorInfo.IME_ACTION_NEXT);



        TextView    textView_powerThreshold = new TextView(this);
        textView_powerThreshold.setText(R.string.device_settings_powerthreshold);
        textView_powerThreshold.setId(R.id.powerThreshold_text);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,R.id.devicelocation_text);
        params.setMargins(60,60,0,0);
        textView_powerThreshold.setLayoutParams(params);

        EditText editText_powerThreshold = new EditText(this);
        editText_powerThreshold.setId(R.id.editText_powerThreshold_edit);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.END_OF,R.id.powerThreshold_text);
        params.addRule(RelativeLayout.ALIGN_BASELINE,R.id.powerThreshold_text);

        editText_powerThreshold.setWidth(550);
        editText_powerThreshold.setText(Float.toString(device.getPowerThreshold()));
        editText_powerThreshold.setLayoutParams(params);

        editText_powerThreshold.setMaxLines(1);
        editText_powerThreshold.setSingleLine();//necesario para q funcione el ACTION_NEXT y pase directamente al siguiente campo
        editText_powerThreshold.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        CheckBox checkBox_SendNotifications = new CheckBox(this);
        checkBox_SendNotifications.setText(R.string.device_settings_sendnotifications);
        checkBox_SendNotifications.setId(R.id.sendNotifications_checkBox);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,R.id.powerThreshold_text);
        params.setMargins(60,60,0,0);
        checkBox_SendNotifications.setLayoutParams(params);
        checkBox_SendNotifications.setImeOptions(EditorInfo.IME_ACTION_DONE);


        relativeLayoutList.addView(textView_ssid);
        relativeLayoutList.addView(editText_ssid);
        relativeLayoutList.addView(textView_password);
        relativeLayoutList.addView(editText_password);
        relativeLayoutList.addView(textView_devicename);
        relativeLayoutList.addView(editText_devicename);
        relativeLayoutList.addView(textView_devicelocation);
        relativeLayoutList.addView(editText_devicelocation);
        relativeLayoutList.addView(textView_powerThreshold);
        relativeLayoutList.addView(editText_powerThreshold);
        relativeLayoutList.addView(checkBox_SendNotifications);

        if(device.getDeviceStatus()){
            editText_ssid.setEnabled(true);
            editText_password.setEnabled(true);
            editText_devicename.setEnabled(true);
            editText_devicelocation.setEnabled(true);
            editText_powerThreshold.setEnabled(true);
            checkBox_SendNotifications.setEnabled(true);
        }
        else{
            editText_ssid.setEnabled(false);
            editText_password.setEnabled(false);
            editText_devicename.setEnabled(false);
            editText_devicelocation.setEnabled(false);
            editText_powerThreshold.setEnabled(false);
            checkBox_SendNotifications.setEnabled(false);
        }

        this.ssid = editText_ssid;
        this.password = editText_password;
        this.deviceName = editText_devicename;
        this.deviceLocation = editText_devicelocation;
        this.powerThreshold = editText_powerThreshold;
        this.sendNotifications = checkBox_SendNotifications;


    }

    private void addDeviceSettingsButtons(RelativeLayout relativeLayoutList) {


        Button saveButton = new Button(this);
        saveButton.setText(R.string.device_settings_savebutton);
        //saveButton.setBackgroundResource(R.color.colorPrimary);
        saveButton.setId(R.id.saveconfig_button);
        Button deleteDevice = new Button(this);
        deleteDevice.setText(R.string.device_settings_deletedevice);
        deleteDevice.setId(R.id.deletedevice_button);
        //deleteDevice.setBackgroundResource(R.color.colorPrimary);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,R.id.sendNotifications_checkBox);
        params.setMargins(200,60,0,0);
        saveButton.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.END_OF,R.id.saveconfig_button);
        params.addRule(RelativeLayout.ALIGN_BASELINE,R.id.saveconfig_button);
        params.setMargins(40,0,0,0);

        deleteDevice.setLayoutParams(params);

        relativeLayoutList.addView(saveButton);
        relativeLayoutList.addView(deleteDevice);

        if(device.getDeviceStatus()){
            saveButton.setEnabled(true);
        }
        else{
            saveButton.setEnabled(false);
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!((ssid.getText().toString()).equals("")) && !((ssid.getText().toString()).equals(device.getSsidNetwork())))
                {
                    device.setSsidNetwork(ssid.getText().toString());

                }
                if(!((password.getText().toString()).equals("")) && !((password.getText().toString()).equals(device.getPasswordNetwork()))){
                    device.setPasswordNetwork(password.getText().toString());

                }

                if(!((deviceName.getText().toString()).equals("")) && !((deviceName.getText().toString()).equals(device.getDeviceName()))){
                    device.setDeviceName(deviceName.getText().toString());

                }

                if(!((deviceLocation.getText().toString()).equals("")) && !((deviceLocation.getText().toString()).equals(device.getHouseLocation()))){
                    device.setHouseLocation(deviceLocation.getText().toString());

                }

                if(!((powerThreshold.getText().toString()).equals("")) && !((powerThreshold.getText().toString()).equals(Float.toString(device.getPowerThreshold())))){
                    device.setPowerThreshold(Float.valueOf(powerThreshold.getText().toString()));

                }

                if(sendNotifications.isChecked()){
                    device.setSendNotifications(true);
                }
                else{
                    device.setSendNotifications(false);
                }
                //TODO SEND the configuration to the web server to update the device config

            }
        });



    }

    private void addStatisticsTextView(RelativeLayout relativeLayoutList) {
        //TODO BUILD STATISTIC CHART OF POWER CONSUMPTION with a WebView
    }

    private void addTimerSettingsButtons(RelativeLayout relativeLayoutList) {
        Button saveButton = new Button(this);
        saveButton.setText(R.string.device_settings_savebutton);
        //saveButton.setBackgroundResource(R.color.colorPrimary);
        saveButton.setId(R.id.saveconfig_button);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,R.id.closeswitch_text);
        params.setMargins(300,60,0,0);
        saveButton.setLayoutParams(params);


        relativeLayoutList.addView(saveButton);

        if(device.getDeviceStatus()){
            saveButton.setEnabled(true);
            timeToOPen.setEnabled(true);
            timeToClose.setEnabled(true);
        }
        else{
            saveButton.setEnabled(false);
            timeToOPen.setEnabled(false);
            timeToClose.setEnabled(false);
        }

        timerSetUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    timeToOPen.setEnabled(true);
                    timeToClose.setEnabled(true);
                }else{
                    timeToOPen.setEnabled(false);
                    timeToClose.setEnabled(false);
                }
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                pattern = Pattern.compile(TIME24HOURS_PATTERN);
                matcher = pattern.matcher(timeToOPen.getText().toString());
                if(matcher.matches())//si el tiempo ingresada respeta el formato y patron entonces guardo lso datos. matches() devuelve true o false dependiendo si se cumple o no el formato
                {
                    java.text.DateFormat formatter  = new SimpleDateFormat("hh:mm");
                    try {
                        Date date = formatter.parse(timeToOPen.getText().toString());

                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(date);

                        hour_toOpen = c.get(Calendar.HOUR_OF_DAY);
                        min_toOpen = c.get(Calendar.MINUTE);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                matcher = pattern.matcher(timeToClose.getText().toString());
                if(matcher.matches())//si el tiempo ingresada respeta el formato y patron entonces guardo lso datos. matches() devuelve true o false dependiendo si se cumple o no el formato
                {
                    java.text.DateFormat formatter  = new SimpleDateFormat("hh:mm");
                    try {
                        Date date = formatter.parse(timeToClose.getText().toString());

                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(date);

                        hour_toClose = c.get(Calendar.HOUR_OF_DAY);
                        min_toClose = c.get(Calendar.MINUTE);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }




                if(hour_toOpen != device.getHourToOpen() && timerSetUp.isChecked())
                {
                    device.setHourToOpen(hour_toOpen);

                }
                else if(!timerSetUp.isChecked()){
                    device.setHourToOpen(-1);//deshabilito la gestion horaria del switch
                }

                if(min_toOpen != device.getMinToOpen() && timerSetUp.isChecked())
                {
                    device.setMinToOpen(min_toOpen);

                }
                else if(!timerSetUp.isChecked()){
                    device.setMinToOpen(-1);//deshabilito la gestion horaria del switch
                }



                if(hour_toClose != device.getHourToClose() && timerSetUp.isChecked())
                {
                    device.setHourToClose(hour_toClose);

                }
                else if(!timerSetUp.isChecked()){
                    device.setHourToClose(-1);//deshabilito la gestion horaria del switch
                }

                if(min_toClose != device.getMinToClose() && timerSetUp.isChecked())
                {
                    device.setMinToClose(min_toClose);

                }
                else if(!timerSetUp.isChecked()){
                    device.setMinToClose(-1);//deshabilito la gestion horaria del switch
                }





                //TODO SEND the configuration to the web server to update the device sleep and wake time config

            }
        });

    }

    private void addTimerSettingsTextView(RelativeLayout relativeLayoutList) {

        CheckBox checkBox_TimerSetUp = new CheckBox(this);
        checkBox_TimerSetUp.setText(R.string.device_settings_enabletimer);
        checkBox_TimerSetUp.setId(R.id.enableTimer_checkBox);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(60,60,0,0);
        checkBox_TimerSetUp.setLayoutParams(params);
        checkBox_TimerSetUp.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        TextView    textView_openSwitch = new TextView(this);
        textView_openSwitch.setText(R.string.device_settings_openSwitch);
        textView_openSwitch.setId(R.id.openswitch_text);


        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(60,60,0,0);
        params.addRule(RelativeLayout.BELOW,R.id.enableTimer_checkBox);
        textView_openSwitch.setLayoutParams(params);

        EditText editText_openswitch = new EditText(this);
        editText_openswitch.setId(R.id.openswitch_edit);
        editText_openswitch.setHint("hh:mm");
        editText_openswitch.setWidth(200);
        editText_openswitch.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.END_OF,R.id.openswitch_text);
        params.addRule(RelativeLayout.ALIGN_BASELINE,R.id.openswitch_text);
        editText_openswitch.setLayoutParams(params);

        editText_openswitch.setMaxLines(1);
        editText_openswitch.setSingleLine();//necesario para q funcione el ACTION_NEXT y pase directamente al siguiente campo
        editText_openswitch.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        TextView    textView_closeSwitch = new TextView(this);
        textView_closeSwitch.setText(R.string.device_settings_closeSwitch);
        textView_closeSwitch.setId(R.id.closeswitch_text);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,R.id.openswitch_text);
        params.setMargins(60,60,0,0);
        textView_closeSwitch.setLayoutParams(params);

        EditText editText_closeswitch = new EditText(this);
        editText_closeswitch.setId(R.id.closeswitch_edit);
        editText_closeswitch.setHint("hh:mm");
        editText_closeswitch.setWidth(200);
        editText_closeswitch.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.END_OF,R.id.closeswitch_text);
        params.addRule(RelativeLayout.ALIGN_BASELINE,R.id.closeswitch_text);
        editText_closeswitch.setLayoutParams(params);

        editText_closeswitch.setMaxLines(1);
        editText_closeswitch.setSingleLine();//necesario para q funcione el ACTION_NEXT y pase directamente al siguiente campo
        editText_closeswitch.setImeOptions(EditorInfo.IME_ACTION_DONE);



        relativeLayoutList.addView(checkBox_TimerSetUp);
        relativeLayoutList.addView(textView_openSwitch);
        relativeLayoutList.addView(editText_openswitch);
        relativeLayoutList.addView(textView_closeSwitch);
        relativeLayoutList.addView(editText_closeswitch);



        this.timeToOPen = editText_openswitch;
        this.timeToClose = editText_closeswitch;
        this.timerSetUp = checkBox_TimerSetUp;

    }
}
