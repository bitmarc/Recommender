package com.example.recommender.ui.signUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import android.view.KeyEvent;
import com.example.recommender.Interface.MyCallback;
import com.example.recommender.entities.Message;
import com.example.recommender.R;
import com.example.recommender.retrofit.models.UserResponse;
import com.example.recommender.connection.ConnectionManager;


public class SignupActivity extends AppCompatActivity {
    private SignupViewModel signupViewModel;
    private Button signupButton;
    private EditText persoNameEditText;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordRepEditText;
    private ProgressBar pbar;
    ConnectionManager cm;


    // clase que maneja el registro de un nuevo usuario.
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
        this.pbar= findViewById(R.id.progressBarSignup);
        cm = new ConnectionManager();
        listener();
    }

    private void listener() {
        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);

        signupViewModel.getSignupFormState().observe(this, new Observer<SignupFormState>() {
            @Override
            public void onChanged(SignupFormState signupFormState) {
                if (signupFormState == null) {
                    return;
                }
                signupButton.setEnabled(signupFormState.isDataValid());
                if (signupFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(signupFormState.getUsernameError()));
                }
                if (signupFormState.getEmailError() != null){
                    emailEditText.setError(getString(signupFormState.getEmailError()));
                }
                if (signupFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(signupFormState.getPasswordError()));
                }
                if (signupFormState.getPasswordRepeatError() != null){
                    passwordRepEditText.setError(getString(signupFormState.getPasswordRepeatError()));
                }
            }
        });


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
                signupViewModel.signupDataChanged(getApplicationContext(), usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),emailEditText.getText().toString(), passwordRepEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordRepEditText.addTextChangedListener(afterTextChangedListener);
        passwordRepEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //signupViewModel.signup(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    verificar();
                }
                return false;
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificar();
            }
        });
    }

    private void verificar(){
        pbar.setVisibility(View.VISIBLE);
        cm.checkUser(usernameEditText.getText().toString(), new MyCallback(){
            @Override
            public void onDataGot(Message message){
                if(message.getMessage().equals("El usuario ya existe")){
                    pbar.setVisibility(View.GONE);
                    usernameEditText.setError(getString(R.string.existing_username));
                }else{
                    cm.addUser(usernameEditText, passwordEditText, persoNameEditText, emailEditText, new MyCallback() {
                        @Override
                        public void onDataGot(Message message) {
                        }
                        @Override
                        public void onUserAddedGot(UserResponse usuarR) {
                            if(usuarR.getMessage().equals("Usuario agregado satisfactoriamente")){
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result",usuarR.getUser().getUsername());
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                            }else
                               System.out.println(usuarR.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onUserAddedGot(UserResponse usuarR) {
            }
        });

    }

}