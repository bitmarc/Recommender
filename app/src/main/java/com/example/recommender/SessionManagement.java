package com.example.recommender;

import android.content.Context;
import android.content.SharedPreferences;
    // clase destinada a manejar el guardado de la sesión implementando shared preferences.
public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String SHARED_PREF_NAME = "session";

    String SESSION_KEY = "session_user";// id
    String USERNAME = "user_name";      // nombre usuario
    String PERSONANME = "person_name";  // nombre persona
    String USERPASS = "user_password";  // contraseña

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        //save session of user whenever user is logged in
        editor.putInt(SESSION_KEY,user.getId()).commit();
        editor.putString(USERNAME,user.getUsername()).commit();
        editor.putString(PERSONANME,user.getPersonname()).commit();
        editor.putString(USERPASS,user.getPassword());
    }

    public int getSession(){
        //return user id whose session is saved
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }
    public User getUser(){
        User usuario = new User(sharedPreferences.getInt(SESSION_KEY, -1),sharedPreferences.getString(USERNAME, "UNKNOW"),
                sharedPreferences.getString(USERPASS,"UNKNOW"),sharedPreferences.getString(PERSONANME,"UNKNOW"));
        //return user id whose session is saved
        return usuario;
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
        System.out.println("sesion removida::ok");
    }
}
