package com.example.recommender.entities;

import java.io.Serializable;

public class Profile implements Serializable {

    int id;
    String name;
    String description;

    public Profile(int id, String name, String description){
        this.id=id;
        this.name=name;
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
