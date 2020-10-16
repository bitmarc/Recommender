package com.example.recommender.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.recommender.entities.User;

/*Clase necesaria con el proposito de pasar parametros como argumentos
 de la calse view model */

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private User user;

    public HomeViewModelFactory(User user){
        this.user=user;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeViewModel(user);
    }
}
