package com.example.recommender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    public User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currentUser=new User();
        getSession();
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recommendations, R.id.navigation_history, R.id.navigation_profile).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void logout(View view){
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        Intent activityIntent = new Intent(MainActivity.this, PreStartActivity.class);
        startActivity(activityIntent);
        finish();
    }

    public void getSession(){
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        this.currentUser=sessionManagement.getUser();
    }

    public User getUser() {
        return  currentUser;
    }

    public void setUser(User user){ // ver fragment profile
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.updateSession(user);
        this.currentUser=sessionManagement.getUser();
    }

}