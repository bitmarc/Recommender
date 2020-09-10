package com.example.recommender.ui.home;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recommender.R;
import com.example.recommender.User;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.ui.login.LoginViewModel;

import java.io.IOException;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> appTitle;
    private MutableLiveData<String> msgWellcome;
    private User user;

    public HomeViewModel(User user) {
        this.user=user;
        appTitle = new MutableLiveData<>();
        msgWellcome = new MutableLiveData<>();
        msgWellcome.setValue("null");
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
        msgWellcome.setValue("Bienvenido "+userPersonName);
    }



    class updateNameInBackground extends AsyncTask<User, Void, String>{
        @Override
        protected String doInBackground(User... users) {
            String userPersonName="Error 2";
            ConnectionManager cm = new ConnectionManager();
            try {
                userPersonName=cm.getUserPersonNAme(users[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userPersonName;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateCurrentName(s);
        }
    }
}