package com.example.recommender.entities;

import com.example.recommender.form.Question;

import java.util.ArrayList;

public class Recommendation {

    private int idRecommendation;
    private ArrayList<Automobile> results;
    private Profile profile;
    private ArrayList<ScoreSheet> scores;

    public Recommendation(){
    }

    public Recommendation(int idRecommendation, ArrayList<Automobile> results, Profile profile, ArrayList<ScoreSheet> scores) {
        this.idRecommendation = idRecommendation;
        this.results = results;
        this.profile = profile;
        this.scores=scores;
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

    public ArrayList<ScoreSheet> getScores() {
        return scores;
    }

    public void setScores(ArrayList<ScoreSheet> scores) {
        this.scores = scores;
    }
}
