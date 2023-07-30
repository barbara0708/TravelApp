package com.example.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recAdapter extends RecyclerView.Adapter<recAdapter.MyViewHolder> {
    public recAdapter(ArrayList<Attractions> attractions, Context context,ItemClickListener itemClickListener) {
        this.attractions = attractions;
        this.myContext=context;
        this.mItemClickListener=itemClickListener;
    }

    public void setAttractions(ArrayList<Attractions> attractions) {
        this.attractions = attractions;

    }
    private ItemClickListener mItemClickListener;
    private ArrayList<Attractions>attractions;
    Context myContext;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAddress,tvName;
        private ImageView imgAttr;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress=(TextView) itemView.findViewById(R.id.tvAddress);
            tvName=itemView.findViewById(R.id.tvName);
            imgAttr=itemView.findViewById(R.id.imgAttr);

        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Attractions attraction=attractions.get(position);
        holder.tvAddress.setText(attraction.getAddress());
        holder.tvName.setText(attraction.getName());
        holder.imgAttr.setImageResource(attraction.getImage());
        holder.itemView.setOnClickListener(view -> {
            mItemClickListener.onItemClick(attractions.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public interface ItemClickListener{
        void onItemClick(Attractions attraction);
    }
}
