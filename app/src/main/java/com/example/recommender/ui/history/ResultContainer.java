package com.example.recommender.ui.history;

import android.widget.Button;

import java.util.ArrayList;

public class ResultContainer {

    private ArrayList<Button> buttonForm;
    private ArrayList<Button> buttonRecom;

    public ResultContainer(ArrayList<Button> buttonForm, ArrayList<Button> buttonRecom){
        this.buttonForm=buttonForm;
        this.buttonRecom=buttonRecom;
    }
    public ArrayList<Button> getButtonForm() {
        return buttonForm;
    }

    public void setButtonForm(ArrayList<Button> buttonForm) {
        this.buttonForm = buttonForm;
    }

    public ArrayList<Button> getButtonRecom() {
        return buttonRecom;
    }

    public void setButtonRecom(ArrayList<Button> buttonRecom) {
        this.buttonRecom = buttonRecom;
    }


}
