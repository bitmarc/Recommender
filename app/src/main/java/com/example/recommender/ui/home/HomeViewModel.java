package com.example.recommender.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> appTitle;

    public HomeViewModel() {
        appTitle = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        appTitle.setValue("RecomendAuto");
        return appTitle;
    }
}