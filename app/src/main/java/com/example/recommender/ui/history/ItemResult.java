package com.example.recommender.ui.history;
// clase para generar items de resultaods de manera dinamica.

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;


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
    private Typeface franklin;
    private TextView nRes;
    private float scale;


    public ItemResult(Context context, History userResults){
        super(context);
        this.context=context;
        this.scale = context.getResources().getDisplayMetrics().density;
        this.userResults=userResults.getArrRequest();
        this.nresults=userResults.getRequests();
        //idsRes = new ArrayList<>();
        btnForm = new ArrayList<>();
        btnRes = new ArrayList<>();
        this.franklin= ResourcesCompat.getFont(context,R.font.franklin_gothic_demi_cond);
    }

    public ResultContainer initContainers(LinearLayout parent) {
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
        contenedor.setPadding(0,toPixels(5,scale),0,toPixels(8,scale));
        contenedor.setLayoutParams(layoutParamsContainer);
        //contenedor.setBackgroundColor(Color.parseColor("#FF5733"));

        nRes = new TextView(context);
        nRes.setId(View.generateViewId());
        nRes.setText("# "+(count+1));
        nRes.setTypeface(franklin);
        nRes.setGravity(Gravity.CENTER);
        nRes.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        nRes.setTextSize(getResources().getDimension(R.dimen.LargeFranklin));
        nRes.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        ConstraintLayout.LayoutParams paramsTvInd=new ConstraintLayout.LayoutParams(toPixels(150,scale), LayoutParams.WRAP_CONTENT);
        paramsTvInd.setMargins(toPixels(5,scale),0,0,0);
        nRes.setLayoutParams(paramsTvInd);
        contenedor.addView(nRes);

        TextView numResult = new TextView(context); // title ahora es numberResult
        numResult.setId(View.generateViewId());
        numResult.setText("RESULTADO #"+userResults.get(count).getId()); // question.getTitle() ahora se coambia por user resulT
        numResult.setTextSize(getResources().getDimension(R.dimen.container_text_values));
        numResult.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        ConstraintLayout.LayoutParams lparamstvResult= new LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT);
        lparamstvResult.setMargins(0,toPixels(15,scale),0,0);
        numResult.setLayoutParams(lparamstvResult);
        contenedor.addView(numResult);

        TextView dateTime = new TextView(context);
        dateTime.setId(View.generateViewId());
        dateTime.setText(userResults.get(count).getDate());
        dateTime.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        dateTime.setTextSize(getResources().getDimension(R.dimen.container_text_values));
        dateTime.setLayoutParams(new LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(dateTime);

        TextView profile = new TextView(context);
        profile.setId(View.generateViewId());
        String sourceString = "<b>Perfil:</b> '"+userResults.get(count).getProfile().getName()+"'";
        profile.setText(Html.fromHtml(sourceString));
        profile.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        profile.setTextSize(getResources().getDimension(R.dimen.container_text_values));
        profile.setLayoutParams(new LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(profile);

        TextView numRec = new TextView(context);
        numRec.setId(View.generateViewId());
        numRec.setText("Recomendaciones: "+userResults.get(count).getResults());
        numRec.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        numRec.setTextSize(getResources().getDimension(R.dimen.container_text_values));
        numRec.setLayoutParams(new LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT));
        contenedor.addView(numRec);

        final Button bForm = new Button(context);
        bForm.setId(View.generateViewId());
        bForm.setText("Ver formulario");
        bForm.setTextSize(getResources().getDimension(R.dimen.normal_text_questions));
        bForm.setTypeface(franklin);
        bForm.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        bForm.setBackgroundColor(getResources().getColor(R.color.colorBackgroundNav));
        bForm.setPadding(toPixels(2,scale),1,toPixels(2,scale),1);
        LayoutParams btnFormLp =new LayoutParams(LayoutParams.MATCH_CONSTRAINT, toPixels(25,scale));
        btnFormLp.setMargins(0,toPixels(8,scale),0,0);
        bForm.setLayoutParams(btnFormLp);
        contenedor.addView(bForm);
        btnForm.add(bForm);

        Button bResult = new Button(context);
        bResult.setId(View.generateViewId());
        bResult.setText("Ver recomendaciones");
        bResult.setTextSize(getResources().getDimension(R.dimen.normal_text_questions));
        bResult.setTypeface(franklin);
        bResult.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        bResult.setBackgroundColor(getResources().getColor(R.color.colorBackgroundNav));
        bResult.setPadding(toPixels(2,scale),1,toPixels(2,scale),1);
        LayoutParams btnResLp =new LayoutParams(LayoutParams.MATCH_CONSTRAINT, toPixels(25,scale));
        btnResLp.setMargins(0,toPixels(5,scale),0,0);
        bResult.setLayoutParams(btnResLp);
        //bResult.setBackgroundColor(getResources().getColor(R.color.recomBox1));
        contenedor.addView(bResult);
        btnRes.add(bResult);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contenedor);

        constraintSet.connect(nRes.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(nRes.getId(), constraintSet.TOP, contenedor.getId(), constraintSet.TOP);
        //constraintSet.connect(nRes.getId(), constraintSet.BOTTOM, bForm.getId(), constraintSet.TOP);

        constraintSet.connect(numResult.getId(), constraintSet.TOP, contenedor.getId(), constraintSet.TOP);
        constraintSet.connect(numResult.getId(), constraintSet.START, nRes.getId(), constraintSet.END);
        constraintSet.connect(numResult.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(dateTime.getId(), constraintSet.TOP, numResult.getId(), constraintSet.BOTTOM);
        constraintSet.connect(dateTime.getId(), constraintSet.START, nRes.getId(), constraintSet.END);
        constraintSet.connect(dateTime.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(profile.getId(), constraintSet.TOP, dateTime.getId(), constraintSet.BOTTOM);
        constraintSet.connect(profile.getId(), constraintSet.START, nRes.getId(), constraintSet.END);
        constraintSet.connect(profile.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(numRec.getId(), constraintSet.TOP, profile.getId(), constraintSet.BOTTOM);
        constraintSet.connect(numRec.getId(), constraintSet.START, nRes.getId(), constraintSet.END);
        constraintSet.connect(numRec.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(bForm.getId(), constraintSet.TOP, nRes.getId(), constraintSet.BOTTOM);
        constraintSet.connect(bForm.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(bForm.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(bResult.getId(), constraintSet.TOP, bForm.getId(), constraintSet.BOTTOM);
        constraintSet.connect(bResult.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(bResult.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);


        constraintSet.applyTo(contenedor);
        //contenedor.setBackgroundColor(getResources().getColor(R.color.recomBox2));
        contenedor.setBackgroundColor(getResources().getColor(R.color.colorBackgroundContainer));
        return contenedor;
    }

    private int toPixels(int dp, float scale) {
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }


}
