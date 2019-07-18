package com.example.dublinbusapp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParse = "";
    String singleParse="";
    String stopid = null;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //URL url= new URL("https://data.smartdublin.ie/cgi-bin/rtpi/realtimebusinformation?stopid=7602&format=json");
            //URL url= new URL("http://api.myjson.com/bins/j5f6b");
            URL url = new URL("https://api.myjson.com/bins/96k67");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String jsonStr = sh.makeServiceCall(url);


            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
//            data = String.format("%s%s", data, line);}

            JSONObject JA = new JSONObject(data);
            JSONArray jr = JA.getJSONArray("results");
            System.out.println(jr);
            stopid = (String) JA.getString("stopid");
            String timestamp = JA.getString("timestamp");
            for (int i = 0; i < jr.length(); i++) {
                JSONObject jb1 = jr.getJSONObject(i);

                try {
                    //stopid = jb1.getString("stopid");

                    //  String timestamp = jb1.getString("timestamp");
                    String arrivaldatetime = jb1.getString("arrivaldatetime");
                    String duetime = jb1.getString("duetime");
                    String departuredatetime = jb1.getString("departuredatetime");
                    String departureduetime = jb1.getString("departureduetime");
                    String scheduledarrivaldatetime = jb1.getString("scheduledarrivaldatetime");
                    String scheduleddeparturedatetime = jb1.getString("scheduleddeparturedatetime");
                    String destination = jb1.getString("destination");
                    String destinationlocalized = jb1.getString("destinationlocalized");
                    String origin = jb1.getString("origin");
                    String originlocalized = jb1.getString("originlocalized");
                    String direction = jb1.getString("direction");
                    String operator = jb1.getString("operator");
                    String additionalinformation = jb1.getString("additionalinformation");
                    String lowfloorstatus = jb1.getString("lowfloorstatus");
                    String route = jb1.getString("route");
                    String sourcetimestamp = jb1.getString("sourcetimestamp");
                    String monitored = jb1.getString("monitored");
                    Log.i(".......\nstopid  ", stopid +
                            "\narrivaldatetime  " + arrivaldatetime +
                            "\nduetime  " + duetime +
                            "\ndeparturedatetime  " + departuredatetime +
                            "\ndepartureduetime  " + departureduetime +
                            "\nscheduledarrivaldatetime  " + scheduledarrivaldatetime +
                            "\nscheduleddeparturedatetime  " + scheduleddeparturedatetime +
                            "\ndestination  " + destination +
                            "\ndestinationlocalized  " + destinationlocalized +
                            "\norigin  " + origin +
                            "\noriginlocalized  " + originlocalized +
                            "\ndirection  " + direction +
                            "\noperator  " + operator +
                            "\nadditionalinformation  " + additionalinformation +
                            "\nlowfloorstatus  " + lowfloorstatus +
                            "\nroute  " + route +
                            "\nsourcetimestamp  " + sourcetimestamp +
                            "\noperator  " + operator +
                            "\ndestinationlocalized  " + destinationlocalized +
                            "\nmonitored  " + monitored);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                     /*
                 timestamp"timestamp"
                 arrivaldatetime"arrivaldatetime"
                 duetime"duetime"
                 departuredatetime"departuredatetime"
                 departureduetime"departureduetime"
                 scheduledarrivaldatetime"scheduledarrivaldatetime"
                 scheduleddeparturedatetime"scheduleddeparturedatetime"
                 destination"destination"
                 destinationlocalized"destinationlocalized"
                 origin"origin"
                 originlocalized"originlocalized"
                 direction"direction"
                 operator"operator"
                 additionalinformation"additionalinformation"
                 lowfloorstatus"lowfloorstatus"
                 route"route"
                 sourcetimestamp"sourcetimestamp"
                 monitored"monitored"
            */
//            for(int i=0;i<JA.length();i++)
//            {
//                JSONObject JO = (JSONObject) JA.get(i);
//                singleParse ="errormessage:" +JO.get("errormessage");
//                        //"Name:" +JO.get("name")+ "\n"
//                        /*"Password:" +JO.get("password")+ "\n"+
//                        "Contact:" +JO.get("contact")+ "\n"+
//                        "Country:" +JO.get("country")+ "\n";*/
//                        /*+"\n"+
//                        "errormessage:" +JO.get("errormessage")+"\n"+
//                        "numberofresults:" +JO.get("numberofresults")+"\n"+
//                        "stopid:" +JO.get("stopid")+"\n"+
//                        "timestamp:" +JO.get("timestamp")+"\n"+
//                        "results:" +JO.get("results")+"\n"+
//                        "arrivaldatetime:" +JO.get("arrivaldatetime")+"\n"+
//                        "duetime:" +JO.get("duetime")
//                        */
//                dataParse = String.format("%s%s", dataParse, singleParse);
//
//            }
            }
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.data.setText(this.data);
    }
}