package com.example.recommender.ui.signUp;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recommender.R;
import com.example.recommender.data.Result;
import com.example.recommender.data.model.LoggedInUser;
import com.example.recommender.ui.signUp.SignupFormState;

public class SignupViewModel extends ViewModel {
    private MutableLiveData<SignupFormState> signupFormState = new MutableLiveData<>();



    public void signup(String username, String password) {
        // can be launched in a separate asynchronous job
        System.out.println("Lanzar registro");
    }


    public void signupDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            signupFormState.setValue(new SignupFormState(null, R.string.invalid_password));
        } else {
            signupFormState.setValue(new SignupFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}
