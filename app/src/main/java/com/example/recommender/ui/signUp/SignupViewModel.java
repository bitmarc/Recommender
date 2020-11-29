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

    LiveData<SignupFormState> getSignupFormState() {
        return signupFormState;
    }


    public void signupDataChanged(Context context, String username, String password, String email, String passR) {
        if (!isUserNameValid(username)) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_username, null, null,null));
        } else if (!isEmailValid(email)) {
            signupFormState.setValue(new SignupFormState(null, null,  R.string.invalid_email,null));
        } else if (!isPasswordValid(password)) {
            signupFormState.setValue(new SignupFormState(null, R.string.invalid_password, null,null));
        }else if (!isPasswordRepeatValid(password, passR)){
            signupFormState.setValue((new SignupFormState(null,null,null,R.string.invalid_password2)));
        }else{
            signupFormState.setValue(new SignupFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        return username != null && username.trim().length() > 2 ;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
    private boolean isPasswordRepeatValid(String password, String passR) {
        return passR != null && passR.equals(password);
    }

    private boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        /*
        if (email.contains("@") && email.contains("."))
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        else
            return false;
         */
    }

}
