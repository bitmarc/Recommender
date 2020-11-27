package com.example.recommender.retrofit.models;

import com.example.recommender.entities.Recommendation;
import com.example.recommender.entities.RequestResult;
import com.example.recommender.form.Form;

import java.io.Serializable;
import java.util.ArrayList;

public class History implements Serializable {
    private int requests;
    private ArrayList<RequestResult> arrRequest;

    public History() {
    }

    public int getRequests() {
        return requests;
    }

    public void setMessage(int requests) {
        this.requests = requests;
    }

    public ArrayList<RequestResult> getArrRequest() {
        return arrRequest;
    }

    public void setArrRequest(ArrayList<RequestResult> arrRequest) {
        this.arrRequest = arrRequest;
    }
}
