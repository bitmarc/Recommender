package com.example.recommender.entities;

import java.io.Serializable;

public class ScoreSheet implements Serializable {
    int idAuto;
    String positive;
    String negative;
    String seeMore;

    public ScoreSheet(int idAuto, String positive, String negative, String seeMore) {
        this.idAuto = idAuto;
        this.positive = positive;
        this.negative = negative;
        this.seeMore = seeMore;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public String getSeeMore() {
        return seeMore;
    }

    public void setSeeMore(String seeMore) {
        this.seeMore = seeMore;
    }
}