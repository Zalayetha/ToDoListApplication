package com.example.todolistapplication.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

//        observe onNavigatedToAddTask
        final Observer<Boolean> onNavigatedToAddObserver = aBoolean -> {
            if(aBoolean == true){
                Navigation.findNavController(getView()).navigate(TaskFragmentDirections.actionTaskFragment2ToAddTaskFragment3());
                taskFragmentViewModel.doneNavigatedToAdd();
            }
        };
        taskFragmentViewModel.getNavigatedToAdd().observe(getViewLifecycleOwner(),onNavigatedToAddObserver);
        return fragmentTaskBinding.getRoot();
    }
}
