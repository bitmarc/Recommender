package com.example.recommender;

// Clase usuario para almacenar objetos referentes a la informacion del usuario loggeado.
public class User {
    int id;
    String username;
    String password;
    String personname;
    String email;

    public User(int id, String username, String password, String personname){
        this.username=username;
        this.password=password;
        this.id=id;
        this.personname=personname;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
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
