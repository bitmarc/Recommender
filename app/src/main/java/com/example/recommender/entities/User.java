package com.example.recommender.entities;

// Clase usuario para manejaar objetos referentes a la informacion del usuario loggeado.
public class User {

    private String id;
    private String username;
    private String password;
    private String personname;
    private String email;


    public User(String username, String password, String personname, String email){
        this.username=username;
        this.password=password;
        this.email=email;
        this.personname=personname;
    }

    public User(){
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonname() {
        return personname;
    }
    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


}
