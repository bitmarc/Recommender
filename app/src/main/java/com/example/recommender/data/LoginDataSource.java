package com.example.recommender.data;

import com.example.recommender.Interface.JsonApi;
import com.example.recommender.Interface.MyCallback;
import com.example.recommender.User;
import com.example.recommender.connection.ConnectionManager;
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
    private User user;
    private String usuario1="juana";
    private String password1="123456";
    LoggedInUser userLoggedIn;
    private int codigoerror=111;

    public Result<LoggedInUser> login(String username, String password) throws IOException {
        ConnectionManager cm = new ConnectionManager();
        User usuario= cm.logInUser(username, password);
        if(!usuario.getId().equals("0")){
            System.out.print("exito usuario encontrado y pasword correcto");
            LoggedInUser userAdded = new LoggedInUser(usuario.getId());
            return new Result.Success<>(userAdded);
        }else{
            System.out.print("error usuario no encontrado o password incorrecto");
            try {

                if (codigoerror==111)
                    throw new MiExcepcion(111);
                else
                    throw new MiExcepcion(222);
            } catch (MiExcepcion ex) {
                return new Result.Error(new IOException(ex.getMessage(), ex));
            }
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