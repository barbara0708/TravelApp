package com.example.travelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class City extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Attractions>attractions=new ArrayList<>();
    LinearLayoutManager manager;
    Button btnLocation;
    ImageView ivArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        recyclerView=findViewById(R.id.recyclerView);
        ivArrow=findViewById(R.id.ivArrow);
        btnLocation=findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Map.class));
            }
        });
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(City.this,MainActivity.class));
            }
        });
        attractions=new ArrayList<>();
        attractions.add(new Attractions("Reichstag",R.drawable.reichstag,"Platz der Republik 1"));
        attractions.add(new Attractions("Brandenburg Gate",R.drawable.gate_ber,"Pariser Platz"));
        attractions.add(new Attractions("Berlin Television Tower",R.drawable.tower,"Panoramastraße 1A"));
        attractions.add(new Attractions("Humboldt Forum",R.drawable.forum," Schloßpl."));
        recAdapter adapter=new recAdapter(attractions, this, new recAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Attractions attraction) {
                switch(attraction.getName()){
                    case "Reichstag":
                        open("https://www.bundestag.de/en/visittheBundestag/dome/registration-245686");
                        break;
                    case "Brandenburg Gate":
                        open("https://en.wikipedia.org/wiki/Brandenburg_Gate");
                        break;
                    case "Berlin Television Tower":
                        open("https://en.wikipedia.org/wiki/Fernsehturm_Berlin");
                        break;
                    case "Humboldt Forum":
                        open("https://en.wikipedia.org/wiki/Humboldt_Forum");
                        break;
                }
            }
        });
        manager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    public void open(String link){
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }
}