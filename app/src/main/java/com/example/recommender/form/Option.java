package com.example.recommender.form;

import java.io.Serializable;

public class Option implements Serializable {

    String title;
    int id;
    public Option(String title, int id){
        this.title=title;
        this.id=id;
    }

    public Option(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
