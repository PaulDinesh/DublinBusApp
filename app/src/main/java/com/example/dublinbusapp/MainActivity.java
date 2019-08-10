package com.example.dublinbusapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private WebView mywebView;
    Button btn_webview;
    Button click,clicksearch,btnfirebase,btnStopInfo,btnStartVoice,btnStopVoice;

    public interface readCallback{
        void onCallback(ArrayList<StopInformation> start,ArrayList<StopInformation> end);
    }

    public static String Notify_route;
    private static final String  CHANNEL_ID ="SIMPLIFIED_CODING";
    private static final String  CHANNEL_name ="SIMPLG";
    private static final String  CHANNEL_desc ="SIMPLIF";
    public static TextInputEditText search;
    public static TextInputEditText startstopname;
    public static TextInputEditText endstopname;
    public static String[] Main_Start_Array;
    public static String[] Main_End_Array;
    public static TextView data;
//    DatabaseReference myRef;
    DatabaseReference database;
    BusStopInfo bus=new BusStopInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //adding the webview and notificationbutton//
        mywebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        btn_webview = (Button) findViewById(R.id.btn_webview);

        btn_webview.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View view) {
                                               openWebViewActivity();
//                                               mywebView.loadUrl("https://www.dublinbus.ie/Contact-Us1/Customer-Comment-Form/");
//                                               mywebView.setWebViewClient(new WebViewClient());
                                           }
                                       }
        );
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel(CHANNEL_ID,CHANNEL_name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_desc);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        findViewById(R.id.buttonNotify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNotification();
            }


        });

        database = FirebaseDatabase.getInstance().getReference();
//        myRef = database.getReference("9101");
        readfromDatabase();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        click = findViewById(R.id.button);
        clicksearch = findViewById(R.id.search_btn);
        btnfirebase=findViewById(R.id.firebase);
        btnStopInfo=findViewById(R.id.StopInfo);
        btnStartVoice=findViewById(R.id.start_voice_button);
        btnStopVoice=findViewById(R.id.end_voice_button);

        data = (TextView) findViewById(R.id.fetcheddata);

        search=findViewById(R.id.text_search);

        startstopname=findViewById(R.id.text_start);
        endstopname=findViewById(R.id.text_end);

//        Splitting two input stop names
//        stopname= String.valueOf(searchstopname);
//        searchstopname1 = stopname.split("to");
//        System.out.println(searchstopname1);
        clicksearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
                fetchData process = new fetchData();
                process.execute();


//                read & parse json
//                Resources res = getResources();
//                InputStream is = ((Resources) res).openRawResource(R.raw.bus_stop);
//                Scanner scanner = new Scanner(is);
//                StringBuilder builder = new StringBuilder();
//
//                while(scanner.hasNextLine()){
//                    String line =builder.append(scanner.nextLine());
//                    }
//                System.out.println(builder);
//                parse json
//                StringBuilder builder1 = new StringBuilder();
//                try{
//                    JSONObject root = new JSONObject("");
//
//                    JSONObject BusStop = root.getJSONObject("");
//
//                    JSONArray stop = BusStop.getJSONArray("");
//                    for(int i=0;i<stop.length();i++){
//                        JSONArray stop1 = stop.getJSONArray(i);
//                    }
//                }
//                catch(JSONException e){
//                    e.printStackTrace();
//                }
            }
        });
        btnStopVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent vb=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                vb.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                vb.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(vb,102);
            }
        });
        btnStartVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent va=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                va.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                va.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(va,101);
            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent vc=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                vc.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                vc.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(vc,100);
            }
        });

        btnfirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ViewDatabase.class);
            }});


btnStopInfo.setOnClickListener(new View.OnClickListener(){
        @Override
                public void onClick(View v){
//            openActivity2();
//            fetchData process = new fetchData();
//            process.execute();
//           BusStopInformation processdata = new BusStopInformation();
//            processdata.execute();
//            openActivity2();
            readBusData();
        }
});

    }
public void openActivity2(){
        Intent intent = new Intent(MainActivity.this, Activity2.class);
        startActivity(intent);
    }
    public void openWebViewActivity(){
        Intent intent = new Intent(MainActivity.this, Webview_Activity.class);
        startActivity(intent);
    }

    public void readfromDatabase(){// Read from the database
        database.addValueEventListener(new ValueEventListener() {
//            database.orderByChild("PlateCode").equalTo("339072").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String childvalue = String.valueOf(dataSnapshot.child("PlateCode").getValue());
//             showData(dataSnapshot);
//                Log.d("paul", "Value is: " + childvalue);
//                String childvalue1 = String.valueOf(dataSnapshot.getValue());
//
//                Log.d("Data", "Value is: " + childvalue1);

            }

//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //                String value = dataSnapshot.getValue(String.class);
//
//                String childvalue = String.valueOf(dataSnapshot.getValue());
//                showData(dataSnapshot);
//                Log.d("paul", "Value is: " + childvalue);
//            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });
}
    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Log.d("loop", "Value is: " + ds);
        }

    }
    public void onBackPressed()
    {
        if (mywebView.canGoBack()){
            mywebView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
    private void displayNotification(){
        Notify_route=fetchData.route;
        NotificationCompat.Builder mBuilder=
                new NotificationCompat.Builder(this,CHANNEL_ID )
                        .setSmallIcon(R.drawable.ic_transfer)
                        .setContentTitle("Time Left for "+Notify_route+" Bus")
                        .setContentText("7 min")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat mNotificationMgr= NotificationManagerCompat.from(this);
        mNotificationMgr.notify(1, mBuilder.build());
    }
    public void readBusData  (){
        bus.getBusStopInfo(new readCallback() {
            @Override
            public void onCallback(ArrayList<StopInformation> start, ArrayList<StopInformation> end) {
System.out.println("Aticy-------------------before passing values---------");

                Activity2.activity2_Start_Array=null;
                Activity2.activity2_End_Array=null;

                Activity2.activity2_Start_Array=start;
                Activity2.activity2_End_Array=end;

               System.out.println("Aticy-------after passing values---------------------"+start+end);
                openActivity2();
            }
});
    }

    @Override
    protected void onActivityResult( int requestcode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestcode,resultCode,data);

        switch (requestcode){
            case 100:{
                if(resultCode==RESULT_OK&&null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    search.setText(result.get(0));
                    openActivity2();
                    fetchData process = new fetchData();
                    process.execute();
                }}
                break;
                case 101:{
                    if(resultCode==RESULT_OK&&null!=data){
                        ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        startstopname.setText(result.get(0));
//                        openActivity2();
//                        BusStopInformation processdata = new BusStopInformation();
//                        processdata.execute();
                    }
                    break;
            }
            case 102:{
                if(resultCode==RESULT_OK&&null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    endstopname.setText(result.get(0));
                    readBusData();
                    //                    openActivity2();
//                    BusStopInformation processdata = new BusStopInformation();
//                    processdata.execute();
                }
                break;
            }
        }
    }
}
