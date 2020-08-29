package com.example.recommender.Interface;

import com.example.recommender.retrofit.models.UserLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonApi {
    @GET("users")
    Call<List<UserLog>> getUser();
}
