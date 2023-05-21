package com.example.todolistapplication.models;

public class ToDoModels {
    int id;
    String task,note,due_date,category;
    int status;

    // empty constructor
    public ToDoModels(){

    }

    // constructor
    public ToDoModels(int id,String task,String note,int status,String due_date,String category){
        this.id = id;
        this.task = task;
        this.note = note;
        this.status = status;
        this.due_date = due_date;
        this.category = category;
    }

//    Setter dan getter
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getTask(){
        return task;
    }
    public void setTask(String task){
        this.task = task;
    }


    public String getNote(){
        return note;
    }
    public void setNote(String note){
        this.note = note;
    }

    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status = status;
    }

    public String getDue_date(){
        return due_date;
    }
    public void setDue_date(String due_date){
        this.due_date = due_date;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }


}
