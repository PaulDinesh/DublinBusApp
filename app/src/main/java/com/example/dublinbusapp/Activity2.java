package com.example.dublinbusapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {
    public static TextView data;
    public static TextView Endstopdata;
    public static ListView start_listView;
    public static ListView end_listView;
    public static String[] activity2_Start_Array;
    public static String[] activity2_End_Array;
//    String[] List_start=start_Array;

    public static String[] getactivity2_Start_Array() {
    return activity2_Start_Array;
}

    public static void setactivity2_Start_Array(String[] Activity2_Start_Array) {
        activity2_Start_Array = Activity2_Start_Array;
    }

    public static String[] getactivity2_End_Array() {
        return activity2_End_Array;
    }

    public static void setactivity2_End_Array(String[] Activity2_End_Array) {
        activity2_End_Array = Activity2_End_Array;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        data = (TextView) findViewById(R.id.Stopdata);
        Endstopdata = (TextView) findViewById(R.id.Endstopdata);



//        String s=data.getText().toString();
//        activity2_Start_Array=s.split("\n");
//        String s1=data.getText().toString();
//        activity2_End_Array=s.split("\n");




        for(String i:activity2_Start_Array){
        System.out.println("activity2_Start_Array"+i);}
        for(String i:activity2_Start_Array){
            System.out.println("activity2_End_Array"+i);}

        start_listView = (ListView) findViewById(R.id.List_Startdata);
        ArrayAdapter<String> adapter_start=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,activity2_Start_Array);
        start_listView.setAdapter(adapter_start);
//
        end_listView = (ListView) findViewById(R.id.List_Enddata);
        ArrayAdapter<String> adapter_end=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,activity2_End_Array);
        end_listView.setAdapter(adapter_end);
    }
}
