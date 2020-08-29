package com.example.recommender.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String displayId; //agregado por mi
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String displayId) {
        this.displayName = displayName;
        this.displayId=displayId;
    }

    String getDisplayName() {
        return displayName;
    }
    String getDisplayId() {
        return displayId;
    }
}