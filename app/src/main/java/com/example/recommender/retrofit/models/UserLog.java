package com.example.recommender.retrofit.models;

public class UserLog {

    private String id;
    private String username;
    private String password;
    private String personname;
    private String email;

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPersonname() {
        return personname;
    }

    public String getEmail() {
        return email;
    }

}
