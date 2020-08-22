package com.example.recommender;

import java.util.ArrayList;

public class Question {

    private int id;
    private String tittle;
    private String hint;
    private ArrayList<String> options;
    private int answer;

    public Question(int Id, String Title){
        this.id=Id;
        this.tittle=Title;
        this.options=new ArrayList<String>();
        this.answer=0;
    }
    public int getId() {
        return id;
    }

    public String getTittle() {
        return tittle;
    }

    public String getHint() {
        return hint;
    }
    public void setHint(String hint) {
        this.hint = hint;
    }

    public ArrayList<String> getOptions() {
        return options;
    }
    public void setOption(String option) {
        this.options.add(option);
    }

    public int getAnswer() {
        return answer;
    }
    public void setAnswer(int answer) {
        this.answer = answer;
    }


}
