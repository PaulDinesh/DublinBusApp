package com.example.dublinbusapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.in;

public class BusStopInformation extends AsyncTask<Void, Void, Void> {
    String data_StopInfo ="";
    String[] dataParsed_StopInfo={"",""};
    String[] singleParsed_StopInfo={"",""};
    String errorcode;
    String errormessage;
    String numberofresults;
    String timestamp;
    String[] stopnameArray={"",""};
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
    String[] route_no={"",""};


    @Override
    protected Void doInBackground(Void... voids) {

        stopnameArray[0] = MainActivity.startstopname.getText().toString();
        stopnameArray[1] = MainActivity.endstopname.getText().toString();

        for (int i = 0; i < stopnameArray.length; i++) {
            try {
                URL url = new URL("https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid=&stopname=" + stopnameArray[i] + "&format=json");
//            https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid=&stopname=Jamestown%20Rd&format=json
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data_StopInfo = data_StopInfo + line;
                }
                JSONObject JA = new JSONObject(data_StopInfo);
                errorcode = (String) JA.getString("errorcode");
                errormessage = JA.getString("errormessage");
                numberofresults = JA.getString("numberofresults");
                timestamp = JA.getString("timestamp");

                JSONArray jr = JA.getJSONArray("results");
                for (int j = 0; j < jr.length(); j++) {
                    JSONObject jb1 = jr.getJSONObject(j);
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


//                        singleParsed_StopInfo[i] = shortname + "   " + fullname + "\n";
//                        dataParsed_StopInfo[i] = dataParsed_StopInfo[i] + singleParsed_StopInfo[i];

                        String operators =jb1.get("operators").toString();
                        JSONArray opt=new JSONArray(operators);

                        for (int k = 0; k < opt.length(); k++) {
                            JSONObject job = opt.getJSONObject(k);
                            try {
                                name = job.getString("name");

                                String routes =jb1.get("routes").toString();
                                JSONArray rt = new JSONArray(routes);

                                for (int l = 0; l < rt.length(); l++) {
                                    JSONObject jb = rt.getJSONObject(k);
                                    try {
                                        route_no[l] = job.getString("routes");
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();


                                    if (routes == routes1)
                                        singleParsed_StopInfo[i] = routes + "\n";
                                }
                                    dataParsed_StopInfo[i] = dataParsed_StopInfo[i] + singleParsed_StopInfo[i];
//                                    System.out.println("...."+dataParsed_StopInfo[i]);

                            }} catch (Exception e) {
                                e.printStackTrace();
                            }


                    }}
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            return null;
        }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Activity2.data.setText(this.dataParsed_StopInfo[0]);
        Activity2.Endstopdata.setText(this.dataParsed_StopInfo[1]);
    }
}
