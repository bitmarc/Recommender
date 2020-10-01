package com.example.recommender.ui.profile;

import android.content.Context;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recommender.R;
import com.example.recommender.ui.signUp.SignupFormState;

public class PasswordChangeViewModel extends ViewModel {
    private MutableLiveData<PasswordFormState> passwordFormState = new MutableLiveData<>();

    LiveData<PasswordFormState> getPasswordFormState() {
        return passwordFormState;
    }


    public void passwordChangeDataChanged(Context context, String ActualPassword, String password1, String password2) {
        if (!isActualPasswordValid(ActualPassword)) {
            passwordFormState.setValue(new PasswordFormState(R.string.invalid_password, null, null));
        } else if (!isPassword1Valid(password1)) {
            passwordFormState.setValue(new PasswordFormState(null, R.string.invalid_password, null));
        } else if (!isPassword2Valid(password1, password2)) {
            passwordFormState.setValue(new PasswordFormState(null, null,  R.string.invalid_password2));
        }else{
            passwordFormState.setValue(new PasswordFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isActualPasswordValid(String ActualPassword) {
        return ActualPassword != null && ActualPassword.trim().length() > 5;
    }

    // A placeholder password validation check
    private boolean isPassword1Valid(String password1) {
        return password1 != null && password1.trim().length() > 5;
    }

    private boolean isPassword2Valid(String password1, String password2){
        return password2 != null && password2.trim().length() > 5 && password2.equals(password1);
    }
}
