package com.example.recommender.Interface;

import com.example.recommender.User;

public interface MyCallback {
    void onDataGot(String number);
    void onUserAddedGot(User usuario);
}
