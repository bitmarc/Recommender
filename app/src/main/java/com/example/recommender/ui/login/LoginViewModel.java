package com.example.recommender.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Patterns;

import com.example.recommender.data.LoginRepository;
import com.example.recommender.data.Result;
import com.example.recommender.data.model.LoggedInUser;
import com.example.recommender.R;

import java.io.IOException;
import java.util.UUID;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private Application app;

    LoginViewModel(LoginRepository loginRepository, Application app) {
        this.loginRepository = loginRepository;
        this.app=app;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
/*        WifiManager manager = (WifiManager) app.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();*/
        String address = UUID.randomUUID().toString();
        System.out.println(address);
        new getresultBackground().execute(username, password, address);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
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


    class getresultBackground extends AsyncTask<String,Integer,Result<LoggedInUser>>{

        @Override
        protected Result<LoggedInUser> doInBackground(String... params) {
            Result<LoggedInUser> result = null;
            try {
                result = loginRepository.login(params[0], params[1], params[2]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Result<LoggedInUser> result) {
            super.onPostExecute(result);
            if (result instanceof Result.Success) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                loginResult.setValue(new LoginResult(new LoggedInUserView(data.getUserId())));
            } else {
                System.out.print("login failed");
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        }
    }
}