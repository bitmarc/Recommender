package com.example.recommender.ui.signUp;

import androidx.annotation.Nullable;

public class SignupFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordRepeatError;
    private boolean isDataValid;

    SignupFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer emailError, @Nullable Integer passwordRepeatError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.emailError = emailError;
        this.passwordRepeatError = passwordRepeatError;
        this.isDataValid = false;
    }

    SignupFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.emailError = null;
        this.passwordRepeatError = null;
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

    @Nullable
    Integer getPasswordRepeatError(){
        return passwordRepeatError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
