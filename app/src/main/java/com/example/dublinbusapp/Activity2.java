package com.example.dublinbusapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity2 extends AppCompatActivity {
    public static TextView data;
    public static TextView Endstopdata;
    public static TextView commonroute;
    public static ListView start_listView;
    public static ListView end_listView;
    public static ArrayList<StopInformation> activity2_Start_Array ;
    public static ArrayList<StopInformation> activity2_End_Array ;
    public static String activity2_start = "";
    public static String activity2_end = "";

    public String commonrt;
    Button btnBusno;

    public interface BackPressedListener {
        void onBackPressed();
    }
//    String[] List_start=start_Array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        data = (TextView) findViewById(R.id.Stopdata);
        Endstopdata = (TextView) findViewById(R.id.Endstopdata);
        commonroute = (TextView) findViewById(R.id.commonroute);

        StopInformation startobj = new StopInformation();
        startobj=activity2_Start_Array;
        startobj.getstopid();
        startobj.getshortname();
        startobj.getfullname();

        StopInformation endobj = new StopInformation();
        endobj=activity2_End_Array;
        endobj.getstopid();
        endobj.getshortname();
        endobj.getfullname();

        System.out.println("Activity2 "+activity2_Start_Array);
        System.out.println("Activity2 "+activity2_End_Array);

//        Listview();

        btnBusno = findViewById(R.id.BusNo);
        btnBusno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Listview();

                BusStopInformation processdata = new BusStopInformation();
                processdata.execute();
            }
        });
    }
        /*private void Listview() {
            start_listView = (ListView) findViewById(R.id.List_Startdata);
            ArrayAdapter<String> adapter_start = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activity2_Start_Array);
            start_listView.setAdapter(adapter_start);
            start_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(Activity2.this, activity2_Start_Array.get(position), Toast.LENGTH_SHORT).show();
                    activity2_start = activity2_Start_Array.get(position);
                }

            });

            end_listView = (ListView) findViewById(R.id.List_Enddata);
            ArrayAdapter<String> adapter_end = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activity2_End_Array);
            end_listView.setAdapter(adapter_end);
            end_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(Activity2.this, activity2_Start_Array.get(position), Toast.LENGTH_SHORT).show();
                    activity2_end = activity2_End_Array.get(position);
                }

            });
        }*/
    }


//        List view
//            start_listView = (ListView) findViewById(R.id.List_Startdata);
//            ArrayAdapter<String> adapter_start = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activity2_Start_Array);
//            start_listView.setAdapter(adapter_start);
//            start_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(Activity2.this, activity2_Start_Array.get(position), Toast.LENGTH_SHORT).show();
//                    activity2_start = activity2_Start_Array.get(position);
//                }
//
//            });
//
//            end_listView = (ListView) findViewById(R.id.List_Enddata);
//            ArrayAdapter<String> adapter_end = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activity2_End_Array);
//            end_listView.setAdapter(adapter_end);
//            end_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(Activity2.this, activity2_Start_Array.get(position), Toast.LENGTH_SHORT).show();
//                    activity2_end = activity2_End_Array.get(position);
//                }
//
//            });



/*  ListView ArrayAdapter Class
        class MylistAdapter extends ArrayAdapter{
            int resource;
            Context context;
            public MylistAdapter(Context context, int resource,ArrayList<StopInformation> activity2_Start_Array) {
                super(context, resource, activity2_Start_Array);
                this.context=context;
                this.resource=resource;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder mainViewholder;
                if(convertView == null)
                {
//                    start_listView=findViewById(R.id.List_Startdata);
//                    end_listView=findViewById(R.id.List_Enddata);

                    start_listView.setText(test.setstopid);
                    end_listView.setText(test1.setstopid);
                         getItem(position);
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(resource,parent,false);

                    start_listView=convertView.findViewById(R.id.List_Startdata);
                    end_listView=convertView.findViewById(R.id.List_Enddata);

                }

             return convertView;
            }

        }*/
