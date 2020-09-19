package com.example.recommender.ui.home;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recommender.User;
import com.example.recommender.connection.ConnectionManager;

import java.io.IOException;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> appTitle;
    private MutableLiveData<String> msgWellcome;
    private MutableLiveData<Boolean> flag;
    private User user;

    public HomeViewModel(User user) {
        this.user=user;
        appTitle = new MutableLiveData<>();
        msgWellcome = new MutableLiveData<>();
        msgWellcome.setValue("");
        flag = new MutableLiveData<>();
        flag.setValue(false);
    }

    public LiveData<Boolean> getPbarValue(){
        return flag;
    }

    public LiveData<String> getTitle() {
        appTitle.setValue("RecomendAuto");
        return appTitle;
    }
    public LiveData<String> getCurrentName() {
        new updateNameInBackground().execute(user);
        return msgWellcome;
    }

    public void updateCurrentName(String userPersonName){
        msgWellcome.setValue(userPersonName);
    }



    class updateNameInBackground extends AsyncTask<User, Void, String>{
        @Override
        protected void onPreExecute() {
            flag.setValue(true);
        }

        @Override
        protected String doInBackground(User... users) {
            String userPersonName="[Error al enviar la solicitud]";
            ConnectionManager cm = new ConnectionManager(users[0].getUsername(),users[0].getPassword());
            try {
                userPersonName=cm.getUserPersonNAme();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userPersonName;
        }

        @Override
        protected void onPostExecute(String s) {
            flag.setValue(false);
            updateCurrentName(s);
        }
    }
}