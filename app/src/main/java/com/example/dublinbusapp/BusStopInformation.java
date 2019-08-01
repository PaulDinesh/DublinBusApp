package com.example.dublinbusapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BusStopInformation extends AsyncTask<Void,Void,Void> {
    String data_StopInfo ="";
    String dataParsed_StopInfo = "\n\n\nRoute\t Destination\t duetime \n";
    String singleParsed_StopInfo="";
    String errorcode;
    String errormessage;
    String numberofresults;
    String timestamp;

    String searchterm=MainActivity.search.getText().toString();

    String stopid = null;
    String displaystopid;
    String shortname;
    String shortnamelocalized;
    String fullname;
    String fullnamelocalized;
    String latitude;
    String longitude;
    String lastupdated;
    String name;
    String routes;

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url= new URL("https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid="+searchterm+"&format=json");
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        InputStream inputStream = httpsURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while (line != null) {
            line = bufferedReader.readLine();
            data_StopInfo = data_StopInfo + line;
        }
        JSONObject JA = new JSONObject(data_StopInfo);
            errorcode=(String) JA.getString("errorcode");
            errormessage = JA.getString("errormessage");
            numberofresults = JA.getString("numberofresults");
            timestamp = JA.getString("timestamp");

        JSONArray jr = JA.getJSONArray("results");
        for (int i = 0; i < jr.length(); i++) {
            JSONObject jb1 = jr.getJSONObject(i);
            try {
                stopid = jb1.getString("stopid");
                displaystopid = jb1.getString("displaystopid");

                shortname = jb1.getString("shortname");
                shortnamelocalized = jb1.getString("shortnamelocalized");
                fullname = jb1.getString("fullname");
                fullnamelocalized = jb1.getString("fullnamelocalized");
                latitude = jb1.getString("latitude");
                longitude = jb1.getString("longitude");
                lastupdated = jb1.getString("lastupdated");


                   singleParsed_StopInfo = shortname + "   " +  fullname +"\n";
                dataParsed_StopInfo = dataParsed_StopInfo + singleParsed_StopInfo;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            JSONArray jar = JA.getJSONArray("operators");
            for (int i = 0; i < jar.length(); i++) {
                JSONObject jb1 = jar.getJSONObject(i);
                try {
                    name = jb1.getString("name");
                    routes = jb1.getString("routes");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }       } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.data.setText(this.dataParsed_StopInfo);
    }
}
