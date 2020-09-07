package com.example.recommender.ui.signUp;

import androidx.annotation.Nullable;

public class SignupFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer emailError;

    private boolean isDataValid;

    SignupFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer emailError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.emailError = emailError;
        this.isDataValid = false;
    }

    SignupFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.emailError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getEmailError(){
        return emailError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
