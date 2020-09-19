package com.example.recommender.Interface;

import com.example.recommender.Message;
import com.example.recommender.User;
import com.example.recommender.UserResponse;
import com.example.recommender.retrofit.models.UserLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApi {
    @GET("users")
    Call<User> getUser();

    @POST("signUp")
    Call<UserResponse>createUser(@Body User user);

    @GET("signUp/user/{name}")
    Call<Message>checkUser(@Path("name")String username);

    @POST("logIn")
    Call<UserResponse>logInUser(@Body User user);

    @POST("user")
    Call<User>getDataUser(@Body User user);

    @GET("wellcome")
    Call<Message>getPersonName();

    @PATCH("user")
    Call<User>updateDataUser(@Body User user);

}
