package com.example.recommender.data;

import com.example.recommender.Interface.JsonApi;
import com.example.recommender.User;
import com.example.recommender.data.model.LoggedInUser;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private String usuario1="juana";
    private String password1="123456";
    private int codigoerror=111;

    public Result<LoggedInUser> login(String username, String password) {
/*        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.111:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<User> call = jsonApi.getUser(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    codigoerror=response.code();
                }else{
                    User resUser = response.body();

                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("hubo un fallo!!");
                setUserNamme(t.getMessage());
            }
        });*/
        try {
                // TODO: handle loggedInUser authentication
            if ((username.equals(usuario1)) && (password.equals(password1))) {
                //java.util.UUID.randomUUID().toString()
                LoggedInUser fakeUser = new LoggedInUser("6","Juana V.");
                return new Result.Success<>(fakeUser);

            }else {
                try {

                    if (codigoerror==111)
                        throw new MiExcepcion(111);
                    else
                        throw new MiExcepcion(222);
                } catch (MiExcepcion ex) {
                    return new Result.Error(new IOException(ex.getMessage(), ex));
                }
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }

    }

    public void logout() {
        // TODO: revoke authentication
    }

    public class MiExcepcion extends Exception{

        private int codigoError;

        public MiExcepcion(int codigoError){
            super();
            this.codigoError=codigoError;
        }

        @Override
        public String getMessage(){

            String mensaje="";

            switch(codigoError){
                case 111:
                    mensaje="usuario o contraseña invalidos";
                    break;
                case 222:
                    mensaje="has ingresado una contraseña antigua";
                    break;
                case 333:
                    mensaje="usuario bloqueado";
                    break;
            }

            return mensaje;

        }

    }

    public void validateUser(){

    }
}