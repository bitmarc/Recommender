package com.example.recommender.data;

import com.example.recommender.retrofit.models.UserResponse;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private int codigoerror=111;

    public Result<LoggedInUser> login(String username, String password, String mac) throws IOException {
        ConnectionManager cm = new ConnectionManager();
        UserResponse userR= cm.logInUser(username, password, mac);
        if(!userR.getMessage().equals("Error de autenticación")){
            System.out.print("exito usuario encontrado y pasword correcto");
            LoggedInUser userAdded = new LoggedInUser(userR.getUser().getId());
            return new Result.Success<>(userAdded);
        }else{
            System.out.print(userR.getMessage());
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