package com.example.recommender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.recommender.ui.login.LoginActivity;

public class PreStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);         // Esta actividad no tiene vista, se usa para
        startActivity(checkSession());              // verificar si hay registro de inicio de sessión
        finish();                                   // en el dispositivo, y redirecciona a la actividad
    }                                               // correspondiente.

    private Intent checkSession() {
        //check if user is logged in
        SessionManagement sessionManagement = new SessionManagement(PreStartActivity.this);
        String userID = sessionManagement.getSession();

        if(userID.equals("-1")){// usuario loggeado
            System.out.println("User id : "+userID);
            return moveToLogin();
        }
        else{                                      // usuario no legeado
            return moveToMainActivity();
        }
    }

    private Intent moveToMainActivity() {
        return new Intent(this, MainActivity.class);
    }
    private Intent moveToLogin() {
        return new Intent(this, LoginActivity.class);
    }

}