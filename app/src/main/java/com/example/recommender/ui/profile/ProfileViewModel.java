package com.example.recommender.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recommender.User;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> usuario;
    User newUser;


    public ProfileViewModel() {
        newUser = new User("10","jhon12345","abc123", "Juan");
        newUser.setEmail("jhonconor@asd.com");
        newUser.setPersonname("Jhon Conor");
    }

    public LiveData<User> getText() {
        usuario = new MutableLiveData<>();
        getData();
        return usuario;
    }

    public void refresh(){
        User nuevo =new User("10","jhon12345","abc123","Juan");
        nuevo.setPersonname("Cambiado UNO"+Math.random()*6);
        nuevo.setEmail("jhonconor@asd.com");
        this.newUser=nuevo;
        System.out.println("se refresco");
        getData();
    }

    public void getData(){
        //conexion con base de datos y obtencion de datos
        usuario.setValue(newUser);
    }

}