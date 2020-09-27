package com.example.recommender.form;

import java.util.ArrayList;
import java.util.List;

public class Form {
    private String id;
    private List<Question> questions;

    public Form(String id, List<Question> questions) {
        this.id = id;
        this.questions = questions;
    }

    public Form() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Question> getArrQuestions() {
        return questions;
    }

    public void setArrQuestions(List<Question> questions) {
        this.questions = questions;
    }


}
