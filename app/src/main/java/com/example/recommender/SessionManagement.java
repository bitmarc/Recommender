package com.example.recommender;

import android.content.Context;
import android.content.SharedPreferences;
    // clase destinada a manejar el guardado de la sesión implementando shared preferences.
public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String SHARED_PREF_NAME = "session";

    String SESSION_KEY = "session_user";// id
    String USERNAME = "user_name";
    String PASSWORD = "password_user";


    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        //save session of user whenever user is logged in
        editor.putString(SESSION_KEY,user.getId()).commit();
        editor.putString(USERNAME,user.getUsername()).commit();
        editor.putString(PASSWORD,user.getPassword()).commit();
    }

    public String getSession(){
        return sharedPreferences.getString(SESSION_KEY, "-1"); //era usernmae
    }

    public User getUser(){
        User usuario = new User();
        usuario.setId(sharedPreferences.getString(SESSION_KEY, "-1"));
        usuario.setUsername(sharedPreferences.getString(USERNAME, "-1"));
        usuario.setPassword(sharedPreferences.getString(PASSWORD, "-1"));
        //return username whose session is saved
        return usuario;
    }

    public void removeSession(){
        editor.putString(SESSION_KEY,"-1").commit();
        editor.putString(USERNAME, "-1").commit();
        editor.putString(PASSWORD, "-1").commit();
    }

    public void updateSession(User user){ // cuando maneje la sesssion se hará differente
        //save session of user whenever user is logged in
        editor.putString(USERNAME,user.getUsername()).commit();
        editor.putString(SESSION_KEY,user.getId()).commit();
        editor.putString(PASSWORD, user.getPassword()).commit();
    }
}
