package com.example.todolistapplication.util;

import android.util.Log;

import com.example.todolistapplication.database.Todo;

public class Utils {
    public static Todo formToTodoModel(String task,String note,String category,String date,String time,Integer status){
        String deadline = date+" "+time;
        Log.d("task",task);
        Todo todoModel = new Todo(task,note,category,deadline,status);
        return todoModel;
    }
}
