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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recommender.R;

public class SignupActivity extends AppCompatActivity {
    private SignupViewModel signupViewModel;
    public String username="nuevo";
    private Button signupButton;
    private EditText persoNameEditText;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordRepEditText;


    // clase que maneja el registro de un nuevo usuario.
    // * Falta los metodos de verificacion de validadcion de datos y conexion con servidor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.signupButton = findViewById(R.id.signupLaunch);
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
                    returnIntent.putExtra("result", getUsername());
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
                returnIntent.putExtra("result", getUsername());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }

    public String getUsername(){
        return this.username;
    }


/*    public void signup1 (View view){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", this.username);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }*/


}