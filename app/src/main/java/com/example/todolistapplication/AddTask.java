package com.example.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolistapplication.models.ToDoModels;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class AddTask extends AppCompatActivity {
    AppCompatButton backButton,submitButton;
    EditText dueDate,dueTime,taskText,noteText,categoryText;
    private int mYear,mMonth,mDay,mHour,mMinute;
    String task,note,dueDateTime,category;
    DBConfig db;
    List<ToDoModels> check_tasks;
    MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Objects.requireNonNull(getSupportActionBar()).hide();
        taskText = (EditText)findViewById(R.id.taskEditText);
        noteText = (EditText)findViewById(R.id.noteEditText);
        backButton = (AppCompatButton) findViewById(R.id.backButton);
        categoryText = (EditText) findViewById(R.id.categoryText);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddTask.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        dueDate = (EditText) findViewById(R.id.dueDate);
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month +=1;
                        dueDate.setText(year+"-"+month+"-"+day);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        dueTime = (EditText) findViewById(R.id.dueTimeText);
        dueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        dueTime.setText(hour+":"+minute);
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();
            }
        });
        submitButton = (AppCompatButton) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour,minute,year,month,day;
                db = new DBConfig(AddTask.this);
                task = taskText.getText().toString();
                note = noteText.getText().toString();
                category = categoryText.getText().toString();

                dueDateTime = dueDate.getText().toString()+" "+dueTime.getText().toString();
                if(task.isEmpty()){
                    Toast.makeText(AddTask.this, "Fill Empty Field", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        ToDoModels tasks = new ToDoModels();
                        tasks.setTask(task);
                        tasks.setNote(note);
                        tasks.setStatus(0);
                        tasks.setDue_date(dueDateTime);
                        tasks.setCategory(category);
                        check_tasks = db.getAllTasks();
                        for(ToDoModels item:check_tasks){
                            if(item.getDue_date().toLowerCase().contains(dueDateTime)){
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddTask.this).
                                        setTitle("Conflicting Schedule").
                                        setMessage("Are you sure you want to set this schedule? ").
                                        setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                db.addTask(tasks);
                                                Toast.makeText(AddTask.this, "Successfully Add Task", Toast.LENGTH_SHORT).show();
                                                MainActivity.main.refreshTodos();
                                                MainActivity.main.loadSpinnerCategory();
                                                finish();
                                            }
                                        }).
                                        setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                return;
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                            }
                        }
                        try{
                            Calendar cal = Calendar.getInstance();
//                        split due date and due time editext
                            String[] dueTimeSplit = dueTime.getText().toString().split(":");
                            String[] dueDateSplit = dueDate.getText().toString().split("-");

//                        insert each of splitted string to variable
                            hour = Integer.parseInt(dueTimeSplit[0]);
                            minute = Integer.parseInt(dueTimeSplit[1]);
                            year = Integer.parseInt(dueDateSplit[0]);
                            month = Integer.parseInt(dueDateSplit[1]);
                            day = Integer.parseInt(dueDateSplit[2]);

//                        set datetime for alarm
                            cal.set(Calendar.YEAR,year);
                            cal.set(Calendar.MONTH,month-1);
                            cal.set(Calendar.DAY_OF_MONTH,day);
                            cal.set(Calendar.HOUR_OF_DAY,hour);
                            cal.set(Calendar.MINUTE,minute);
                            cal.set(Calendar.SECOND,0);
                            cal.set(Calendar.MILLISECOND,0);
//                        set alarm
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent i = new Intent(AddTask.this,AlarmReceiver.class);
                            i.putExtra("taskName",task);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTask.this,0,i,PendingIntent.FLAG_MUTABLE);
                                alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
                            }else{
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTask.this,0,i,PendingIntent.FLAG_ONE_SHOT);
                                alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
                            }
                        }catch (Exception e){
                            Log.d("error", String.valueOf(e));
                        }

//                        add task to db
                        db.addTask(tasks);
                        Toast.makeText(AddTask.this, "Successfully Add Task", Toast.LENGTH_SHORT).show();
                        MainActivity.main.refreshTodos();
                        MainActivity.main.loadSpinnerCategory();
                        finish();

                    }catch (Exception e){
                        Toast.makeText(AddTask.this, "Error Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}