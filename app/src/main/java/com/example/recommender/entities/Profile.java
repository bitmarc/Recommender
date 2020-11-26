package com.example.recommender.entities;

public class Profile {

    int id;
    String name;
    int parameter;

    public Profile(int id, String name, int Parameter){
        this.id=id;
        this.name=name;
        this.parameter=Parameter;
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

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

}
