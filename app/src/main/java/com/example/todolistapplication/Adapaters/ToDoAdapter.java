package com.example.todolistapplication.Adapaters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapplication.DBConfig;
import com.example.todolistapplication.MainActivity;
import com.example.todolistapplication.R;
import com.example.todolistapplication.UpdateTask;
import com.example.todolistapplication.models.ToDoModels;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private DBConfig db;
    Context context;
    SQLiteDatabase sqLiteDatabase;
    private List<ToDoModels> toDOList;
    private MainActivity main;
    public ToDoAdapter(){

    }
    public ToDoAdapter(MainActivity main,DBConfig db){
        this.main = main;
        this.db = db;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklayout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        final ToDoModels itemList = toDOList.get(position);
        holder.task.setText(itemList.getTask());
        holder.task.setChecked(convertToBoolean(itemList.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Log.d("True", String.valueOf(1));
                    db.updateStatus(itemList.getId(),1);
                }else{
                    Log.d("False", String.valueOf(2));
                    db.updateStatus(itemList.getId(),2);
                }
            }
        });

    }
    public void deleteItem(int position){
        ToDoModels item =toDOList.get(position);
        int del = db.deleteTask(item.getId());
        if(del >0){
            toDOList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,getItemCount());
            Log.d("size after", String.valueOf(getItemCount()));
        }else{
            Log.d("gagal","delete");
        }

    }
    public boolean convertToBoolean(int status){
        return status != 0;
    }
    @Override
    public int getItemCount() {
//        Log.d("Size ToDoList", String.valueOf(toDOList.size()));
        return toDOList.size();
    }

    public void setTask(List<ToDoModels> toDOList) {
        this.toDOList = toDOList;
        notifyDataSetChanged();
    }
    public void editItem(int position){
        ToDoModels item = toDOList.get(position);
        UpdateTask update = new UpdateTask();

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
