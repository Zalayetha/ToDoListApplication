package com.example.todolistapplication.ui.list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskFragmentViewModel extends ViewModel {
    private MutableLiveData<String> currentText;

    public void init(){
        currentText = new MutableLiveData<String>();
        setCurrentText("Miya Atsumu");
    }

    public MutableLiveData<String> getCurrentText(){
        return currentText;
    }
    private void setCurrentText(String currentText) {
        this.currentText.setValue(currentText);
    }

}
