package com.example.todolistapplication.database;

import static org.junit.Assert.assertThat;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subscribers.TestSubscriber;

@RunWith(JUnit4.class)
public class TodoDatabaseTest extends TestCase {
    private TodoDatabase todoDatabase;
    private TodoDatabaseDao todoDatabaseDao;

    @Before
    @Override
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        todoDatabase = Room.inMemoryDatabaseBuilder(context,TodoDatabase.class).build();
        todoDatabaseDao = todoDatabase.todoDao();
    }
    @After
    public void closeDb(){
        todoDatabase.close();
    }

    @Test
    public void writeAndReadTestTodoDatabase(){

        Todo todo = new Todo("Main Bola","Bersama Miya","2023/09/12 09:00","Bermain",0);
        TestSubscriber<Long> testSubscriberAdd = new TestSubscriber<>();
        todoDatabaseDao.insertTask(todo);
        Flowable<List<Todo>> todoList = todoDatabaseDao.getAll();
        TestSubscriber<List<Todo>> testSubscriberGet = new TestSubscriber<>();
        todoList.subscribe(testSubscriberGet);
        testSubscriberGet.assertNoErrors(); // memastikan tidak ada error
        testSubscriberGet.assertValue(todos -> todos.contains(todo)); // memastikan elemen ada pada daftar
    }

}