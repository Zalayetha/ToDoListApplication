package com.example.todolistapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDatabaseDao {
    @Query("SELECT * FROM todos")
    List<Todo> getAll();
    @Insert
    void insertTask(Todo todo);
    @Update
    void updateTask(Todo todo);
    @Delete
    void deleteTask(Todo todo);

}
