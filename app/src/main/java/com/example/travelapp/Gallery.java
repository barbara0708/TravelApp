package com.example.travelapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class Gallery extends AppCompatActivity {
    GridView grid_view;
    ImageView imgBack;
    ArrayList<Integer> imageArray = new ArrayList < >(Arrays.asList(
            R.drawable.dresden,R.drawable.first_ber,R.drawable.gate_ber,R.drawable.hamburg,
            R.drawable.koln
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        grid_view=findViewById(R.id.grid_view);
        imgBack=findViewById(R.id.imgBack);
        grid_view.setAdapter(new ImageAdapter(this,imageArray));
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item_pos = imageArray.get(position);
                ShowDialogBox(item_pos);
            }
        });
    }
    public void ShowDialogBox(final int item_pos){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.activity_full_screen);
        TextView Image_name=dialog.findViewById(R.id.txt_Image_name);
        ImageView Image = dialog.findViewById(R.id.img);
        Button btn_Full = dialog.findViewById(R.id.btn_full);
        Button btn_Close = dialog.findViewById(R.id.btn_close);
        String title=getResources().getResourceName(item_pos);
        Image.setImageResource(item_pos);
        int index = title.indexOf("/");
        String name = title.substring(index+1,title.length());
        Image_name.setText(name);
        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_Full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),FullView.class);
                i.putExtra("img_id",item_pos);
                startActivity(i);
            }
        });
        dialog.show();
    }
}