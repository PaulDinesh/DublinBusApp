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
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //URL url= new URL("https://data.smartdublin.ie/cgi-bin/rtpi/realtimebusinformation?stopid=7602&format=json");
            //URL url= new URL("http://api.myjson.com/bins/j5f6b");
            URL url= new URL("https://api.myjson.com/bins/96k67");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String jsonStr = sh.makeServiceCall(url);


        String line = "";
            while(line!=null)
            {
                line = bufferedReader.readLine();
            data =data+line;
            }
//            data = String.format("%s%s", data, line);}


            JSONObject JA = new JSONObject(data);
            JSONArray jr = JA.getJSONArray("results");
            for(int i=0;i<jr.length();i++)
            {
                JSONObject jb1 = jr.getJSONObject(i);
                String arrivaldatetime = jb1.getString("arrivaldatetime");
                Log.i(".......",arrivaldatetime);
            }
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
