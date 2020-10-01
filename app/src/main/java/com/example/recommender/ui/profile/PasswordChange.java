package com.example.recommender.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recommender.R;

public class PasswordChange extends AppCompatActivity {

    private Button bchangePass;
    private EditText actualPass;
    private EditText pass1;
    private EditText pass2;
    private PasswordChangeViewModel passwordChangeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        this.bchangePass = findViewById(R.id.idAceptar);
        bchangePass.setEnabled(false);
        this.pass2= findViewById(R.id.idEtNewPass2);
        this.actualPass = findViewById(R.id.idEtOladPass);
        this.pass1 = findViewById(R.id.idEtNewPass);

        passwordChangeViewModel= ViewModelProviders.of(this).get(PasswordChangeViewModel.class);

        passwordChangeViewModel.getPasswordFormState().observe(this, new Observer<PasswordFormState>() {
            @Override
            public void onChanged(PasswordFormState passwordFormState) {
                if (passwordFormState == null) {
                    return;
                }
                bchangePass.setEnabled(passwordFormState.isDataValid());
                if (passwordFormState.getActualPasswordError() != null) {
                    actualPass.setError(getString(passwordFormState.getActualPasswordError()));
                }
                if (passwordFormState.getPassword1Error() != null) {
                    pass1.setError(getString(passwordFormState.getPassword1Error()));
                }
                if (passwordFormState.getPassword2Error() != null){
                    pass2.setError(getString(passwordFormState.getPassword2Error()));
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
                passwordChangeViewModel.passwordChangeDataChanged(getApplicationContext(), actualPass.getText().toString(),
                        pass1.getText().toString(),pass2.getText().toString());
            }
        };

        actualPass.addTextChangedListener(afterTextChangedListener);
        pass1.addTextChangedListener(afterTextChangedListener);
        pass2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //signupViewModel.signup(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("newpass",pass2.getText().toString());
                    setResult(Activity.RESULT_OK, intent); // You can also send result without any data using setResult(int resultCode)
                    finish();
                }
                return false;
            }
        });

        bchangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("newpass",pass2.getText().toString());
                setResult(Activity.RESULT_OK, intent); // You can also send result without any data using setResult(int resultCode)
                finish();
            }
        });
    }



}