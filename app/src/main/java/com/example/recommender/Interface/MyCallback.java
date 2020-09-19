package com.example.recommender.Interface;

import com.example.recommender.Message;
import com.example.recommender.User;
import com.example.recommender.UserResponse;

public interface MyCallback {
    void onDataGot(Message number);
    void onUserAddedGot(UserResponse userR);
}
