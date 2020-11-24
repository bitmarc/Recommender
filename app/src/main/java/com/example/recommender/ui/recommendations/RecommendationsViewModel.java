package com.example.recommender.ui.recommendations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecommendationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Boolean> falgForm; //formulario

    public RecommendationsViewModel() { //CONSTRUCTOR
        falgForm = new MutableLiveData<>();
        falgForm.setValue(false);
    }


    public LiveData<Boolean> SetUI() {
        return falgForm;
    }

    public void changeUI(boolean var){
        this.falgForm.setValue(var);
    }
}