package com.example.todolistapplication.repository;

import com.example.todolistapplication.database.Todo;
import com.example.todolistapplication.database.TodoDatabaseDao;

public class repository {
    private TodoDatabaseDao todoDatabaseDao;
    public repository(TodoDatabaseDao todoDatabaseDao) {
        this.todoDatabaseDao = todoDatabaseDao;
    }

    public void insertTodo(Todo todo){
//        todoDatabaseDao.insertTask(todoe);
    }
}
