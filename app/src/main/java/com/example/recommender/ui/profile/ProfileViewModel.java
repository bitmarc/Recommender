package com.example.recommender.ui.profile;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recommender.entities.User;
import com.example.recommender.retrofit.models.UserResponse;
import com.example.recommender.connection.ConnectionManager;

import java.io.IOException;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> userLive;
    private MutableLiveData<String> username;
    private MutableLiveData<String> personName;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    private MutableLiveData<Boolean> flag;
    User user;
    User newUser;


    public ProfileViewModel(User user) {
        this.user = user;
        username = new MutableLiveData<>();
        personName = new MutableLiveData<>();
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        userLive = new MutableLiveData<>();
        flag = new MutableLiveData<>();
        flag.setValue(false);
        this.newUser= new User();
    }

    public LiveData<Boolean> getPbarValue(){
        return flag;
    }

    public LiveData<User> getCurrentUser(){
        new getDataUserInBackground().execute(user);
        return userLive;
    }

    public void updateUser(User newUser){
        this.newUser=newUser;
        new updateDataUserInBackground().execute(newUser);
    }

    //para refresacar los datos mostrados se ejecuta al cambiar datos del formulario y ya sea que se guarden o no
    public void refresh(){
        new getDataUserInBackground().execute(user);
    }

    //este metodo se ejecuta al finalizar el proceso en segundo plano de la obtencion de datos (getDataUserInBackground)
    public void updateCurrentDataUser(User user){
        user.setPassword(this.user.getPassword());
        userLive.setValue(user);
    }

    // se ejecuta despues de editar datos (updateDataUserInBackground)
    private void updateThisUser(User newUser){
        this.newUser.setId(newUser.getId());
        this.user=this.newUser;
        refresh();
    }

    class getDataUserInBackground extends AsyncTask<User, Void, User>{
        @Override
        protected void onPreExecute() {
            flag.setValue(true);
        }

        @Override
        protected User doInBackground(User... users) {
            User userT=new User("connection error","connection error","connection error","connection error");
            UserResponse userR;
            ConnectionManager cm = new ConnectionManager(user.getUsername(),user.getPassword());
            try {
                userR=cm.getDataUser(users[0]);
                userT=userR.getUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userT;
        }

        @Override
        protected void onPostExecute(User user) {
            flag.setValue(false);
            updateCurrentDataUser(user);
        }
    }

    class updateDataUserInBackground extends AsyncTask<User, Void, User>{
        @Override
        protected void onPreExecute() {
            flag.setValue(true);
        }

        @Override
        protected User doInBackground(User... users) {
            UserResponse userR;
            User userT=new User();
            ConnectionManager cm = new ConnectionManager(user.getUsername(),user.getPassword());
            try {
                userR=cm.updateDataUser(users[0]);
                if(userR.getMessage().equals("Usuario actualizado correctamente"))
                    userT=userR.getUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userT;
        }

        @Override
        protected void onPostExecute(User user) {
            flag.setValue(false);
            //user.setPassword(newUser.getPassword());
            //user.setId(newUser.getId());
            updateThisUser(user); //actualizar usuario en la clase y main
        }
    }



}