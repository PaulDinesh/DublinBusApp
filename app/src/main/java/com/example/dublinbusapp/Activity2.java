package com.example.dublinbusapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {
    public static TextView data;
    public static TextView Endstopdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        data = (TextView) findViewById(R.id.Stopdata);
        Endstopdata = (TextView) findViewById(R.id.Endstopdata);
    }
}
