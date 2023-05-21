package com.example.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapplication.Adapaters.ToDoAdapter;
import com.example.todolistapplication.models.ToDoModels;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DBConfig dbCenter;
//    ImageButton addButton;
    FloatingActionButton addButton;
    RecyclerView taskRecycleView;
    ToDoAdapter taskAdapter;
    public static MainActivity main;
    List<ToDoModels> tasks;
    Spinner categoryTaskSpinner;
    List<String> categoryList;
    ItemTouchHelper itemTouchHelper;
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
        addButton = (FloatingActionButton) findViewById(R.id.fab);
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
        itemTouchHelper = new ItemTouchHelper(new RecycleViewItemTouchHelper(taskAdapter,MainActivity.this));
        categoryTaskSpinner = (Spinner) findViewById(R.id.categoryspinner);
        loadSpinnerCategory();
        refreshTodos();
        itemTouchHelper.attachToRecyclerView(taskRecycleView);
        searchView.clearFocus();;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        categoryTaskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCategory = categoryList.get(i);
                if(selectedCategory.equalsIgnoreCase("All")){
                    refreshTodos();
                }else if(selectedCategory.equalsIgnoreCase("Completed")){
                  //display tasks for the selected category
                } else{
                    // display tasks for the selected category
                    List<ToDoModels> filteredList = new ArrayList<>();
                    for(ToDoModels item:tasks){
                        if(item.getCategory().equalsIgnoreCase(selectedCategory)){
                            filteredList.add(item);
                        }
                        Log.d("status now "+item.getTask(), String.valueOf(item.getStatus()));
                    }
                    taskAdapter.setTask(filteredList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void filterList(String newText) {
        List<ToDoModels> filteredList = new ArrayList<>();
        for(ToDoModels item:tasks){
            if(item.getTask().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(main, "No Data Found", Toast.LENGTH_SHORT).show();
        }else{
            taskAdapter.setFilteredList(filteredList);
        }
    }
    public void loadSpinnerCategory(){
        List<ToDoModels> getCategory = dbCenter.getAllTasks();
        categoryList = new ArrayList<>();
        categoryList.add("All");
        categoryList.add("Completed");
        for(ToDoModels item:getCategory){
            if(!getCategory.contains(item.getCategory())){
                categoryList.add(item.getCategory().toLowerCase());
            }
        }

        ArrayAdapter adapterCategory = new ArrayAdapter(this, R.layout.spinner_list,categoryList);
        categoryTaskSpinner.setAdapter(adapterCategory);
    }

    public void refreshTodos(){
        tasks =  dbCenter.getAllTasks();
        Collections.reverse(tasks);
        taskAdapter.setTask(tasks);
//        Update the checked states of the tasks
        for(ToDoModels task:tasks){
            taskAdapter.setTaskChecked(task.getId(),task.getStatus());
        }
    }

}