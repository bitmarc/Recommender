package com.example.recommender.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayId; //agregado por mi
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayId) {
        this.displayId=displayId;
    }
    String getDisplayId() {
        return displayId;
    }
}