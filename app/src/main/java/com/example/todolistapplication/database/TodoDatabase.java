package com.example.todolistapplication.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class},version = 1)
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoDatabaseDao todoDao();
    private static TodoDatabase instance;

    public static TodoDatabase getDatabase(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static TodoDatabase create(final Context context){
        return Room.databaseBuilder(context,
                TodoDatabase.class,
                "Todo_db").build();
    }

}
