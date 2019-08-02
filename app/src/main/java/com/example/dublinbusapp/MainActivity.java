package com.example.dublinbusapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Locale;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button click,clicksearch,btnfirebase,btnStopInfo;
    public static TextInputEditText search;
    public static TextInputEditText startstopname;
//    String stopname;
//    String[] searchstopname1;
    public static TextInputEditText endstopname;

    public static TextView data;
//    DatabaseReference myRef;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();
//        myRef = database.getReference("9101");
        readfromDatabase();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        click = findViewById(R.id.button);
        clicksearch = findViewById(R.id.search_btn);
        btnfirebase=findViewById(R.id.firebase);
        btnStopInfo=findViewById(R.id.StopInfo);

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
//
//                }
//                catch(JSONException e){
//                    e.printStackTrace();
//                }
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
            openActivity2();
//            fetchData process = new fetchData();
//            process.execute();


            BusStopInformation processdata = new BusStopInformation();
            processdata.execute();
        }
});

    }
    public void openActivity2(){
        Intent intent = new Intent(MainActivity.this, Activity2.class);
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

    @Override
    protected void onActivityResult( int requestcode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestcode,resultCode,data);

        switch (requestcode){
            case 100:{
                if(resultCode==RESULT_OK&&null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    search.setText(result.get(0));
                    fetchData process = new fetchData();
                    process.execute();
                }
                break;
            }
        }


    }


}