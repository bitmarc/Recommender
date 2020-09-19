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
    private MutableLiveData<Boolean> flag;
    User user;


    public ProfileViewModel(User user) {
        this.user = user;
        username = new MutableLiveData<>();
        personName = new MutableLiveData<>();
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        userLive = new MutableLiveData<>();
        flag = new MutableLiveData<>();
        flag.setValue(false);
    }

    public LiveData<Boolean> getPbarValue(){
        return flag;
    }

    public LiveData<User> getCurrentUser(){
        new getDataUserInBackground().execute(user);
        return userLive;
    }

    public void updateUser(User newUser){
        new updateDataUserInBackground().execute(newUser);
    }

    //para refresacar los datos mostrados se ejecuta al cambiar datos del formulario y ya sea que se guarden o no
    public void refresh(){
        new getDataUserInBackground().execute(user);
    }

    //este metodo se ejecuta al finalizar el proceso en segundo plano de la obtencion de datos (getDataUserInBackground)
    public void updateCurrentDataUser(User user){
        userLive.setValue(user);
        //this.user.setId(user.getId()); // para guardar el id de usuario -- en cuanto maneje session no será necesario
    }

    // se ejecuta despues de editar datos (updateDataUserInBackground)
    private void updateThisUser(User user){
        //this.user=user;
        // Cuando maeje el cambio de contraseña, se deberá manejar diferente
        User newActualUser = new User(user.getUsername(),this.user.getPassword(),user.getPersonname(),user.getEmail());
        this.user=newActualUser;
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
            ConnectionManager cm = new ConnectionManager(user.getUsername(),user.getPassword());
            try {
                userT=cm.getDataUser(users[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userT;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            flag.setValue(false);
            updateCurrentDataUser(user);
        }
    }

    class updateDataUserInBackground extends AsyncTask<User, Void, User>{

        @Override
        protected User doInBackground(User... users) {
            User userT=new User();
            ConnectionManager cm = new ConnectionManager(user.getUsername(),user.getPassword());
            try {
                userT=cm.updateDataUser(users[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userT;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            updateThisUser(user); //actualizar usuario en la clase y main
        }
    }



}