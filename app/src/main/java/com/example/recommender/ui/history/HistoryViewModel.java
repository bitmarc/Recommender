package com.example.recommender.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {

    private MutableLiveData<String> title;
    private MutableLiveData<Boolean> flag;

    public HistoryViewModel() {
        flag = new MutableLiveData<>();
        flag.setValue(true);
        title = new MutableLiveData<>();
        title.setValue("");
    }
    public void ChangeTitle(int Number){
        title.setValue(""+Number);
    }
    public LiveData<String> getTitle(){
        return title;
    }

    public LiveData<Boolean> getHistory() {
        return flag;
    }
}