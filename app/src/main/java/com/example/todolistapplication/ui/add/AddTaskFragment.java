package com.example.todolistapplication.ui.add;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistapplication.AlarmReceiver;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.todolistapplication.R;
import com.example.todolistapplication.databinding.FragmentAddTaskBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddTaskFragment extends Fragment {

    private AddTaskViewModel addTaskViewModel;
    private FragmentAddTaskBinding fragmentAddTaskBinding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragmentAddTaskBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_task,container,false);
        addTaskViewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);
        fragmentAddTaskBinding.setAddTaskFragmentViewModel(addTaskViewModel);
        addTaskViewModel.init();
        addTaskViewModel.getTask().observe(getViewLifecycleOwner(),s -> {
        });
        addTaskViewModel.getAddButtonClicked().observe(getViewLifecycleOwner(),aBoolean -> {
            Log.d("add Button Clicked",aBoolean.toString()  );
            if(aBoolean == true){
                try{
                    addTaskViewModel.addTaskToDatabase().
                            subscribeOn(Schedulers.io()).
                            observeOn(AndroidSchedulers.mainThread());
                    setReminder();
                    addTaskViewModel.onDoneAddButtonClicked();
                    Toast.makeText(getContext(), "Menambahkan Data", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Log.d("Error Add",e.getMessage());
                }
            }
        });
        showDatePicker();
        showTimePicker();

        return fragmentAddTaskBinding.getRoot();
    }

    private void showDatePicker(){
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select The Date");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        fragmentAddTaskBinding.editTextDate.setOnClickListener(view -> {
            materialDatePicker.show(getParentFragmentManager(),"MATERIAL_DATE_PICKER");
        });

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            long selectedTimeStamp = (Long) selection;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", new Locale("id","ID"));
            String alarmReminderFormat = sdf.format(new Date(selectedTimeStamp));
            fragmentAddTaskBinding.editTextDate.setText(alarmReminderFormat);
        });
    }
    private void showTimePicker(){
        MaterialTimePicker.Builder materialTimeBuilder = new MaterialTimePicker.Builder()
                .setTitleText("Select Time")
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK);
        MaterialTimePicker materialTimePicker = materialTimeBuilder.build();
        fragmentAddTaskBinding.editTextTime.setOnClickListener(view -> {
            materialTimePicker.show(getParentFragmentManager(),"MATERIAL_TIME_PICKER");
        });
        materialTimePicker.addOnPositiveButtonClickListener(view -> {
            String formattedTime = getString(R.string.format_date,String.valueOf(materialTimePicker.getHour()),String.valueOf(materialTimePicker.getMinute()));
            fragmentAddTaskBinding.editTextTime.setText(formattedTime);
        });
    }

    private void setReminder(){
        String[] date = addTaskViewModel.getDate().getValue().split("/");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        String[] time = addTaskViewModel.getTime().getValue().split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,hour,minute);

        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("taskName",addTaskViewModel.getTask().getValue());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext()
                ,0
                , intent
                ,PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

    }
}