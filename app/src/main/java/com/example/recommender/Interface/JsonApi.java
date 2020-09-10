package com.example.recommender.Interface;

import com.example.recommender.User;
import com.example.recommender.retrofit.models.UserLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApi {
    @GET("users")
    Call<User> getUser();

    @POST("signUp")
    Call<User>createUser(@Body User user);

    @GET("signUp/user/{name}")
    Call<String>checkUser(@Path("name")String username);

    @POST("logIn")
    Call<User>logInUser(@Body User user);

    @POST("user")
    Call<User>getDataUser(@Body User user);

}
