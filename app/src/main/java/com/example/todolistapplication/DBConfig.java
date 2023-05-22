package com.example.todolistapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.todolistapplication.Adapaters.ToDoAdapter;
import com.example.todolistapplication.models.ToDoModels;

import java.util.ArrayList;
import java.util.List;

public class DBConfig extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "ToDo";
    MainActivity main;
    private SQLiteDatabase db;
    protected  Cursor cursor;
    public DBConfig(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @SuppressLint("Range")
    public List<ToDoModels> getAllTasks(){
        List<ToDoModels> tasks = new ArrayList<ToDoModels>();
        db = getReadableDatabase();
        cursor = null;
        cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        cursor.moveToFirst();
        try{
            for(int cc=0;cc<cursor.getCount();cc++){
                cursor.moveToPosition(cc);
                ToDoModels task = new ToDoModels();
                task.setId(cursor.getInt(cursor.getColumnIndex("id")));
                task.setTask(cursor.getString(cursor.getColumnIndex("task")));
                task.setNote(cursor.getString(cursor.getColumnIndex("note")));
                task.setDue_date(cursor.getString(cursor.getColumnIndex("due_date")));
                task.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                task.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                tasks.add(task);
            }
        }catch (Exception e){

        }

        return tasks;
    }

    public void addTask(ToDoModels todo){
        db = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("task",todo.getTask());
        data.put("note",todo.getNote());
        data.put("status",0);
        data.put("due_date",todo.getDue_date());
        data.put("category",todo.getCategory().toLowerCase());
        String note = String.valueOf(data.get("note"));
        db.insert(TABLE_NAME,null,data);
    }
    public int deleteTask(int id){
        db = getWritableDatabase();
        return db.delete(TABLE_NAME,"id= ?",new String[] {String.valueOf(id)});
    }
    public void updateStatus(int id,int status){
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status",status);
        db.update(TABLE_NAME,cv,"id= ?",new String[]{String.valueOf(id)});

    }
    public void updateTask(int id, ToDoModels task){
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("task",task.getTask());
        cv.put("note",task.getNote());
        cv.put("due_date",task.getDue_date());
        cv.put("category",task.getCategory());
        db.update(TABLE_NAME,cv,"id= ?",new String[]{String.valueOf(id)});
    }
    public int getCountTable(){
        db = getReadableDatabase();
        Cursor cursorCount = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursorCount.getCount();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(id integer primary key autoincrement NOT NULL, task text NOT NULL, note text, status integer, due_date text,category text)";
        sqLiteDatabase.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
