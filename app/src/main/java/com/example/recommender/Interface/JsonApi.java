package com.example.recommender.Interface;

import com.example.recommender.entities.Message;
import com.example.recommender.entities.Recommendation;
import com.example.recommender.entities.User;
import com.example.recommender.retrofit.models.RecommendationRequest;
import com.example.recommender.retrofit.models.UserResponse;
import com.example.recommender.form.Form;
import com.example.recommender.ui.recommendations.RecomResult;

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
    Call<UserResponse>getDataUser(@Body User user);

    @GET("wellcome")
    Call<Message>getPersonName();

    @PATCH("user")
    Call<UserResponse>updateDataUser(@Body User user);

    @GET("form")
    Call<Form> getform();

    @POST("recom")
    Call<Recommendation> getRecom(@Body RecommendationRequest Rreq);
}
