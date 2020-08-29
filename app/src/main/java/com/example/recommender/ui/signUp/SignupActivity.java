package com.example.recommender.ui.signUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.view.KeyEvent;

import com.example.recommender.Interface.JsonApi;
import com.example.recommender.R;
import com.example.recommender.retrofit.models.UserLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {
    private SignupViewModel signupViewModel;
    private Button signupButton;
    private Button petB;
    private EditText persoNameEditText;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordRepEditText;
    private String userNamme;


    // clase que maneja el registro de un nuevo usuario.
    // * Falta los metodos de verificacion de validadcion de datos y conexion con servidor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.signupButton = findViewById(R.id.signupLaunch);
        this.petB= findViewById(R.id.petB);
        this.persoNameEditText = findViewById(R.id.editTextTextPersonName);
        this.usernameEditText = findViewById(R.id.editTextTextUserName);
        this.emailEditText = findViewById(R.id.editTextTextEmailAddress);
        this.passwordEditText = findViewById(R.id.editTextTextPassword);
        this.passwordRepEditText = findViewById(R.id.editTextTextPasswordRpeat);
        listener();
    }

    private void listener() {
        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                signupViewModel.signupDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };


        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signupViewModel.signup(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", userNamme);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
                return false;
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",userNamme);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        petB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUsername();
            }
        });

    }

    public void getUsername(){
        System.out.println("Entro a getUsername");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.18:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<List<UserLog>> call = jsonApi.getUser();
        call.enqueue(new Callback<List<UserLog>>() {
            @Override
            public void onResponse(Call<List<UserLog>> call, Response<List<UserLog>> response) {
                if(!response.isSuccessful()){
                    setUserNamme("error, codigo:  "+response.code());
                    System.out.println("respuesta no exitosa");
                    return;
                }else{
                    System.out.println("respuesta exitosa");
                    List<UserLog> userList = response.body();
                    for(UserLog userLog: userList){
                        setUserNamme(userLog.getUsername());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<UserLog>> call, Throwable t) {
                System.out.println("hubo un fallo!!");
                setUserNamme(t.getMessage());
            }
        });
    }


    public String getUserNamme() {
        return userNamme;
    }

    public void setUserNamme(String userNamme) {
        this.userNamme = userNamme;
    }



}