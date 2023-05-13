package com.example.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapplication.Adapaters.ToDoAdapter;
import com.example.todolistapplication.models.ToDoModels;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DBConfig dbCenter;
    ImageButton addButton;
    RecyclerView taskRecycleView;
    ToDoAdapter taskAdapter;
    public static MainActivity main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        SearchView searchView = (SearchView) findViewById(R.id.SearchView);
        EditText searchEditTextView = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditTextView.setTextColor(getResources().getColor(R.color.white));
        searchEditTextView.setHintTextColor(getResources().getColor(R.color.white));

        // database
        dbCenter = new DBConfig(this);
        addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTaskAct = new Intent(MainActivity.this, AddTask.class);
                startActivity(addTaskAct);

            }
        });
        main= this;
        taskRecycleView = (RecyclerView) findViewById(R.id.taskRecycleViewer);
        taskRecycleView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdapter(MainActivity.this,dbCenter);
        taskRecycleView.setAdapter(taskAdapter);
        refreshTodos();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecycleViewItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskRecycleView);
    }
    public void refreshTodos(){
        List<ToDoModels> tasks =  dbCenter.getAllTasks();
        Log.d("size tasks", String.valueOf(tasks.size()));
        Collections.reverse(tasks);
        taskAdapter.setTask(tasks);

}

}