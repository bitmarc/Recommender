package com.example.recommender.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recommender.MainActivity;
import com.example.recommender.R;
import com.example.recommender.SessionManagement;
import com.example.recommender.ui.signUp.SignupActivity;
import com.example.recommender.User;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    public EditText usernameEditText;
    public EditText passwordEditText;
    public TextView logoApp;
    public Button loginButton;
    public ProgressBar loadingProgressBar;
    public int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Typeface type = Typeface.createFromAsset(this.getAssets(),"fonts/Caveat-Bold.ttf");
        this.usernameEditText = findViewById(R.id.username);
        this.passwordEditText = findViewById(R.id.password);
        this.loginButton = findViewById(R.id.login);
        this.loadingProgressBar = findViewById(R.id.loading);
        this.logoApp = findViewById(R.id.logoApp);
        logoApp.setTypeface(type);
        listener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Se ejecuta como resultado despues de un registro exitoso.
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                System.out.println("el dato es: "+data.getStringExtra("result"));
                this.usernameEditText.setText(data.getStringExtra("result"));
                listener();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                listener();
            }
        }
    }

    public void signup(View view){
        Intent activityIntent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivityForResult(activityIntent, LAUNCH_SECOND_ACTIVITY);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        //initiate successful logged in experience
        String id= model.getDisplayId();
        User usuario = new User();
        usuario.setId(id);
        usuario.setUsername(usernameEditText.getText().toString());
        usuario.setPassword(passwordEditText.getText().toString());
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        sessionManagement.saveSession(usuario);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void listener(){
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory(getApplication())).get(LoginViewModel.class);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));

                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    Intent activityIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(activityIntent);
                    //Complete and destroy login activity once successful
                    finish();
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }
}