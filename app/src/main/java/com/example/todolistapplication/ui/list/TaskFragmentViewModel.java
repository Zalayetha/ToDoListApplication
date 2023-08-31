package com.example.todolistapplication.ui.list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskFragmentViewModel extends ViewModel {
    private MutableLiveData<String> currentText;
    private MutableLiveData<Boolean> navigatedToAdd;
    public void init() {
        currentText = new MutableLiveData<String>();
        navigatedToAdd = new MutableLiveData<Boolean>();
        setCurrentText("Miya Atsumu");
    }
    public MutableLiveData<String> getCurrentText(){
        return currentText;
    }
    private void setCurrentText(String currentText) {
        this.currentText.setValue(currentText);
    }
    public MutableLiveData<Boolean> getNavigatedToAdd(){
        return  navigatedToAdd;
    }
    private void setNavigatedToAdd(Boolean onNavigatedToAdd) {
        this.navigatedToAdd.setValue(onNavigatedToAdd);
    }
    public void onNavigated(){
        setNavigatedToAdd(true);
    }
    public void doneNavigatedToAdd(){
        setNavigatedToAdd(false);
    }
}
