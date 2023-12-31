package com.example.travelapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class Checklist extends AppCompatActivity implements DialogCloseListener{
    private DatabaseHandler db;
    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private FloatingActionButton fab;
     ImageView imgBack;

    private List<ToDoModel>tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        db=new DatabaseHandler(this);
        db.openDatabase();

        imgBack=findViewById(R.id.imgBack1);
        taskRecyclerView=findViewById(R.id.tasksRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter=new ToDoAdapter(db,Checklist.this);
        taskRecyclerView.setAdapter(taskAdapter);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);

        fab=findViewById(R.id.fab);
        tasks=db.getAllTasks();
        Collections.reverse(tasks);
        taskAdapter.setTasks(tasks);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Checklist.this,MainActivity.class));
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        tasks=db.getAllTasks();
        Collections.reverse(tasks);
        taskAdapter.setTasks(tasks);
        taskAdapter.notifyDataSetChanged();
    }
}