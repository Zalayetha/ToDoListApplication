package com.example.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolistapplication.models.ToDoModels;

import java.util.Calendar;
import java.util.Objects;

public class UpdateTask extends AppCompatActivity {
    AppCompatButton backButton,submitButton;
    EditText dueDate,dueTime,taskText,noteText;
    private int mYear,mMonth,mDay,mHour,mMinute;
    String task,note,dueDateTime;
    DBConfig db;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        Objects.requireNonNull(getSupportActionBar()).hide();
        extras = getIntent().getExtras();
        taskText = (EditText)findViewById(R.id.taskEditText);
        noteText = (EditText)findViewById(R.id.noteEditText);
        dueDate = (EditText)findViewById(R.id.dueDate);
        dueTime = (EditText) findViewById(R.id.dueTimeText);

        String[] dueDateTime_split = extras.getString("due_date").split(" ");

        taskText.setText(extras.getString("task"));
        noteText.setText(extras.getString("note"));
        if(dueDateTime_split.length == 2){
            dueDate.setText(dueDateTime_split[0]);
            dueTime.setText(dueDateTime_split[1]);
        }else if(dueDateTime_split.length == 1){
            dueDate.setText(dueDateTime_split[0]);
        }
        backButton = (AppCompatButton) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateTask.this,MainActivity.class);
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        dueTime.setText(hour+":"+minute+":00");
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();
            }
        });
        submitButton = (AppCompatButton) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DBConfig(UpdateTask.this);
                task = taskText.getText().toString();
                note = noteText.getText().toString();
                dueDateTime = dueDate.getText().toString()+" "+dueTime.getText().toString();
                if(task.isEmpty()){
                    Toast.makeText(UpdateTask.this, "Fill Empty Field", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        ToDoModels tasks = new ToDoModels();
                        tasks.setTask(task);
                        tasks.setNote(note);
                        tasks.setStatus(0);
                        tasks.setDue_date(dueDateTime);
                        int id = extras.getInt("id");
                        db.updateTask(id,tasks);
                        Toast.makeText(UpdateTask.this, "Successfully Update Task", Toast.LENGTH_SHORT).show();
                        MainActivity.main.refreshTodos();
//                        Intent i = new Intent(UpdateTask.this,MainActivity.class);
//                        startActivity(i);
                        finish();
                    }catch (Exception e){
                        Toast.makeText(UpdateTask.this, "Error Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}