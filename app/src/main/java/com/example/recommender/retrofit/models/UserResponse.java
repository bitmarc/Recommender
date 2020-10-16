package com.example.recommender.retrofit.models;

import com.example.recommender.entities.User;

public class UserResponse {
    private User user;
    private String message;

    public UserResponse() {
        this.message="";
        this.user = new User("","","","");
    }

    public UserResponse(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
