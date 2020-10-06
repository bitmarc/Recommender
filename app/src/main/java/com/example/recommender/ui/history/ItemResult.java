package com.example.recommender.ui.history;
// clase para generar items de resultaods de manera dinamica.

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

public class ItemResult extends ConstraintLayout implements View.OnClickListener{
    private Context context;
    private int userResults;
    private LinearLayout.LayoutParams layoutParamsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private ConstraintLayout.LayoutParams layoutParamsContainer = new LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    private ArrayList<Integer> idsContenedores, idsBtnForm, idsBtnRes;

    public ItemResult(Context context, int userResults){
        super(context);
        this.context=context;
        this.userResults=userResults;
        idsContenedores = new ArrayList<>();
        idsBtnForm = new ArrayList<>();
        idsBtnRes = new ArrayList<>();
    }

    public void initContainers(LinearLayout parent) {
        float scale = context.getResources().getDisplayMetrics().density;
        layoutParamsP.setMargins(0, toPixels(30,scale), 0, 0);
        for (int i = 0; i < userResults; i++) {
            parent.addView(createItem(context),layoutParamsP); //objeto result en lugar de question
        }
    }

    private ConstraintLayout createItem(Context context) {
        ConstraintLayout contenedor = new ConstraintLayout(context);
        contenedor.setId(View.generateViewId());
        contenedor.setLayoutParams(layoutParamsContainer);
        contenedor.setBackgroundColor(Color.parseColor("#FF5733"));

        TextView numResult = new TextView(context); // title ahora es numberResult
        numResult.setId(View.generateViewId());
        numResult.setText("Resultado #1325"); // question.getTitle() ahora se coambia por user result
        numResult.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(numResult);

        TextView dateTime = new TextView(context);
        dateTime.setId(View.generateViewId());
        dateTime.setText("Fecha 21/03/2020, 14:55:30");
        dateTime.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(dateTime);

        TextView profile = new TextView(context);
        profile.setId(View.generateViewId());
        profile.setText("Perfil: 'Economistas'");
        profile.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(profile);

        TextView numRec = new TextView(context);
        numRec.setId(View.generateViewId());
        numRec.setText("Recomendaciones: 5");
        numRec.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(numRec);

        final Button bForm = new Button(context);
        bForm.setId(View.generateViewId());
        bForm.setText("Ver formulario");
        bForm.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(bForm);
        idsBtnForm.add(bForm.getId());
        bForm.setOnClickListener(this);

        Button bResult = new Button(context);
        bResult.setId(View.generateViewId());
        bResult.setText("Ver recomendaciones");
        bResult.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(bResult);
        idsBtnRes.add(bResult.getId());
        bResult.setOnClickListener(this);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contenedor);
        constraintSet.connect(numResult.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(numResult.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(numResult.getId(), constraintSet.TOP, contenedor.getId(), constraintSet.TOP);
        constraintSet.connect(dateTime.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(dateTime.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(dateTime.getId(), constraintSet.TOP, numResult.getId(), constraintSet.BOTTOM);
        constraintSet.connect(profile.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(profile.getId(), constraintSet.END, numRec.getId(), constraintSet.START);
        constraintSet.connect(profile.getId(), constraintSet.TOP, dateTime.getId(), constraintSet.BOTTOM);
        constraintSet.connect(numRec.getId(), constraintSet.START, profile.getId(), constraintSet.END);
        constraintSet.connect(numRec.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(numRec.getId(), constraintSet.TOP, dateTime.getId(), constraintSet.BOTTOM);

        constraintSet.connect(bForm.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(bForm.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(bForm.getId(), constraintSet.TOP, profile.getId(), constraintSet.BOTTOM);
        constraintSet.connect(bResult.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(bResult.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(bResult.getId(), constraintSet.TOP, bForm.getId(), constraintSet.BOTTOM);
        constraintSet.applyTo(contenedor);
        idsContenedores.add(contenedor.getId());
        return contenedor;
    }

    private int toPixels(int dp, float scale) {
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    private void loadForms(int idButton){
        System.out.println("id form:"+idButton);
    }
    private void loadRecom(int idButton){
        System.out.println("id recom:"+idButton);
    }

    @Override
    public void onClick(View view) {
        for(int x = 0; x<idsContenedores.size() ; x++){
            if(view.getId()==idsBtnForm.get(x)){
                loadForms(view.getId());
                break;
            }else if(view.getId()==idsBtnRes.get(x)){
                loadRecom(view.getId());
                break;
            }
        }
    }
}
