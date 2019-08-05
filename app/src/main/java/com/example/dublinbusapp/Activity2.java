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
    public static String[] activity2_Start_Array= MainActivity.Main_Start_Array;
    public static String[] activity2_End_Array= MainActivity.Main_End_Array;
//    String[] List_start=start_Array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        data = (TextView) findViewById(R.id.Stopdata);
        Endstopdata = (TextView) findViewById(R.id.Endstopdata);

        ListView start_listView = (ListView) findViewById(R.id.List_Startdata);
        ArrayAdapter<String> adapter_start=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,activity2_Start_Array);
        start_listView.setAdapter(adapter_start);

        ListView end_listView = (ListView) findViewById(R.id.List_Enddata);
        ArrayAdapter<String> adapter_end=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,activity2_End_Array);
        end_listView.setAdapter(adapter_end);
    }
}
