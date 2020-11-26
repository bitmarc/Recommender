package com.example.recommender.entities;

import com.example.recommender.form.Question;

import java.util.ArrayList;

public class Recommendation {

    private int idRecommendation;
    private ArrayList<Automobile> results;
    private Profile profile;

    public Recommendation(){
    }

    public Recommendation(int idRecommendation, ArrayList<Automobile> results, Profile profile) {
        this.idRecommendation = idRecommendation;
        this.results = results;
        this.profile = profile;
    }

    public int getIdRecommendation() {
        return idRecommendation;
    }

    public void setIdRecommendation(int idRecommendation) {
        this.idRecommendation = idRecommendation;
    }

    public ArrayList<Automobile> getResults() {
        return results;
    }

    public void setResults(ArrayList<Automobile> results) {
        this.results = results;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
