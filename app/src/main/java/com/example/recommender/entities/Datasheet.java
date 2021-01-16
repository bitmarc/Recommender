package com.example.recommender.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Datasheet implements Serializable {
    private int idCar;
    private ArrayList<Attribute> attributes;
    private ScoreSheet scoreSheet;

    public Datasheet(){

    }

    public Datasheet(int idCar, ArrayList<Attribute> attributes, ScoreSheet scoreSheet) {
        this.idCar = idCar;
        this.attributes = attributes;
        this.scoreSheet = scoreSheet;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public ScoreSheet getScoreSheet() {
        return scoreSheet;
    }

    public void setScoreSheet(ScoreSheet scoreSheet) {
        this.scoreSheet = scoreSheet;
    }
}
