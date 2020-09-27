package com.example.recommender.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recommender.R;

public class PasswordChange extends AppCompatActivity {

    private Button bchangePass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        this.bchangePass = findViewById(R.id.idAceptar);

        bchangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("newpass","password");
                setResult(Activity.RESULT_OK, intent); // You can also send result without any data using setResult(int resultCode)
                finish();
            }
        });
    }



}