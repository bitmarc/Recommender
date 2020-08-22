package com.example.recommender.ui.recommendations;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.recommender.Question;
import com.example.recommender.R;

import java.util.ArrayList;

public class ItemForm extends ConstraintLayout implements AdapterView.OnItemSelectedListener {
    private ArrayList<Integer> idsContenedores, idsSpiners;
    private ArrayList<Question> questions;
    private TextView title;
    private Spinner spiner;
    private Button imageHint;
    private ConstraintLayout contenedor;
    private ConstraintLayout.LayoutParams layoutParamsContainer = new LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams layoutParamsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private int counter;


    public ItemForm(Context context, ArrayList<Question> questions) {
        super(context);
        System.out.println("Empieza ItemForm");
        idsContenedores = new ArrayList<>();
        idsSpiners = new ArrayList<>();
        this.questions = questions;
        this.counter=0;
    }


    public void initContainers(Context context, LinearLayout parent) {
        float scale = context.getResources().getDisplayMetrics().density;
        layoutParamsP.setMargins(0, toPixels(20,scale), 0, 0);
        for (int i = 0; i < questions.size(); i++) {
            parent.addView(createItem(context, questions.get(i)),layoutParamsP);
        }
    }

    private ConstraintLayout createItem(Context context, Question question) {
        contenedor = new ConstraintLayout(context);
        contenedor.setId(View.generateViewId());
        contenedor.setLayoutParams(layoutParamsContainer);

        final float scale = context.getResources().getDisplayMetrics().density;
        title = new TextView(context);
        title.setId(View.generateViewId());
        title.setText(idsContenedores.size()+1+".- "+question.getTittle());
        LayoutParams tvLp= new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        title.setLayoutParams(tvLp);
        contenedor.addView(title);

        spiner = new Spinner(context);
        spiner.setId(View.generateViewId());
        String[] optionsArray = new String[question.getOptions().size()];// inicializo un string de los elementos
        optionsArray = question.getOptions().toArray(optionsArray);//
        ArrayAdapter ad = new ArrayAdapter(context, android.R.layout.simple_spinner_item, optionsArray);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(ad);
        contenedor.addView(spiner);
        idsSpiners.add(spiner.getId());
        spiner.setOnItemSelectedListener(this);

        imageHint = new Button(context);
        imageHint.setId(View.generateViewId());
        imageHint.setBackground(context.getDrawable(R.drawable.ic_baseline_help_outline_24));
        imageHint.setLayoutParams(new LinearLayout.LayoutParams(toPixels(25, scale), toPixels(25, scale)));
        contenedor.addView(imageHint);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contenedor);
        constraintSet.connect(title.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(title.getId(), constraintSet.END, imageHint.getId(), constraintSet.END);
        constraintSet.connect(title.getId(), constraintSet.TOP, contenedor.getId(), constraintSet.TOP);
        constraintSet.connect(imageHint.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(imageHint.getId(), ConstraintSet.TOP, contenedor.getId(), constraintSet.TOP);
        constraintSet.connect(spiner.getId(), ConstraintSet.TOP, title.getId(), constraintSet.BOTTOM);
        constraintSet.applyTo(contenedor);
        idsContenedores.add(contenedor.getId());
        return contenedor;
    }

    private int toPixels(int dp, float scale) {
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public boolean getStatus(){
        boolean status= true;
        for (int j=0;j<questions.size();j++){
            if(questions.get(j).getAnswer()!=0){
                System.out.println("correcto "+questions.size()+" j:"+j);
            }else{
                status=false;
                break;
            }
        }
        return status;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        if(parent.getItemAtPosition(position).equals("Selecciona")){
            System.out.println("Elemento invalido");
            //do nothing
        }else{
            for(int i=0;i<idsSpiners.size();i++)
            if(parent.getId()==idsSpiners.get(i)){
                questions.get(i).setAnswer(position);
                Toast.makeText(parent.getContext(), "Selected: " + questions.get(i).getTittle(), Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}