package com.example.recommender.Interface;

import com.example.recommender.entities.Message;
import com.example.recommender.retrofit.models.UserResponse;

public interface MyCallback {
    void onDataGot(Message number);
    void onUserAddedGot(UserResponse userR);
}
