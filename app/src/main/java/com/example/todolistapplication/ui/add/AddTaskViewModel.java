package com.example.todolistapplication.ui.add;

import android.app.Application;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolistapplication.database.Todo;
import com.example.todolistapplication.database.TodoDatabase;
import com.example.todolistapplication.util.Utils;

import io.reactivex.rxjava3.core.Completable;

public class AddTaskViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> addButtonClick;
    private MutableLiveData<String> task;

    private MutableLiveData<String> note;
    private MutableLiveData<String> date;
    private MutableLiveData<String> time;
    private MutableLiveData<String> category;
    private TodoDatabase todoDatabase;

    public AddTaskViewModel(@NonNull Application application) {
        super(application);
        todoDatabase = TodoDatabase.getDatabase(application);
    }

    public void init(){
        task = new MutableLiveData<String>();
        note = new MutableLiveData<String>();
        date = new MutableLiveData<String>();
        time = new MutableLiveData<String>();
        category = new MutableLiveData<String>();
        addButtonClick = new MutableLiveData<Boolean>();

    }

//    getter and setter task
//    ======================
    public LiveData<String> getTask(){
        return task;
    }
    public void setTask(Editable s){
        this.task.setValue(s.toString());
    }

//    getter and setter note
//    ============================
    public LiveData<String> getNote(){return note;}
    public void setNote(Editable s){this.note.setValue(s.toString());}

    //    getter and setter date
//    ============================
    public LiveData<String> getDate(){return date;}
    public void setDate(Editable s){this.date.setValue(s.toString());}

    //    getter and setter time
//    ============================
    public LiveData<String> getTime(){return time;}
    public void setTime(Editable s){this.time.setValue(s.toString());}

    //    getter and setter category
//    ============================
    public LiveData<String> getCategory(){return category;}
    public void setCategory(Editable s){this.category.setValue(s.toString());}


//    getter and setter addbuttonclicked livedata
//    ===============================================
    public LiveData<Boolean> getAddButtonClicked(){
        return addButtonClick;
    }
    private void setAddButtonClick(Boolean addButtonClick){
        this.addButtonClick.setValue(addButtonClick);
    }

//    getter and setter adddbuttonclick condition
//    ==============================================
    public void onAddButtonClicked(){
        setAddButtonClick(true);
    }
    public void onDoneAddButtonClicked(){
        setAddButtonClick(false);
    }


    Completable addTaskToDatabase(){
        Todo todo = Utils.formToTodoModel(getTask().getValue(),getNote().getValue(),
                getCategory().getValue(),getDate().getValue(),getTime().getValue(),0);
        return todoDatabase.todoDao().insertTask(todo);
    }


}