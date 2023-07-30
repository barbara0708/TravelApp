package com.example.travelapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    public Context myContext;
    public ArrayList<Integer> MyimageArray;
    public ImageAdapter(Context context, ArrayList<Integer> imageArray) {
        this.myContext = context;
        this.MyimageArray=imageArray;
    }

    @Override
    public int getCount() {
        return MyimageArray.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView=(ImageView) view;
        if(imageView==null){
            imageView=new ImageView(myContext);
            imageView.setLayoutParams(new GridView.LayoutParams(350,450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        imageView.setImageResource(MyimageArray.get(i));
        return imageView;
    }
}

