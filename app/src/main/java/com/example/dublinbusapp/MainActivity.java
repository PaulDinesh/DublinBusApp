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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button click,clicksearch;
    public static TextInputEditText search;
    public static TextView data;
    private WebView mywebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mywebView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings= mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mywebView.loadUrl("https://www.dublinbus.ie/Contact-Us1/Customer-Comment-Form/");
        mywebView.setWebViewClient(new WebViewClient());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        click = findViewById(R.id.button);
        clicksearch = findViewById(R.id.search_btn);
        data = (TextView) findViewById(R.id.fetcheddata);
search=findViewById(R.id.as);
        clicksearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
            }

        });
        //comment//








        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent vc=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                vc.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                vc.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(vc,100);
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
            fetchData process = new fetchData();
                process.execute();
        }
        break;
    }
}


    }
}
