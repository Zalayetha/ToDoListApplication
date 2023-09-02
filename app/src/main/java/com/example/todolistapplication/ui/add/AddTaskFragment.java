package com.example.todolistapplication.ui.add;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistapplication.database.Todo;
import com.example.todolistapplication.util.Utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.todolistapplication.R;
import com.example.todolistapplication.databinding.FragmentAddTaskBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddTaskFragment extends Fragment {

    private AddTaskViewModel addTaskViewModel;
    private FragmentAddTaskBinding fragmentAddTaskBinding;
    private Todo todo;
    private String taskText,noteText,dateText,timeText,categoryText;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragmentAddTaskBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_task,container,false);
        addTaskViewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);
        fragmentAddTaskBinding.setAddTaskFragmentViewModel(addTaskViewModel);
        addTaskViewModel.init();
        addTaskViewModel.getTask().observe(getViewLifecycleOwner(),s -> {
//            addTaskViewModel.setTask(s);
        });
        addTaskViewModel.getAddButtonClicked().observe(getViewLifecycleOwner(),aBoolean -> {
            if(aBoolean == true){
                addTaskViewModel.addTaskToDatabase().
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread());
                addTaskViewModel.onDoneAddButtonClicked();
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
            fragmentAddTaskBinding.editTextDate.setText(materialDatePicker.getHeaderText());
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
            fragmentAddTaskBinding.editTextTime.setText(materialTimePicker.getHour()+":"+materialTimePicker.getMinute());
        });
    }
}