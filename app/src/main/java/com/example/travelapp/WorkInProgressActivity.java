package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class WorkInProgressActivity extends AppCompatActivity {

    LottieAnimationView animation;
    ImageView imgMainMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_in_progress);
        animation=findViewById(R.id.animation);
        imgMainMenu=findViewById(R.id.imgMainMenu);
        imgMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkInProgressActivity.this,MainActivity.class));
            }
        });
        //animation.playAnimation();
    }
}