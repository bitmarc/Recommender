package com.example.recommender.ui.profile;

import androidx.annotation.Nullable;

public class PasswordFormState {
    @Nullable
    private Integer password1Error;
    @Nullable
    private Integer password2Error;
    @Nullable
    private Integer actualPasswordError;

    private boolean isDataValid;

    PasswordFormState(@Nullable Integer actualPasswordError, @Nullable Integer password1Error, @Nullable Integer password2Error) {
        this.password1Error = password1Error;
        this.password2Error = password2Error;
        this.actualPasswordError = actualPasswordError;
        this.isDataValid = false;
    }

    PasswordFormState(boolean isDataValid) {
        this.password1Error = null;
        this.password2Error = null;
        this.actualPasswordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getPassword1Error() {
        return password1Error;
    }

    @Nullable
    Integer getPassword2Error() {
        return password2Error;
    }

    @Nullable
    Integer getActualPasswordError(){
        return actualPasswordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
