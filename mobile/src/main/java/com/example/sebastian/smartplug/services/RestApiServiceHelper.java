package com.example.sebastian.smartplug.services;

import android.util.Log;

import com.example.sebastian.smartplug.Processor.RestApiProcessor;
import com.example.sebastian.smartplug.models.SmartPlug;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection; //descomentar para usar HTTPURLConnection
import java.net.MalformedURLException;
import java.net.URL; //descomentar para usar HTTPURLConnection
import java.io.OutputStream;//descomentar para usar HTTPURLConnection
import java.io.OutputStreamWriter;//descomentar para usar HTTPURLConnection
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastian on 5/27/2017.
 */

public class RestApiServiceHelper {
    private static final String LOG = RestApiServiceHelper.class.getName();
    private static final String destination = null;
    private static final String BASE = "https://esp8266server-unstraitened-desalination.mybluemix.net/api/";

    public List<SmartPlug> GetDevices(String route){
        JSONObject json = null;
        RestApiProcessor processor = new RestApiProcessor();
        URL url = null;
        try {
            url = new URL(BASE + route);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        json = GET(url);
        Log.d("Pidiendo ", "Devices");
        return processor.toSmartPlugList(json);//toSmartPlug devuelve una lista de SmartPlug objects o un solo elemento dependiendo lo q se pida
    }

    public SmartPlug GetDeviceData(String route){//para cuando se elige un dispositivo en particular
        RestApiProcessor processor = new RestApiProcessor();
        JSONObject json = null;
        URL url = null;
        try {
            url = new URL(BASE + route);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        json = GET(url);
        return processor.toSmartPlug(json);//toSmartPlug devuelve una lista de SmartPlug objects o un solo elemento dependiendo lo q se pida
    }

    public void SendConfig(String route, String payload){
        //JSONObject json = new JSONObject(payload);

        URL url = null;
        try {
            url = new URL(BASE + route);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String response = PUT(url, payload);

        //TODO tratar la respuesta para ver si pudo enviar el comando o si se desconecto el dispositivo
        return;
    }



    public JSONObject GET(URL url){
        JSONObject json = null;



        try {


            Log.d(LOG, url.toString());
            //setting up http connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            // optional default is GET
            conn.setRequestMethod("GET");

            // Set Headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "application/json");
            Log.d(LOG, conn.getRequestProperty("accept"));
            // Request not successful
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Request Failed. HTTP Error Code: " + conn.getResponseCode());
            }
            InputStream instream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(instream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line);
            }
            conn.disconnect();
            Log.d(LOG, total.toString());
            json = new JSONObject(total.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }


    public String PUT(URL url, String params){
        JSONObject json = null;



        try {

            Log.d(LOG, url.toString());
            //setting up http connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);//10 seg
            conn.setConnectTimeout(15000);//15 seg
            // optional default is GET
            conn.setRequestMethod("PUT");

            // Set Headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "application/json");
            Log.d(LOG, conn.getRequestProperty("accept"));
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(params);
            writer.flush();
            writer.close();
            os.close();

            //Request not successful
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Request Failed. HTTP Error Code: " + conn.getResponseCode());
            }
            InputStream instream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(instream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line);
            }
            conn.disconnect();
            Log.d(LOG, total.toString());
            //json = new JSONObject(total.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
