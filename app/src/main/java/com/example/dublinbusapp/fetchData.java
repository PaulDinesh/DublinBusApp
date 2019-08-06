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
    String dataParsed = "\n\n\nRoute\t Destination\t duetime \n";
    String singleParsed="";
    String errorcode;
    String stopid = null;
    String searchterm=MainActivity.search.getText().toString();
    String arrivaldatetime;
    String duetime;
    String departuredatetime;
    String departureduetime;
    String scheduledarrivaldatetime;
    String scheduleddeparturedatetime;
    String destination;
    String destinationlocalized;
    String origin;
    String originlocalized;
    String direction;
    String operator;
    String additionalinformation;
    String lowfloorstatus;
    public static String route;
    String sourcetimestamp;
    String monitored;

    @Override
    protected Void doInBackground(Void... voids) {
        try {

System.out.println("help......"+searchterm);
            URL url= new URL("https://data.smartdublin.ie/cgi-bin/rtpi/realtimebusinformation?stopid="+searchterm+"&format=json");
            //URL url = new URL("https://api.myjson.com/bins/96k67");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String jsonStr = sh.makeServiceCall(url);
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
//            if (data==null){ dataParsed="No buses currently at the moment";}
//            data = String.format("%s%s", data, line);}

            JSONObject JA = new JSONObject(data);
            JSONArray jr = JA.getJSONArray("results");
            System.out.println(jr);
            stopid = (String) JA.getString("stopid");
            String timestamp = JA.getString("timestamp");
            errorcode=(String) JA.getString("errorcode");
            for (int i = 0; i < jr.length(); i++) {
//                if (errorcode!="0"){ dataParsed="No buses currently at the moment";break;}
//                else {

                JSONObject jb1 = jr.getJSONObject(i);

                try {

                    arrivaldatetime = jb1.getString("arrivaldatetime");
                    duetime = jb1.getString("duetime"); if (duetime=="Due") {duetime+=" Now";} else {duetime+=" min";};
                        System.out.println(duetime);
                     departuredatetime = jb1.getString("departuredatetime");
                     departureduetime = jb1.getString("departureduetime");
                    scheduledarrivaldatetime = jb1.getString("scheduledarrivaldatetime");
                    scheduleddeparturedatetime = jb1.getString("scheduleddeparturedatetime");
                    destination = jb1.getString("destination");
                    destinationlocalized = jb1.getString("destinationlocalized");
                    origin = jb1.getString("origin");
                    originlocalized = jb1.getString("originlocalized");
                    direction = jb1.getString("direction");
                    operator = jb1.getString("operator");
                    additionalinformation = jb1.getString("additionalinformation");
                    lowfloorstatus = jb1.getString("lowfloorstatus");
                    route = jb1.getString("route");
                    sourcetimestamp = jb1.getString("sourcetimestamp");
                    monitored = jb1.getString("monitored");

//                    System.out.println(arrivaldatetime);

                    singleParsed = route + "   "+destinationlocalized + "   " +  duetime +"\n";
                    dataParsed = dataParsed + singleParsed;

//                    Log.i(".......\nstopid  ", stopid +"\narrivaldatetime  " + arrivaldatetime +"\nduetime  " + duetime +"\ndeparturedatetime  " + departuredatetime + "\ndepartureduetime  " + departureduetime + "\nscheduledarrivaldatetime  " + scheduledarrivaldatetime +"\nscheduleddeparturedatetime  " + scheduleddeparturedatetime +"\ndestination  " + destination +"\ndestinationlocalized  " + destinationlocalized +"\norigin  " + origin +"\noriginlocalized  " + originlocalized +"\ndirection  " + direction +"\noperator  " + operator +"\nadditionalinformation  " + additionalinformation +"\nlowfloorstatus  " + lowfloorstatus +"\nroute  " + route +"\nsourcetimestamp  " + sourcetimestamp +"\nmonitored  " + monitored);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        Activity2.data.setText(this.dataParsed);
    }
}