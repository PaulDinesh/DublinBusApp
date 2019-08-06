package com.example.dublinbusapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    ArrayList<String> route_start= new ArrayList<String>();
    ArrayList<String> route_end= new ArrayList<String>();
    HashSet<String> set = new HashSet<>();
    ArrayList<String> unique_start = new ArrayList<>();
    ArrayList<String> unique_end = new ArrayList<>();
    String[] end_route={"",""};
    String[] start_route={"",""};
    public String[] start_Array;
    public String[] end_Array;
    public static TextView data;
    public static TextView Endstopdata;

    @Override
    protected Void doInBackground(Void... voids) {

        stopnameArray[0] = MainActivity.startstopname.getText().toString();
        stopnameArray[1] = MainActivity.endstopname.getText().toString();
//        System.out.println("outside forloop "+stopnameArray[0]+"..."+stopnameArray[1]);

        for (int i = 0; i < stopnameArray.length; i++) {
            try {
                URL url = new URL("https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid=&stopname=" + stopnameArray[i] + "&format=json");
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

                        for (int k = 0; k < opt.length(); k++) {
                            JSONObject job = opt.getJSONObject(k);
                            try {
                                name = job.getString("name");

                                String routes = (String) job.getString("routes");
                                JSONArray rt = new JSONArray(routes);
                                for (int l = 0; l < rt.length(); l++) {
                                    try {
                                        if (i == 0) {
                                            route_start.add(stopid);

//                                        route_start.add(rt.getString(l));
//                                            System.out.println("...." +i +" " + dataParsed_StopInfo[i]);
                                        } else {
                                            route_end.add(stopid);
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

                if (i == 0) {
                    for (String item : route_start) {
                        if (!set.contains(item)) {
                            unique_start.add(item);
                            set.add(item);
                        }
                    }
                    start_Array = unique_start.toArray(new String[unique_start.size()]);
                    MainActivity.Main_Start_Array = start_Array;
                    for (String str : start_Array) {
                        singleParsed_StopInfo[i] = str + "\n";
                        dataParsed_StopInfo[i] = dataParsed_StopInfo[i] + singleParsed_StopInfo[i];
                    }

                } else {
                    for (String item : route_end) {
                        if (!set.contains(item)) {
                            unique_end.add(item);
                            set.add(item);
                        }
                    }
                    end_Array = unique_end.toArray(new String[unique_end.size()]);
                    MainActivity.Main_End_Array = end_Array;
                    for (String str : end_Array) {
                        /*SpannableString spanString = new SpannableString(str);
                        Matcher matcher = Pattern.compile("@([A-Za-z0-9_-]+)").matcher(spanString);

                        while (matcher.find())
                        {
                            spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#0000FF")), matcher.start(), matcher.end(), 0); //to highlight word havgin '@'
                            final String tag = matcher.group(0);
                            ClickableSpan clickableSpan = new ClickableSpan() {
                                @Override
                                public void onClick(View textView) {
                                    Log.e("click", "click " + tag);
                                    String searchText=tag.replace("@",""); //replace '@' with blank character to search on google.
//                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in/search?q=" + searchText));
//                                    startActivity(browserIntent);
                                }
                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);

                                }
                            };
                            spanString.setSpan(clickableSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }

                        txtData.setText(spanString);
                        txtData.setMovementMethod(LinkMovementMethod.getInstance());
                    }*/
                        singleParsed_StopInfo[i] = str + "\n";
                        dataParsed_StopInfo[i] = dataParsed_StopInfo[i] + singleParsed_StopInfo[i];
                    }
                }
//                System.out.println("Route Start"+route_start);
//                System.out.println("Route End"+route_end);
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
        return null;
    }
        @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Activity2.data.setText(this.dataParsed_StopInfo[0]);
        Activity2.Endstopdata.setText(this.dataParsed_StopInfo[1]);

//        Activity2.start_listView.setAdapter(this,android.R.layout.simple_list_item_1,this.start_Array);
//        Activity2.start_listView.setText(this.end_Array);
    }
}
