package com.example.sebastian.smartplug.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sebastian.smartplug.R;
import com.example.sebastian.smartplug.activities.DeviceDetailsActivity;
import com.example.sebastian.smartplug.models.SmartPlug;
import com.example.sebastian.smartplug.services.RestApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;




import static android.app.PendingIntent.getActivity;

/**
 * Created by sebastian on 5/20/2017.
 */

public class DevicesRecyclerViewAdapter extends RecyclerView.Adapter<DevicesRecyclerViewAdapter.ViewHolder> {

    List<SmartPlug> devices;
    Context context;

    public DevicesRecyclerViewAdapter(Context context, List<SmartPlug> devices){
        this.devices = devices;
        this.context = context;

    }

    private Context getContext(){

        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_devices, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SmartPlug device = devices.get(position);
        holder.tvTitle.setText(device.getDeviceName()+" - "+device.getHouseLocation());
        holder.tvPower.setText("Power Consumption: " + Float.toString(device.getPower()) + "kW");


        if(device.getDeviceStatus()){
            Picasso.with(getContext())
                    .load(R.mipmap.ic_statusgreen)//cargo imagen de conectado
                    .into(holder.ivDeviceStatus);

            holder.ivSwitchButton.setEnabled(true);

            if(device.getSwitchStatus()){
                holder.ivSwitchButton.setChecked(true);
            }else{
                holder.ivSwitchButton.setChecked(false);
            }

        }else{
            Picasso.with(getContext())
                    .load(R.mipmap.ic_statusred)//cargo imagen de desconexion
                    .into(holder.ivDeviceStatus);

            holder.ivSwitchButton.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivDeviceStatus)
        ImageView ivDeviceStatus;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvPower)
        TextView tvPower;
        @BindView(R.id.ivSwitchButton)
        ToggleButton ivSwitchButton;
        @BindView(R.id.cvDevices)
        CardView cvDevices;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


            this.ivSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartPlug device = devices.get(getAdapterPosition());
                    if (isChecked) {
                        // The toggle is enabled
                        //en realidad en vez de cammbiar el color del estado deberia mandar por la restapi abrir o cerrar el switch
                        //if deviceconnected

                        Picasso.with(getContext())
                                .load(R.mipmap.ic_statusgreen)//cargo imagen de appliance
                                .into(ivDeviceStatus);
                        //Switch Cerrado
                        devices.set(getAdapterPosition(),devices.get(getAdapterPosition())).setSwitchStatus(true);


                    } else {
                        // The toggle is disabled
                        Picasso.with(getContext())
                                .load(R.mipmap.ic_statusred)//cargo imagen de appliance
                                .into(ivDeviceStatus);
                        //Switch abierto
                        devices.set(getAdapterPosition(),devices.get(getAdapterPosition())).setSwitchStatus(false);

                    }

                    //TODO hago el llamado a la RESTAPI para setear el HW
                    Intent intent = new Intent(getContext(), RestApiService.class);
                    intent.putExtra("Config","SendConfig");
                    intent.putExtra("DeviceId", device.getId());
                    //startService
                    getContext().startService(intent);
                }
            });

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            SmartPlug device = devices.get(getAdapterPosition());

            Intent intent = new Intent(getContext(), DeviceDetailsActivity.class);
            intent.putExtra("DEVICE", device);
            getContext().startActivity(intent);

        }
    }
}
