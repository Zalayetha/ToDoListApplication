package com.example.todolistapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapplication.Adapaters.ToDoAdapter;
import com.example.todolistapplication.database.Todo;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addButton;
    RecyclerView taskRecycleView;
    ToDoAdapter taskAdapter;
    public static MainActivity main;
    List<Todo> tasks;
    Spinner categoryTaskSpinner;
    List<String> categoryList;
    ItemTouchHelper itemTouchHelper;
     private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController =  Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(MainActivity.this,navController);

//        Objects.requireNonNull(getSupportActionBar()).hide();
//        SearchView searchView = (SearchView) findViewById(R.id.SearchView);
//        EditText searchEditTextVieEditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
////        searchEditTextView.setTextColorw = ((getResources().getColor(R.color.white));
//        searchEditTextView.setHintTextColor(getResources().getColor(R.color.white));
//
//        // database
//        addButton = (FloatingActionButton) findViewById(R.id.fab);
//
//        main= this;
//        taskRecycleView = (RecyclerView) findViewById(R.id.taskRecycleViewer);
//        taskRecycleView.setLayoutManager(new LinearLayoutManager(this));
//        taskAdapter = new ToDoAdapter(MainActivity.this,dbCenter);
//        taskRecycleView.setAdapter(taskAdapter);
//        itemTouchHelper = new ItemTouchHelper(new RecycleViewItemTouchHelper(taskAdapter,MainActivity.this));
//        categoryTaskSpinner = (Spinner) findViewById(R.id.categoryspinner);
//        loadSpinnerCategory();
//        refreshTodos();
//        itemTouchHelper.attachToRecyclerView(taskRecycleView);
//        searchView.clearFocus();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterList(newText);
//                return true;
//            }
//        });
//        categoryTaskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectedCategory = categoryList.get(i);
//                if(selectedCategory.equalsIgnoreCase("All")){
//                    refreshTodos();
//                }else if(selectedCategory.equalsIgnoreCase("Completed")){
//                  //display tasks for the selected category
//                    tasks =  dbCenter.getAllTasks();
//                    Collections.reverse(tasks);
//                    List<Todo> filteredList = new ArrayList<>();
//                    for(Todo item:tasks){
//                        if(item.getStatus() == 1){
//                            filteredList.add(item);
//                            taskAdapter.setTaskChecked(item.getId(),item.getStatus());
//                        }
//                    }
//
//                    taskAdapter.setTask(filteredList);
//                } else{
//                    // display tasks for the selected category
//                    tasks =  dbCenter.getAllTasks();
//                    Collections.reverse(tasks);
//                    List<Todo> filteredList = new ArrayList<>();
//                    for(Todo item:tasks){
//                        if(item.getCategory().equalsIgnoreCase(selectedCategory)){
//                            filteredList.add(item);
//                            taskAdapter.setTaskChecked(item.getId(),item.getStatus());
//                        }
//
//                    }
//
//                    taskAdapter.setTask(filteredList);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return navController.navigateUp()||super.onSupportNavigateUp();
    }
//    private void filterList(String newText) {
//        List<Todo> filteredList = new ArrayList<>();
//        for(Todo item:tasks){
//            if(item.getTask().toLowerCase().contains(newText.toLowerCase())){
//                filteredList.add(item);
//            }
//        }
//        if(filteredList.isEmpty()){
//            Toast.makeText(main, "No Data Found", Toast.LENGTH_SHORT).show();
//        }else{
//            taskAdapter.setFilteredList(filteredList);
//        }
//    }
//    public void loadSpinnerCategory(){
//        List<Todo> getCategory = dbCenter.getAllTasks();
//        categoryList = new ArrayList<>();
//        categoryList.add("All");
//        categoryList.add("Completed");
//        for(Todo item:getCategory){
//            if(!categoryList.contains(item.getCategory().toLowerCase())){
//                categoryList.add(item.getCategory().toLowerCase());
//            }
//        }
//
//        ArrayAdapter adapterCategory = new ArrayAdapter(this, R.layout.spinner_list,categoryList);
//        adapterCategory.setDropDownViewResource(R.layout.spinner_list);
//        categoryTaskSpinner.setAdapter(adapterCategory);
//    }

//    public void refreshTodos(){
//        tasks =  dbCenter.getAllTasks();
//        Collections.reverse(tasks);
//        //        Update the checked states of the tasks
//        for(Todo task:tasks){
//            taskAdapter.setTaskChecked(task.getId(),task.getStatus());
//        }
//        taskAdapter.setTask(tasks);
//    }


}