package com.example.recommender.data;

import com.example.recommender.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private String usuario1="juana";
    private String password1="123456";

    public Result<LoggedInUser> login(String username, String password) {

        try {
                // TODO: handle loggedInUser authentication
            if ((username.equals(usuario1)) && (password.equals(password1))) {
                //java.util.UUID.randomUUID().toString()
                LoggedInUser fakeUser = new LoggedInUser(6,"Juana V.");
                return new Result.Success<>(fakeUser);
            }else {
                int codigoerror=111;
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
}