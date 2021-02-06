package com.example.recommender.connection;

import android.widget.EditText;

import com.example.recommender.Interface.JsonApi;
import com.example.recommender.Interface.MyCallback;
import com.example.recommender.entities.Automobile;
import com.example.recommender.entities.Datasheet;
import com.example.recommender.entities.Message;
import com.example.recommender.entities.Recommendation;
import com.example.recommender.entities.User;
import com.example.recommender.retrofit.models.History;
import com.example.recommender.retrofit.models.RecommendationRequest;
import com.example.recommender.retrofit.models.UserResponse;
import com.example.recommender.form.Form;

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
    private String base_Url="http://192.168.0.104:5000/";


    public ConnectionManager(String username, String password){ //usada para cuando se requiere autenticacion en la cabecera http
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new BasicAuthInterceptor(username, password))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonApi = retrofit.create(JsonApi.class);
    }

    public ConnectionManager(){ //sin autenticacion en cabecera de peticion http
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(base_Url)
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

    public UserResponse getDataUser(User user) throws IOException {
        final UserResponse[] fakeUserR = {new UserResponse()};
        User fakeUser = new User();
        Call<UserResponse> call = jsonApi.getDataUser(user);
        Response<UserResponse> response=call.execute();
        if(response.isSuccessful()) {
            fakeUserR[0]=response.body();
            return fakeUserR[0];
        }
        else {
            fakeUser.setPersonname("error: "+response.code());
            fakeUser.setUsername("error: "+response.code());
            fakeUser.setEmail("error: "+response.code());
            fakeUser.setPassword("error: "+response.code());
            fakeUser.setId("0");
            fakeUserR[0].setUser(fakeUser);
            return fakeUserR[0];
        }
    }

    public UserResponse updateDataUser(User user) throws IOException {
        UserResponse fakeUserR = new UserResponse();
        Call<UserResponse> call = jsonApi.updateDataUser(user);
        fakeUserR = call.execute().body();
        return fakeUserR;
    }

    public Form getUserForm() throws IOException {
        Form userForm = new Form();
        Call<Form> call = jsonApi.getform();
        userForm = call.execute().body();
        return userForm;
    }

    public Recommendation getRecommendation(RecommendationRequest Rreq) throws IOException {
        Recommendation resultRecommendation = new Recommendation();
        Call<Recommendation> call = jsonApi.getRecom(Rreq);
        resultRecommendation = call.execute().body();
        return resultRecommendation;
    }

    public History getHistory() throws IOException {
        History reqHistory = new History();
        Call<History> call = jsonApi.getHistory();
        reqHistory = call.execute().body();
        return reqHistory;
    }

    public Datasheet getDatasheet(Automobile auto) throws IOException {
        Datasheet datasheet = new Datasheet();
        Call<Datasheet> call = jsonApi.getDatasheet(auto);
        datasheet = call.execute().body();
        return datasheet;
    }
}
