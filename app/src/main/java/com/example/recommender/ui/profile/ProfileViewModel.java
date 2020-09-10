package com.example.recommender.ui.profile;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recommender.User;
import com.example.recommender.connection.ConnectionManager;

import java.io.IOException;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> userLive;
    private MutableLiveData<String> username;
    private MutableLiveData<String> personName;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    User user;


    public ProfileViewModel(User user) {
        this.user = user;
        username = new MutableLiveData<>();
        personName = new MutableLiveData<>();
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        userLive = new MutableLiveData<>();
    }

    public LiveData<User> getCurrentUser(){
        new updateDataUserInBackground().execute(user);
        return userLive;
    }


    public void updateCurrentDataUser(User user){
        userLive.setValue(user);
    }

    class updateDataUserInBackground extends AsyncTask<User, Void, User>{
        @Override
        protected User doInBackground(User... users) {
            User user=new User();
            ConnectionManager cm = new ConnectionManager();
            try {
                user=cm.getDataUser(users[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            updateCurrentDataUser(user);
        }
    }



}