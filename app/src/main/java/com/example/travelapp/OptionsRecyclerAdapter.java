package com.example.travelapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OptionsRecyclerAdapter extends RecyclerView.Adapter<OptionsRecyclerAdapter.ViewHolder>  {
    private ArrayList<Options>options;
    private ItemClickListener mItemClickListener;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final ImageView image;
        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            image=view.findViewById(R.id.image);
        }

        public TextView getTextView() {
            return tvName;
        }
    }

    public OptionsRecyclerAdapter(ArrayList<Options> options, ItemClickListener itemClickListener) {
        this.options = options;
        this.mItemClickListener=itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_option, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Options option= options.get(position);
        holder.getTextView().setText(option.name);
        holder.image.setImageResource(option.image);
        holder.itemView.setOnClickListener(view -> {
            mItemClickListener.onItemClick(options.get(position));
        });
    }


    @Override
    public int getItemCount() {
        return options.size();
    }
    public interface ItemClickListener{
        void onItemClick(Options options);
    }
}