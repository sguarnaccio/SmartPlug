package com.example.sebastian.smartplug.services;

import android.util.Log;

import com.example.sebastian.smartplug.Processor.RestApiProcessor;
import com.example.sebastian.smartplug.models.SmartPlug;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.net.HttpURLConnection; //descomentar para usar HTTPURLConnection
//import java.net.URL; //descomentar para usar HTTPURLConnection
//import java.io.OutputStream;//descomentar para usar HTTPURLConnection
//import java.io.OutputStreamWriter;//descomentar para usar HTTPURLConnection
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
        //URL url = new URL(BASE + route);
        String url = BASE + route;
        json = GET(url);
        Log.d("Pidiendo ", "Devices");
        return processor.toSmartPlugList(json);//toSmartPlug devuelve una lista de SmartPlug objects o un solo elemento dependiendo lo q se pida
    }

    public SmartPlug GetDeviceData(String route){//para cuando se elige un dispositivo en particular
        RestApiProcessor processor = new RestApiProcessor();
        JSONObject json = null;
        //URL url = new URL(BASE + route);
        String url = BASE + route;
        json = GET(url);
        return processor.toSmartPlug(json);//toSmartPlug devuelve una lista de SmartPlug objects o un solo elemento dependiendo lo q se pida
    }

    public void SendConfig(String route, String payload){
        //JSONObject json = new JSONObject(payload);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("firstParam", payload));
        //URL url = new URL(BASE + route);
        String url = BASE + route;
        //String response = PUT(url, payload);
        String response = PUT(url,params);
        //TODO tratar la respuesta para ver si pudo enviar el comando o si se desconecto el dispositivo
        return;
    }



    private JSONObject GET(String url) {
    //public JSONObject request(URL url){
        JSONObject json = null;
        //TODO MIGRAR libreris apache A HTTPURLConnection
        HttpClient httpClient = new DefaultHttpClient();
        //HttpPost httpPost = new HttpPost(url);
        HttpGet httpGet = new HttpGet(url);


        try {

            Log.d(LOG, url);
            /*Log.d(LOG, url.toString());
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
            json = new JSONObject(total.toString());*/

            httpGet.setHeader("Content-Type", "application/json");
            //httpPost.setHeader("Content-Type", "application/json");
            HttpResponse response = httpClient.execute(httpGet);
            //HttpResponse response = httpClient.execute(httpPost);

            Log.d(LOG, response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(instream));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    total.append(line);
                }
                Log.d(LOG, total.toString());
                json = new JSONObject(total.toString());
            }

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

    /*public String requestHtml(String url, List<NameValuePair> params) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            UrlEncodedFormEntity query = new UrlEncodedFormEntity(params);
            httpPost.setEntity(query);

            HttpResponse response = httpClient.execute(httpPost);


            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(instream));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    total.append(line);
                }
                Log.d(LOG, total.toString());
                return total.toString();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    private String PUT(String url, List<NameValuePair> params) {
    //public String PUT(URL url, String params){
        //JSONObject json = null;
        HttpClient httpClient = new DefaultHttpClient();

        HttpPut httpPut = new HttpPut(url);
        //TODO MIGRAR libreris apache A HTTPURLConnection

        try {

            /*Log.d(LOG, url.toString());
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
            json = new JSONObject(total.toString());*/


            httpPut.setHeader("Content-Type", "application/json");
            httpPut.setHeader("Accept", "application/json");
            UrlEncodedFormEntity query = new UrlEncodedFormEntity(params);
            httpPut.setEntity(query);

            HttpResponse response = httpClient.execute(httpPut);


            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(instream));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    total.append(line);
                }
                Log.d(LOG, total.toString());
                //json = new JSONObject(total.toString());
                return total.toString();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
