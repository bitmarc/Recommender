package com.example.recommender.connection;

import android.widget.EditText;

import com.example.recommender.Interface.JsonApi;
import com.example.recommender.Interface.MyCallback;
import com.example.recommender.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionManager {
    private Retrofit retrofit;
    private JsonApi jsonApi;
    //private Context context;

    public ConnectionManager(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.111:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        //this.context=context;
    }


    public void checkUser(String userName, final MyCallback callback){
        final String[] resp = new String[1];
        Call<String> call = jsonApi.checkUser(userName);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    System.out.println("respuesta no exitosa");
                    return;
                }
                resp[0] =response.body();
                callback.onDataGot(resp[0]);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("hubo un fallo!!");
            }
        });
    }

    public User logInUser(String userName, String password) throws IOException {
        final User[] user = {new User()};
        user[0].setUsername(userName);
        user[0].setPassword(password);
        Call<User> call = jsonApi.logInUser(user[0]);
        user[0]=call.execute().body();
        return user[0];
    }


    public void addUser(EditText usernameEditText, EditText passwordEditText, EditText persoNameEditText, EditText emailEditText, final MyCallback callback){
        final User[] usuario = {new User(usernameEditText.getText().toString(), passwordEditText.getText().toString(),
                persoNameEditText.getText().toString(), emailEditText.getText().toString())};
        Call<User> call = jsonApi.createUser(usuario[0]);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    System.out.println("respuesta no exitosa");
                    return;
                }
                    usuario[0] = response.body();
                    callback.onUserAddedGot(usuario[0]);
                    System.out.println("respuesta exitosa com: codigo: "+response.code());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("hubo un fallo!!");
            }
        });
    }



}
