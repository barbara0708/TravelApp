package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class TravelTips extends AppCompatActivity {
    WebView webView;
    ImageView imgBack3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_tips);
        webView=findViewById(R.id.webView);
        imgBack3=findViewById(R.id.imgBack3);
        imgBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TravelTips.this,MainActivity.class));
            }
        });
        webView.loadUrl("file:///android_asset/tips.html");
    }
}