package com.example.travelapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<com.example.travelapp.ToDoModel>list;
    private DatabaseHandler db;
    private Checklist checklist;

    public ToDoAdapter(DatabaseHandler handler, Checklist checklist) {
        this.db = handler;
        this.checklist = checklist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db.openDatabase();

        final com.example.travelapp.ToDoModel model=list.get(position);
        holder.task.setText(model.getTask());
        holder.task.setChecked(toBoolean(model.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    db.updateStatus(model.getId(),1);
                }else {
                    db.updateStatus(model.getId(), 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public Context getContext(){
        return checklist;
    }
    public void setTasks(List<com.example.travelapp.ToDoModel>tasks){
        this.list=tasks;
        notifyDataSetChanged();
    }
    public void deleteItem(int position){
        com.example.travelapp.ToDoModel item=list.get(position);
        db.deleteTask(item.getId());
        list.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        com.example.travelapp.ToDoModel item=list.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask addNewTask=new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(checklist.getSupportFragmentManager(),AddNewTask.TAG);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        public ViewHolder(View view) {
            super(view);
            task=view.findViewById(R.id.todoCheckBox);
        }
    }
    boolean toBoolean(int n){
        return n!=0;
    }
}
