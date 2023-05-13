package com.example.todolistapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        db.insert(TABLE_NAME,null,data);
        db.close();

    }
    public int deleteTask(int id){
        db = getWritableDatabase();
        Log.d("id", String.valueOf(id));
        return db.delete(TABLE_NAME,"id= ?",new String[] {String.valueOf(id)});
//        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE id='"+id+"'");
    }
    public void updateStatus(int id,int status){
        ContentValues cv = new ContentValues();
        cv.put("status",status);
        db.update(TABLE_NAME,cv,"id= ?",new String[]{String.valueOf(id)});
    }
    public void updateTask(int id,String task){
        ContentValues cv = new ContentValues();
        cv.put("task",task);
        db.update(TABLE_NAME,cv,"id= ?",new String[]{String.valueOf(id)});
    }
    public int getCountTable(){
        db = this.getReadableDatabase();
        Cursor cursorCount = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursorCount.getCount();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(id integer primary key autoincrement NOT NULL, task text, note text, status integer, due_date text)";
        Log.d("Sql string", sql);
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO "+TABLE_NAME +"(id,task,note,status,due_date) values ('1','Main','test',0,'2009')";
        sqLiteDatabase.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
