package com.example.todolistapplication.Adapaters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapplication.DBConfig;
import com.example.todolistapplication.MainActivity;
import com.example.todolistapplication.R;
import com.example.todolistapplication.UpdateTask;
import com.example.todolistapplication.models.ToDoModels;

import java.util.HashMap;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private DBConfig db;
    Context context;
    SQLiteDatabase sqLiteDatabase;
    private List<ToDoModels> toDOList;
    private MainActivity main;
    private HashMap<Integer,Boolean> checkedStates;
    public ToDoAdapter(){

    }
    public ToDoAdapter(MainActivity main,DBConfig db){
        this.main = main;
        this.db = db;
        checkedStates = new HashMap<>();

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklayout,parent,false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        final ToDoModels itemList = toDOList.get(position);
        holder.task.setText(itemList.getTask());
        // TODO masih buggy
        boolean isChecked = checkedStates.getOrDefault(itemList.getId(),false);
        holder.task.setChecked(isChecked);
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(itemList.getId(),1);
                }else{
                    db.updateStatus(itemList.getId(),0);
                }
            }
        });
    }
    public void setTaskChecked(int taskId,int isChecked){
        checkedStates.put(taskId,convertToBoolean(isChecked));

    }

    public void deleteItem(int position){
        ToDoModels item =toDOList.get(position);
        int del = db.deleteTask(item.getId());
        if(del >0){
            toDOList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,getItemCount());
        }else{
        }

    }
    public boolean convertToBoolean(int status){
        return status != 0;
    }
    @Override
    public int getItemCount() {
        return toDOList.size();
    }

    public void setTask(List<ToDoModels> toDOList) {
        this.toDOList = toDOList;
        notifyDataSetChanged();
    }
    public void editItem(int position){
        ToDoModels item = toDOList.get(position);
        Context context = getContext();
        Intent i = new Intent(context,UpdateTask.class);
        i.putExtra("id",item.getId());
        i.putExtra("task",String.valueOf(item.getTask()));
        i.putExtra("note",String.valueOf(item.getNote()));
        i.putExtra("due_date",String.valueOf(item.getDue_date()));
        i.putExtra("category",String.valueOf(item.getCategory()));
        context.startActivity(i);
    }
    public void setFilteredList(List<ToDoModels> filteredList){
        this.toDOList = filteredList;
        notifyDataSetChanged();
    }
    public Context getContext(){
        return main;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task =itemView.findViewById(R.id.todoCheckBox);
        }
        public TextView getDataView(){
            return task;
        }
    }
}
