package com.example.recommender.form;

public class Option {

    String title;
    String id;
    public Option(String title, String id){
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
