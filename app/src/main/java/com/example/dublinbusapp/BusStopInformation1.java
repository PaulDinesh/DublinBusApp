package com.example.dublinbusapp;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import javax.net.ssl.HttpsURLConnection;

public class BusStopInformation1 {
    String data_StopInfo1 ="";
    String[] dataParsed_StopInfo1={"",""};
    String[] singleParsed_StopInfo1={"",""};
    String errorcode1;
    String errormessage1;
    String numberofresults1;
    String timestamp1;
    String[] stopnameArray1={"",""};
    String stopid1 = null;
    String displaystopid1;
    String shortname1;
    String shortnamelocalized1;
    String fullname1;
    String fullnamelocalized1;
    String latitude1;
    String longitude1;
    String lastupdated1;
    String name1;
    String routes1=null;
    ArrayList<String> route_start1= new ArrayList<String>();
    ArrayList<String> route_end1= new ArrayList<String>();

    ArrayList<String> unique_start1 = new ArrayList<>();
    ArrayList<String> unique_end1 = new ArrayList<>();
    String[] end_route={"",""};
    String[] start_route={"",""};
    public String[] start_Array1;
    public String[] end_Array1;
    public static TextView data;
    public static TextView Endstopdata;

    public  void getBusStopInfo(final MainActivity.readCallback rCallback) {
        BusStopInformation1 busArray;
        new Thread( new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                try {
                    start_Array1=null;
                    end_Array1=null;
                    System.out.println(end_Array1);
                    {
                        stopnameArray1[0] = MainActivity.startstopname.getText().toString();
                        stopnameArray1[1] = MainActivity.endstopname.getText().toString();
//        System.out.println("outside forloop "+stopnameArray[0]+"..."+stopnameArray[1]);

                        for (int i = 0; i < stopnameArray1.length; i++) {
                            try {
                                URL url = new URL("https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid=&stopname=" + stopnameArray1[i] + "&format=json");
//            https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid=&stopname=Jamestown%20Rd&format=json
//                System.out.println("inside for loop "+i+" "+stopnameArray[i]);
                                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                                InputStream inputStream = httpsURLConnection.getInputStream();
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                String line1 = "";
                                data_StopInfo1 = "";
                                while (line1 != null) {
                                    line1 = bufferedReader.readLine();
                                    data_StopInfo1 = data_StopInfo1 + line1;
                                }
                                System.out.println("."+i+"..DataStopInfo"+data_StopInfo1);
                                JSONObject JA1 = new JSONObject(data_StopInfo1);
                                errorcode1 = (String) JA1.getString("errorcode");
                                errormessage1 = JA1.getString("errormessage");
                                numberofresults1 = JA1.getString("numberofresults");
                                timestamp1 = JA1.getString("timestamp");

                                JSONArray jr1 = JA1.getJSONArray("results");
//                System.out.println("paul"+jr);
                                for (int j = 0; j < jr1.length(); j++) {
                                    JSONObject jb11 = jr1.getJSONObject(j);
                                    try {
                                        stopid1 = jb11.getString("stopid");
                                        displaystopid1 = jb11.getString("displaystopid");
                                        shortname1 = jb11.getString("shortname");
                                        shortnamelocalized1 = jb11.getString("shortnamelocalized");
                                        fullname1 = jb11.getString("fullname");
                                        fullnamelocalized1 = jb11.getString("fullnamelocalized");
                                        latitude1 = jb11.getString("latitude");
                                        longitude1 = jb11.getString("longitude");
                                        lastupdated1 = jb11.getString("lastupdated");
                                        String operators1 = jb11.get("operators").toString();
                                        JSONArray opt1 = new JSONArray(operators1);

                                        for (int k = 0; k < opt1.length(); k++) {
                                            JSONObject job1 = opt1.getJSONObject(k);
                                            try {
                                                name1 = job1.getString("name");

                                                routes1 = (String) job1.getString("routes");
                                                System.out.println(".."+i+"routes "+routes1);
                                                JSONArray rt = new JSONArray(routes1);
                                                for (int l = 0; l < rt.length(); l++) {
                                                    try {
                                                        if (i == 0) {
                                                            route_start1.add(stopid1);

//                                        route_start.add(rt.getString(l));
//                                            System.out.println("...." +i +" " + dataParsed_StopInfo[i]);
                                                        } else {
                                                            route_end1.add(stopid1);
//                                                route_end.add(rt.getString(l));
//                                                System.out.println("Route Start"+route_end);
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                HashSet<String> set1 = new HashSet<>();
                                if (i == 0) {
                                    for (String item : route_start1) {
                                        if (!set1.contains(item)) {
                                            unique_start1.add(item);
                                            set1.add(item);
                                        }
                                    }
                                    start_Array1 = unique_start1.toArray(new String[unique_start1.size()]);
                                    for (String str : start_Array1) {
                                        singleParsed_StopInfo1[i] = str + "\n";
                                        dataParsed_StopInfo1[i] = dataParsed_StopInfo1[i] + singleParsed_StopInfo1[i];
                                    }

                                } else {
                                    for (String item : route_end1) {
                                        if (!set1.contains(item)) {
                                            unique_end1.add(item);
                                            set1.add(item);
                                        }
                                    }
                                    end_Array1 = unique_end1.toArray(new String[unique_end1.size()]);


                                    for (String str : end_Array1) {

                                        singleParsed_StopInfo1[i] = str + "\n";
                                        dataParsed_StopInfo1[i] = dataParsed_StopInfo1[i] + singleParsed_StopInfo1[i];
                                    }
                                    for(int x=0;x<start_Array1.length;x++){System.out.println("CallBack class Route Start"+start_Array1[x]);}
                                    for(int x=0;x<end_Array1.length;x++){System.out.println("CallBack class Route End"+end_Array1[x]);}
//                                    rCallback.onCallback(start_Array1,end_Array1);

                                }


                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//            if (start_route == end_route)
//              start_route.retainAll(end_route);
//                singleParsed_StopInfo[i] = end_route + "\n";
//            dataParsed_StopInfo[i] = dataParsed_StopInfo[i] + singleParsed_StopInfo[i];
//            System.out.println("...."+dataParsed_StopInfo[i]);
                        }
                        for(int x=0;x<start_Array1.length;x++){System.out.println("Inside class Route Start"+start_Array1[x]);}
                        for(int x=0;x<end_Array1.length;x++){System.out.println("Inside class Route End"+end_Array1[x]);}


                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
}

