package com.example.todolistapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface TodoDatabaseDao {
    @Query("SELECT * FROM todos")
    Flowable<List<Todo>> getAll();


    @Insert
    Single<Long> insertTask(Todo todo);
    @Update
    void updateTask(Todo todo);
    @Delete
    void deleteTask(Todo todo);

}
