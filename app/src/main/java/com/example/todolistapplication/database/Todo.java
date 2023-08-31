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

    public Todo( String task, String note, String deadline, String category, int status) {
        this.task = task;
        this.note = note;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
    }


}


