package com.example.recommender.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.recommender.User;

/*Clase necesaria con el proposito de pasar parametros como argumentos
 de la calse view model */

public class ProfileViewModelFactory implements ViewModelProvider.Factory {
    private User user;

    public ProfileViewModelFactory(User user){
        this.user=user;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileViewModel(user);
    }
}
