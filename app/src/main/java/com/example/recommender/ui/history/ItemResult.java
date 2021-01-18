package com.example.recommender.ui.history;
// clase para generar items de resultaods de manera dinamica.

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


import com.example.recommender.R;
import com.example.recommender.entities.RequestResult;
import com.example.recommender.retrofit.models.History;

import java.util.ArrayList;

public class ItemResult extends ConstraintLayout{
    private Context context;
    private ArrayList<RequestResult> userResults;
    private int nresults;
    private LinearLayout.LayoutParams layoutParamsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private ConstraintLayout.LayoutParams layoutParamsContainer = new LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private ArrayList<String> idsRes;
    private ArrayList<Button> btnRes, btnForm;


    public ItemResult(Context context, History userResults){
        super(context);
        this.context=context;
        this.userResults=userResults.getArrRequest();
        this.nresults=userResults.getRequests();
        //idsRes = new ArrayList<>();
        btnForm = new ArrayList<>();
        btnRes = new ArrayList<>();
    }

    public ResultContainer initContainers(LinearLayout parent) {
        float scale = context.getResources().getDisplayMetrics().density;
        layoutParamsP.setMargins(0, toPixels(30,scale), 0, 0);
        for (int i = 0; i < nresults; i++) {
            parent.addView(createItem(context,i),layoutParamsP); //objeto result en lugar de question
        }
        ResultContainer rc = new ResultContainer(btnForm,btnRes);
        return rc;
    }

    private ConstraintLayout createItem(Context context, int count) {
        ConstraintLayout contenedor = new ConstraintLayout(context);
        contenedor.setId(View.generateViewId());
        contenedor.setLayoutParams(layoutParamsContainer);
        contenedor.setBackgroundColor(Color.parseColor("#FF5733"));

        TextView numResult = new TextView(context); // title ahora es numberResult
        numResult.setId(View.generateViewId());
        numResult.setText("Recomendacion #"+userResults.get(count).getId()); // question.getTitle() ahora se coambia por user result
        numResult.setGravity(Gravity.CENTER);
        numResult.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(numResult);
        //idsRes.add(numResult.getText().toString());

        TextView dateTime = new TextView(context);
        dateTime.setId(View.generateViewId());
        dateTime.setText(userResults.get(count).getDate());
        dateTime.setGravity(Gravity.CENTER);
        dateTime.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(dateTime);

        TextView profile = new TextView(context);
        profile.setId(View.generateViewId());
        profile.setText("Perfil: '"+userResults.get(count).getProfile().getName()+"'");
        profile.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(profile);

        TextView numRec = new TextView(context);
        numRec.setId(View.generateViewId());
        numRec.setText("Recomendaciones: "+userResults.get(count).getResults());
        numRec.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(numRec);

        final Button bForm = new Button(context);
        bForm.setId(View.generateViewId());
        bForm.setText("Ver formulario");
        bForm.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        bForm.setBackgroundColor(getResources().getColor(R.color.recomBox1));
        contenedor.addView(bForm);
        btnForm.add(bForm);

        Button bResult = new Button(context);
        bResult.setId(View.generateViewId());
        bResult.setText("Ver recomendaciones");
        bResult.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        bResult.setBackgroundColor(getResources().getColor(R.color.recomBox1));
        contenedor.addView(bResult);
        btnRes.add(bResult);

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
        contenedor.setBackgroundColor(getResources().getColor(R.color.recomBox2));
        return contenedor;
    }

    private int toPixels(int dp, float scale) {
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }


}
