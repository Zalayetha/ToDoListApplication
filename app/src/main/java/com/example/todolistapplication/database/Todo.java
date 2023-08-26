package com.example.todolistapplication.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Todos")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "task")
    public String task;
    @ColumnInfo(name = "note")
    public String note;
    @ColumnInfo(name = "deadline")
    public String deadline;
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "status")
    public int status;
}


