package com.example.recommender.entities;

import java.io.Serializable;

public class Automobile implements Serializable {

    private int id;
    private String brand;
    private String model;
    private String year;
    private String version;

    public Automobile(int id, String brand, String model, String year, String version) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.version = version;
    }

    public Automobile(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
