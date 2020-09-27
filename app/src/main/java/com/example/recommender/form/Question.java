package com.example.recommender.form;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String id;
    private String title;
    private String hint;
    private List<Option> options;
    private int answer;

    public Question(String id, String title, String hint, List<Option> options) {
        this.id = id;
        this.title = title;
        this.hint = hint;
        this.options = options;
    }

    public Question() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }



}
