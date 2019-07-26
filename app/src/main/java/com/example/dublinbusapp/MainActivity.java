package com.example.dublinbusapp;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button click,clicksearch,btnfirebase;
    public static TextInputEditText search;
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
        data = (TextView) findViewById(R.id.fetcheddata);
        search=findViewById(R.id.as);
        clicksearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
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
    }
    public void readfromDatabase(){// Read from the database
        database.addValueEventListener(new ValueEventListener() {
//            database.orderByChild("PlateCode").equalTo("339072").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String childvalue = String.valueOf(dataSnapshot.child("PlateCode").getValue());
                showData(dataSnapshot);
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