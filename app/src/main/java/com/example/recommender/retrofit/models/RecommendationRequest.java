package com.example.recommender.retrofit.models;

import com.example.recommender.entities.User;
import com.example.recommender.form.Form;

public class RecommendationRequest {
    private Form form;
    private User user;

    public RecommendationRequest(Form formulario, User usuario){
        this.form=formulario;
        this.user=usuario;
    }

    public Form getFormulario() {
        return form;
    }

    public void setFormulario(Form formulario) {
        form = formulario;
    }

    public User getUsuario() {
        return user;
    }

    public void setUsuario(User usuario) {
        this.user = usuario;
    }
}
