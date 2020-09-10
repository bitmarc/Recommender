package com.example.recommender.ui.signUp;

import android.content.Context;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recommender.R;
import com.example.recommender.connection.ConnectionManager;

public class SignupViewModel extends ViewModel {
    private MutableLiveData<SignupFormState> signupFormState = new MutableLiveData<>();
    ConnectionManager cm;

    public SignupViewModel(){
        cm = new ConnectionManager();
    }



    public void signup(String username, String password) {
        // can be launched in a separate asynchronous job
        System.out.println("Lanzar registro");
    }

    LiveData<SignupFormState> getSignupFormState() {
        return signupFormState;
    }


    public void signupDataChanged(Context context, String username, String password, String email, String passR) {
        if (!isUserNameValid(username)) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_username, null, null));
        } else if (!isPasswordValid(password, passR)) {
            signupFormState.setValue(new SignupFormState(null, R.string.invalid_password, null));
        } else if (!isEmailValid(email)) {
            signupFormState.setValue(new SignupFormState(null, null,  R.string.invalid_email));
        }else{
            signupFormState.setValue(new SignupFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        return username != null && username.trim().length() > 2 ;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password, String passR) {
        return password != null && password.trim().length() > 5 && password.equals(passR);
    }

    private boolean isEmailValid(String email){
        if (email.contains("@"))
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        else
            return false;
    }

}
