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

public class BusStopInfo {
    String data_StopInfo ="";
    String dataParsed_StopInfo="";
    String singleParsed_StopInfo="";
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
    String routes;
    ArrayList<StopInformation> route_start= new ArrayList<>();
    HashSet<StopInformation> set = new HashSet<>();
    ArrayList<StopInformation> unique_start = new ArrayList<>();
    String[] end_route={"",""};
    String[] start_route={"",""};
    ArrayList<StopInformation> start_Array;
    ArrayList<StopInformation> end_Array;
    public static TextView data;
    public static TextView Endstopdata;

    //
    String data_StopInfo1 ="";
    String dataParsed_StopInfo1="";
    String singleParsed_StopInfo1="";
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
    ArrayList<StopInformation> route_end1= new ArrayList<>();
    ArrayList<StopInformation> unique_end1 = new ArrayList<>();
    HashSet<StopInformation> set2 = new HashSet<>();
    String[] end_route1={"",""};
    String[] start_route1={"",""};
    ArrayList<StopInformation> start_Array1;
    ArrayList<StopInformation> end_Array1;
//    public static TextView data;
//    public static TextView Endstopdata;


    public  void getBusStopInfo(final MainActivity.readCallback rCallback) {
//        BusStopInfo busArray;
        new Thread( new Runnable() {
        @Override
        public void run() {
            final StringBuilder builder = new StringBuilder();
            try {
                start_method();
                end_method();
                rCallback.onCallback(unique_start,unique_end1);
            }
                catch (Exception e) {
                e.printStackTrace();
        }
    }
}).start();
    }
    private void start_method() {
        start_Array=null;
        end_Array=null;
//        System.out.println(end_Array);

            stopnameArray[0] = MainActivity.startstopname.getText().toString();
//        System.out.println("outside forloop "+stopnameArray[0]+"..."+stopnameArray[1]);
                try {
                    URL url = new URL("https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid=&stopname=" + stopnameArray[0] + "&format=json");
//            https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid=&stopname=Jamestown%20Rd&format=json
//                System.out.println("inside for loop "+i+" "+stopnameArray[i]);
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    InputStream inputStream = httpsURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    data_StopInfo = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        data_StopInfo = data_StopInfo + line;
                    }
                    System.out.println("."+0+"..DataStopInfo"+data_StopInfo);
                    JSONObject JA = new JSONObject(data_StopInfo);
                    errorcode = (String) JA.getString("errorcode");
                    errormessage = JA.getString("errormessage");
                    numberofresults = JA.getString("numberofresults");
                    timestamp = JA.getString("timestamp");

                    JSONArray jr = JA.getJSONArray("results");
//                System.out.println("paul"+jr);
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
                            String operators = jb1.get("operators").toString();
                            JSONArray opt = new JSONArray(operators);

                            StopInformation sobj = new StopInformation();
                            sobj.setstopid(stopid);
                            System.out.println("object stop "+sobj.getstopid().toString());
                            sobj.setshortname(shortname);
                            System.out.println("object short"+sobj.getshortname());
                            sobj.setfullname(fullname);
                            System.out.println("object full"+sobj.getfullname());

                            for (int k = 0; k < opt.length(); k++) {
                                JSONObject job = opt.getJSONObject(k);
                                try {
                                    name = job.getString("name");

                                    routes = (String) job.getString("routes");
                                    System.out.println(".."+"routes "+routes);
                                    JSONArray rt = new JSONArray(routes);
                                    for (int l = 0; l < rt.length(); l++) {
                                        try {route_start.add(sobj);
//                                            System.out.println("...." +i +" " + dataParsed_StopInfo[i]);
                                            } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    System.out.println("CallBack class Route--------------"+route_start);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    for (int count=0;count<route_start.size();count++) {
                            if (!set.contains(route_start)) {
                                unique_start.add(route_start.get(count));
                                set.add(route_start.get(count));
                            }
//To be included if added in TextView
//                        start_Array = unique_start.toArray(new String[unique_start.size()]);
//                        for (String str : start_Array) {
//                            singleParsed_StopInfo = str + "\n";
//                            dataParsed_StopInfo = dataParsed_StopInfo + singleParsed_StopInfo;
//                        }
                        for(int x=0;x<unique_start.size();x++){System.out.println("Object Unique Start"+unique_start.get(x).toString());}
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//            for(int x=0;x<start_Array.length;x++){System.out.println("Inside class Route Start"+start_Array[x]);}
        }

    private void end_method() {
        start_Array1=null;
        end_Array1=null;
        System.out.println(end_Array1);
        {
            stopnameArray[1] = MainActivity.endstopname.getText().toString();
//        System.out.println("outside forloop "+stopnameArray[0]+"..."+stopnameArray[1]);
             try {
                    URL url = new URL("https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid=&stopname=" + stopnameArray[1] + "&format=json");
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
                    System.out.println("."+1+"..DataStopInfo"+data_StopInfo1);
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

                            StopInformation sobj1 = new StopInformation();
                            sobj1.setstopid(stopid);
                            sobj1.setshortname(shortname);
                            sobj1.setfullname(fullname);

                            for (int k = 0; k < opt1.length(); k++) {
                                JSONObject job1 = opt1.getJSONObject(k);
                                try {
                                    name1 = job1.getString("name");

                                    routes1 = (String) job1.getString("routes");
                                    System.out.println(".."+1+"routes "+routes1);
                                    JSONArray rt = new JSONArray(routes1);
                                    for (int l = 0; l < rt.length(); l++) {
                                        try {//                                        route_start.add(rt.getString(l));
//                                            System.out.println("...." +i +" " + dataParsed_StopInfo[i]);
                                                 route_end1.add(sobj1);
//                                                route_end.add(rt.getString(l));
//                                                System.out.println("Route Start"+route_end);
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

                 for (int count=0;count<route_end1.size();count++) {

                            if (!set2.contains(route_end1)) {
                                unique_end1.add(route_end1.get(count));
                                set2.add(route_end1.get(count));
                            }
                        }
//To be included if added in TextView
                 //                        end_Array1 = unique_end1.toArray(new String[unique_end1.size()]);
//                        for (String str : end_Array1) {
//
//                            singleParsed_StopInfo1 = str + "\n";
//                            dataParsed_StopInfo1 = dataParsed_StopInfo1 + singleParsed_StopInfo1;
//                        }
//                        for(int x=0;x<end_Array1.length;x++){System.out.println("CallBack class Route End"+end_Array1[x]);}
             } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//            for(int x=0;x<end_Array1.length;x++){System.out.println("Inside class Route End"+end_Array1[x]);}
    }
}