package com.example.recommender.entities;

import com.example.recommender.form.Question;

import java.util.ArrayList;

public class Recommendation {
    private int idRecommendation;
    private User usuerOb;
    private ArrayList<Automobile> results;
    private ArrayList<Question> form;
    private String profile;

    public Recommendation(int id){
        this.idRecommendation=id;
    }
}
