package com.example.recommender.entities;

import com.example.recommender.form.Form;

import java.io.Serializable;
import java.util.ArrayList;

public class RequestResult implements Serializable {

    private int id;
    private String date;
    private int results;
    private Profile profile;
    private ArrayList<Automobile> autos;
    private Form form;

    public RequestResult(int id, String date, int results, Profile profile, ArrayList<Automobile> autos, Form form) {
        this.id = id;
        this.date = date;
        this.results = results;
        this.profile = profile;
        this.autos = autos;
        this.form=form;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ArrayList<Automobile> getAutos() {
        return autos;
    }

    public void setAutos(ArrayList<Automobile> autos) {
        this.autos = autos;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

}
