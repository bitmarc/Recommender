package com.example.recommender.connection;

import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.recommender.Interface.JsonApi;
import com.example.recommender.Interface.MyCallback;
import com.example.recommender.Message;
import com.example.recommender.User;
import com.example.recommender.UserResponse;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionManager {
    private Retrofit retrofit;
    private JsonApi jsonApi;
    //private Context context;

    public ConnectionManager(String username, String password){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new BasicAuthInterceptor(username, password))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.111:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonApi = retrofit.create(JsonApi.class);
    }

    public ConnectionManager(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.111:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonApi = retrofit.create(JsonApi.class);
    }


    public void checkUser(String userName, final MyCallback callback){
        final Message[] resp = new Message[1];
        Call<Message> call = jsonApi.checkUser(userName);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(!response.isSuccessful()){
                    return;
                }
                resp[0] =response.body();
                callback.onDataGot(resp[0]);
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
            }
        });
    }

    public UserResponse logInUser(String userName, String password, String mac) throws IOException {
        User user = new User();
        UserResponse userR;
        user.setId(mac);
        user.setUsername(userName);
        user.setPassword(password);
        Call<UserResponse> call = jsonApi.logInUser(user);
        userR=call.execute().body();
        return userR;
    }

    public void addUser(EditText usernameEditText, EditText passwordEditText, EditText persoNameEditText, EditText emailEditText, final MyCallback callback){
        User usuario = new User(usernameEditText.getText().toString(), passwordEditText.getText().toString(),
                persoNameEditText.getText().toString(), emailEditText.getText().toString());
        final UserResponse[] userR = {new UserResponse()};
        Call<UserResponse> call = jsonApi.createUser(usuario);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }
                userR[0] = response.body();
                callback.onUserAddedGot(userR[0]);
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                System.out.println("hubo un fallo!!");
            }
        });
    }

    public String getUserPersonNAme() throws IOException {
        Message message;
        Call<Message> call = jsonApi.getPersonName();
        Response<Message> response=call.execute();
        if (response.isSuccessful()) {
            message=response.body();
            return message.getMessage();
        }
        else {
            return "[Acceso no autorizado]: "+response.code();
        }
    }

    public User getDataUser(User user) throws IOException {
        final User[] fakeUser = {new User()};
        Call<User> call = jsonApi.getDataUser(user);
        Response<User> response=call.execute();
        if(response.isSuccessful()) {
            fakeUser[0]=response.body();
            return fakeUser[0];
        }
        else {
            fakeUser[0].setPersonname("error: "+response.code());
            fakeUser[0].setUsername("error: "+response.code());
            fakeUser[0].setEmail("error: "+response.code());
            fakeUser[0].setPassword("error: "+response.code());
            fakeUser[0].setId("0");
            return fakeUser[0];
        }
        //fakeUser[0]=call.execute().body();
        //return fakeUser[0];
    }

    public User updateDataUser(User user) throws IOException {
        User fakeUser = new User();
        Call<User> call = jsonApi.updateDataUser(user);
        fakeUser = call.execute().body();
        return fakeUser;
    }


}
