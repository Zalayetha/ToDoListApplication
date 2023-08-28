package com.example.todolistapplication.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistapplication.R;
import com.example.todolistapplication.databinding.FragmentTaskBinding;

public class TaskFragment extends Fragment {
    private FragmentTaskBinding fragmentTaskBinding;
    private TaskFragmentViewModel taskFragmentViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentTaskBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_task,container,false);
        taskFragmentViewModel = new ViewModelProvider(this).get(TaskFragmentViewModel.class);
        fragmentTaskBinding.setTaskFragmentViewModel(taskFragmentViewModel);
        taskFragmentViewModel.init();
        fragmentTaskBinding.setLifecycleOwner(this);
        return fragmentTaskBinding.getRoot();
    }
}
